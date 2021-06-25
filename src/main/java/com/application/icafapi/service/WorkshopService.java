package com.application.icafapi.service;

import com.application.icafapi.common.util.EmailUtil;
import com.application.icafapi.model.Workshop;
import com.application.icafapi.repository.ConductorRepository;
import com.application.icafapi.repository.WorkshopRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
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
    private final UserService userService;
    private final ConductorRepository conductorRepository;

    @Autowired  //Dependency injection
    public WorkshopService(WorkshopRepository workshopRepository, UserService userService, FileService fileService,ConductorRepository conductorRepository,EmailUtil emailUtil, MongoTemplate mongoTemplate) {
        this.workshopRepository = workshopRepository;
        this.fileService = fileService;
        this.emailUtil = emailUtil;
        this.userService = userService;
        this.mongoTemplate = mongoTemplate;
        this.conductorRepository = conductorRepository;
    }
   
    //create a workshop conductor
    public String createWorkshop(Workshop workshop,MultipartFile proposal){
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
        return "created";
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
    public String reviewProposal( String workshopId, String status, String rComment, String conductorEmail,String adminComment) {
        if(status.equals("approved")) {
            emailUtil.sendEmail(conductorEmail, SUBMISSION_STATUS_SUBJECT, SUBMISSION_APPROVED_BODY+COMMITTEE_REGISTRATION_END);
        } else if (status.equals("rejected")) {
            emailUtil.sendEmail(conductorEmail, SUBMISSION_STATUS_SUBJECT, SUBMISSION_REJECTED_BODY+COMMITTEE_REGISTRATION_END);
        }
        //querying and finding the object matching with workshopId
        Workshop workshop = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(workshopId)), Workshop.class);
        workshop.setStatus(status);
        log.info(workshop.getStatus());
        //check reviewer comment null
        workshop.setRComment(rComment);
        workshop.setAComment(adminComment);

        mongoTemplate.save(workshop);
        return "reviewed";
    }
    //returns a workshop for each conductor
    public Workshop getWorkshopsByConductor(String conductorEmail){
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
        //find the workshop of the Id ,delete workshop
        Workshop workshop = workshopRepository.findById(email).get();
        String workshopId = workshop.getWorkshopId();
        workshopRepository.deleteById(workshopId);
        //delete workshop conductor
        conductorRepository.deleteById(email);
        //delete the user
        userService.deleteUserByEmail(email);
        // please implement the workshop deletion logic here | uploaded file should be deleted at the same time
        emailUtil.sendEmail(email, ACCOUNT_REMOVAL_SUBJECT, ACCOUNT_REMOVAL_BODY+COMMITTEE_REGISTRATION_END);
        return "Workshop deleted";

    }
    public String editWorkshop(String workshopId, String date, String time, MultipartFile image, String venue, String title, String subject, String conductor, String description, String publish, String aComment) {
        //querying and finding the object matching with workshopId
        Workshop workshop = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(workshopId)), Workshop.class);
        workshop.setDate(date);
        workshop.setTime(time);
        workshop.setVenue(venue);
        workshop.setTitle(title);
        workshop.setSubject(subject);
        workshop.setConductor(conductor);
        workshop.setDescription(description);
        workshop.setPublish(publish);
        workshop.setAComment(aComment);
        workshop.setEdit(true);
        //Generate image name and setting
        String ext = FilenameUtils.getExtension(image.getOriginalFilename());
        IMAGE_NAME = workshop.getWorkshopId() +"."+ext;
        workshop.setImageName(IMAGE_NAME);
        //upload file
        fileService.uploadFile(image,IMAGE_NAME, PROPOSAL);
        mongoTemplate.save(workshop);
        return "edited";
    }
    public String publishPost(String workshopId,String status,String postComment){
        Workshop workshop = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(workshopId)), Workshop.class);
        log.info(workshop.getWorkshopId());
        //send emails to conductors for published and unpublished posts
        if(status.equals("published")) {
            emailUtil.sendEmail(workshop.getConductor(), POST_STATUS_SUBJECT, POST_APPROVED_BODY+COMMITTEE_REGISTRATION_END);
        } else if (status.equals("unpublished")) {
            emailUtil.sendEmail(workshop.getConductor(), POST_STATUS_SUBJECT, POST_REJECTED_BODY+COMMITTEE_REGISTRATION_END);
        }
        workshop.setPublish(status);
        workshop.setPostComment(postComment);
        workshopRepository.save(workshop);
        return "Published";
    }
}
