package com.application.icafapi.service;

import com.application.icafapi.model.User;
import com.application.icafapi.model.WorkshopConductor;
import com.application.icafapi.repository.ConductorRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConductorService {

    private final ConductorRepository conductorRepository;
    private final UserService userService;
    private final FileService fileService;

    public ConductorService(ConductorRepository conductorRepository, UserService userService, FileService fileService) {
        this.conductorRepository = conductorRepository;
        this.userService = userService;
        this.fileService = fileService;
    }

    //create a workshop conductor
    public WorkshopConductor createWorkshopConductor(WorkshopConductor workshopConductor, User user){
        //Saving as user
        userService.insertUser(user);
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
