package com.application.icafapi.service;

import com.application.icafapi.common.util.EmailUtil;
import com.application.icafapi.model.Workshop;
import com.application.icafapi.repository.WorkshopRepository;
import lombok.extern.slf4j.Slf4j;
import com.application.icafapi.model.User;
import com.application.icafapi.model.WorkshopConductor;
import com.application.icafapi.repository.WorkshopRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import static com.application.icafapi.common.constant.Email.*;
import static com.application.icafapi.common.constant.Email.COMMITTEE_REGISTRATION_END;
import static com.application.icafapi.common.constant.workshopConstant.*;
import static com.application.icafapi.common.constant.AWS.*;

@Slf4j
@Service
public class WorkshopService {

    private final WorkshopRepository workshopRepository;
    private final FileService fileService;
    private final EmailUtil emailUtil;
    private final MongoTemplate mongoTemplate;

    @Autowired  //Dependency injection
    public WorkshopService(WorkshopRepository workshopRepository, UserService userService, FileService fileService, EmailUtil emailUtil) {
        this.workshopRepository = workshopRepository;
        this.fileService = fileService;
        this.emailUtil = emailUtil;
        this.mongoTemplate = mongoTemplate;
    }
   
    //create a workshop conductor
    public Workshop createWorkshop(Workshop workshop,MultipartFile proposal){
        //unique id generating by counting no of documents
        Query query = new Query();
        query.addCriteria(Criteria.where(""));
        Long count = mongoTemplate.count(query,Workshop.class);
        int part = count.intValue()+1;
        ID = TAG + part;
        log.info("COUNT :" + ID);
        //setting ID
        workshop.setWorkshopId(ID);
        //extracting file extension
        String ext = FilenameUtils.getExtension(proposal.getOriginalFilename());
        FILE_NAME = workshop.getTitle() +"."+ext;
        workshop.setFileName(FILE_NAME);
        //upload file
        fileService.uploadFile(proposal,FILE_NAME, PROPOSAL);
        //generating email
        //emailUtil.sendEmail(workshop.getConductor(), WORKSHOP_REGISTRATION_BODY, WORKSHOP_REGISTRATION_BODY);
        workshopRepository.save(workshop);
        return workshop;
    }

    public List<Workshop> getAllWorkshops(){
        return workshopRepository.findAll();
    }
    public List<Workshop> getBySearch(Workshop workshop){
        //Search probe,matching the expected inputs
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase().withMatcher("title", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))
                .withIgnoreCase().withMatcher("subject", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))
                .withIgnoreCase().withMatcher("description", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))
                .withIgnoreCase().withMatcher("conductor", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
        Example<Workshop> example = Example.of(workshop,matcher);
        return  workshopRepository.findAll(example);
    }


    //review workshop
    public String reviewProposal(String workshopId, String status, String rComment,String conductorEmail,MultipartFile image) {
        if(status.equals("approved")) {
            emailUtil.sendEmail(conductorEmail, SUBMISSION_STATUS_SUBJECT, SUBMISSION_APPROVED_BODY+COMMITTEE_REGISTRATION_END);
        } else if (status.equals("rejected")) {
            emailUtil.sendEmail(conductorEmail, SUBMISSION_STATUS_SUBJECT, SUBMISSION_REJECTED_BODY+COMMITTEE_REGISTRATION_END);
        }
        //querying and finding the object matching with workshopId
        Workshop workshop = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(workshopId)), Workshop.class);
        workshop.setStatus(status);
        workshop.setRComment(rComment);
        //Generate image name
        String ext = FilenameUtils.getExtension(image.getOriginalFilename());
        IMAGE_NAME = workshop.getWorkshopId() +"."+ext;
        //upload file
        fileService.uploadFile(image,IMAGE_NAME, PROPOSAL);
        mongoTemplate.save(workshop);
        return "reviewed";
    }
    //returns a list of workshops for each conductor
    public List<Workshop> getWorkshopsByConductor(String conductorEmail){
       return workshopRepository.findByConductor(conductorEmail);
    }
    //returns a workshop by Id
    public Optional<Workshop> getWorkshopById(String Id){
        return workshopRepository.findById(Id);
    }

    public String getImageUrl(String imageName){

        S3Client s3client = S3Client.create();
        S3Utilities utilities = s3client.utilities();
        GetUrlRequest request = GetUrlRequest.builder().bucket(PROPOSAL_BUCKET).key(imageName).build();
        URL url = utilities.getUrl(request);
        return url.toString();
}
    public String deleteWorkshop(String email) {
        userService.deleteUserByEmail(email);
        // please implement the workshop deletion logic here | uploaded file should be deleted at the same time
        emailUtil.sendEmail(email, ACCOUNT_REMOVAL_SUBJECT, ACCOUNT_REMOVAL_BODY+COMMITTEE_REGISTRATION_END);
        return "Workshop deleted";

    }
}
