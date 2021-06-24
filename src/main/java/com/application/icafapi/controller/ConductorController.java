package com.application.icafapi.controller;

import com.application.icafapi.model.User;
import com.application.icafapi.model.WorkshopConductor;
import com.application.icafapi.service.ConductorService;
import com.application.icafapi.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.application.icafapi.common.constant.workshopConstant.ACCEPT;
import static com.application.icafapi.common.constant.workshopConstant.ROLE;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ConductorController {

    private final ConductorService conductorService;

    @Autowired  //Dependency injection
    public ConductorController(WorkshopService workshopService, ConductorService conductorService) {
        this.conductorService = conductorService;
    }

    @PostMapping("/registerConductor")
    public ResponseEntity<?> createResearch(@RequestParam("email") String email,
                                            @RequestParam("fName") String fName,
                                            @RequestParam("lName") String lName,
                                            @RequestParam("phone") String phone,
                                            @RequestParam("address") String address,
                                            @RequestParam("edu1") String qualifications1,
                                            @RequestParam("edu2") String qualifications2,
                                            @RequestParam("city") String city,
                                            @RequestParam("state") String state,
                                            @RequestParam("organization") String organization,
                                            @RequestParam("post")String post,
                                            @RequestParam("password") String password)
    {
        WorkshopConductor workshopConductor = new WorkshopConductor(email,fName,lName,phone,address,city,state,qualifications1,qualifications2,organization,post);
        //appending name
        String name = fName + " "+lName;
        User user = new User(email,name,phone,ROLE, password);
        return new ResponseEntity<>(conductorService.createWorkshopConductor(workshopConductor,user), HttpStatus.CREATED);
    }

    @GetMapping("/getConductor/{conductor}")
    public ResponseEntity<?> getWorkshopsForConductor(@PathVariable String conductor) {
        return new ResponseEntity<>(conductorService.getConductor(conductor), HttpStatus.ACCEPTED);
    }

}
