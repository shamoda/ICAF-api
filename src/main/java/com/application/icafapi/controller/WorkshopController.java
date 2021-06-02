package com.application.icafapi.controller;

import com.application.icafapi.model.User;
import com.application.icafapi.model.WorkshopConductor;
import com.application.icafapi.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
public class WorkshopController {

    private final WorkshopService workshopService;
    @Autowired //Dependency injection
    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }
    @PostMapping("/registerWorkshop")
    public ResponseEntity<?> createResearch(@RequestParam("email") String email,
                                            @RequestParam("name") String name,
                                            @RequestParam("phone") String phone,
                                            @RequestParam("title") String title,
                                            @RequestParam("qualifications") String qualifications,
                                            @RequestParam("organization") String organization,
                                            @RequestParam("post") String post,
                                            @RequestParam("fileData") MultipartFile multipartFile,
                                            @RequestParam("accept") boolean accept,
                                            @RequestParam("password") String password)
    {
        WorkshopConductor workshopConductor = new WorkshopConductor(email,name,phone,title,qualifications,organization,post,accept);
        User user = new User(email,name,phone,"workshopConductor", password);
        return new ResponseEntity<>(workshopService.createWorkshop(workshopConductor,multipartFile,user), HttpStatus.CREATED);
    }
    @GetMapping("/getWorkshops")
    public ResponseEntity<?> retrieveWorkshops(){
        return  new ResponseEntity<>(workshopService.getAllWorkshops(),HttpStatus.ACCEPTED);
    }
    @GetMapping("/getWorkshops/search")
    public ResponseEntity<?>filterWorkshops(@RequestBody WorkshopConductor workshopConductor){
        return  new ResponseEntity<>(workshopService.getBySearch(workshopConductor),HttpStatus.FOUND);
    }

    @DeleteMapping("/workshop/{email}")
    public ResponseEntity<?> deleteWorkshop(@PathVariable String email) {
        return new ResponseEntity<>(workshopService.deleteWorkshop(email), HttpStatus.OK);
    }

}
