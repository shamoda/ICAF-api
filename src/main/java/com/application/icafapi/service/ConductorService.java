package com.application.icafapi.service;

import com.application.icafapi.common.util.EmailUtil;
import com.application.icafapi.exception.ICAFException;
import com.application.icafapi.model.User;
import com.application.icafapi.model.WorkshopConductor;
import com.application.icafapi.repository.ConductorRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.application.icafapi.common.constant.Email.*;

@Service
public class ConductorService {

    private final ConductorRepository conductorRepository;
    private final UserService userService;
    private final FileService fileService;
    private final EmailUtil emailUtil;

    public ConductorService(ConductorRepository conductorRepository, UserService userService, FileService fileService, EmailUtil emailUtil) {
        this.conductorRepository = conductorRepository;
        this.userService = userService;
        this.fileService = fileService;
        this.emailUtil = emailUtil;
    }

    //create a workshop conductor
    public WorkshopConductor createWorkshopConductor(WorkshopConductor workshopConductor, User user) throws ICAFException {
        //Saving as user
        userService.insertUser(user);
        emailUtil.sendQR(user.getName(), user.getEmail(), QR_SUBJECT, QR_BODY+COMMITTEE_REGISTRATION_END, user.getRole());
        //saving workshop conductor
        conductorRepository.save(workshopConductor);
        return workshopConductor;
    }
    public List<WorkshopConductor> getAllWorkshops(){
        return conductorRepository.findAll();
    }
    public List<WorkshopConductor> getBySearch(WorkshopConductor workshopConductor){
        //Search probe
        Example<WorkshopConductor> example = Example.of(workshopConductor);
        return conductorRepository.findAll(example);
    }

    public WorkshopConductor getConductor(String conductor) {
        return conductorRepository.findById(conductor).get();
    }
}
