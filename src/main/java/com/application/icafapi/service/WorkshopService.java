package com.application.icafapi.service;

import com.application.icafapi.common.util.EmailUtil;
import com.application.icafapi.model.User;
import com.application.icafapi.model.WorkshopConductor;
import com.application.icafapi.repository.WorkshopRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.application.icafapi.common.constant.Email.*;

@Service
public class WorkshopService {

    private final WorkshopRepository workshopRepository;
    private final UserService userService;
    private final FileService fileService;
    private final EmailUtil emailUtil;

    @Autowired  //Dependency injection
    public WorkshopService(WorkshopRepository workshopRepository, UserService userService, FileService fileService, EmailUtil emailUtil) {
        this.workshopRepository = workshopRepository;
        this.userService = userService;
        this.fileService = fileService;
        this.emailUtil = emailUtil;
    }

    public String createWorkshop(WorkshopConductor workshopConductor, MultipartFile multipartFile, User user){
            //Extracting the extension
            String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            //Modify file name
            String fileTitle = workshopConductor.getWorkshopTitle()+ "." + ext;
            fileService.uploadFile(multipartFile,fileTitle,"proposal");
            //Saving as user
            userService.insertUser(user);
            workshopRepository.save(workshopConductor);
            return "DONE";
    }

    public List<WorkshopConductor> getAllWorkshops(){
        return workshopRepository.findAll();
    }

    public List<WorkshopConductor> getBySearch(WorkshopConductor workshopConductor){
        //Search probe
        Example<WorkshopConductor> example = Example.of(workshopConductor);
        return  workshopRepository.findAll(example);
    }

    public String deleteWorkshop(String email) {
        userService.deleteUserByEmail(email);
        // please implement the workshop deletion logic here | uploaded file should be deleted at the same time
        emailUtil.sendEmail(email, ACCOUNT_REMOVAL_SUBJECT, ACCOUNT_REMOVAL_BODY+COMMITTEE_REGISTRATION_END);
        return "Workshop deleted";
    }
}
