package com.application.icafapi.controller;

import com.application.icafapi.model.User;
import com.application.icafapi.model.Workshop;
import com.application.icafapi.model.WorkshopConductor;
import com.application.icafapi.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.Date;

import static com.application.icafapi.common.constant.workshopConstant.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class WorkshopController {

    private final WorkshopService workshopService;
    @Autowired //Dependency injection
    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }
    @PostMapping("/registerWorkshop")
    public ResponseEntity<?> createWorkshop(@RequestParam("title") String title,
                                            @RequestParam("subject") String subject,
                                            @RequestParam("conductor") String condutor,
                                            @RequestParam("description") String description,
                                            @RequestParam(value="file", required = false)MultipartFile proposal
                                         )
    {
        Workshop workshop = new Workshop(ID,title,subject,condutor,description,FILE_NAME,IMAGE_NAME,VENUE,DATE,TIME,ACCEPT,R_COMMENT,A_COMMENT);
        return new ResponseEntity<>(workshopService.createWorkshop(workshop,proposal), HttpStatus.CREATED);
    }
    @GetMapping("/getConductors")
    public ResponseEntity<?> retrieveWorkshops(){
        return  new ResponseEntity<>(workshopService.getAllWorkshops(),HttpStatus.ACCEPTED);
    }
    @GetMapping("/getWorkshops/search")
    public ResponseEntity<?>filterWorkshops(@RequestBody Workshop workshop){
        return  new ResponseEntity<>(workshopService.getBySearch(workshop),HttpStatus.ACCEPTED);
    }
    @PostMapping("/reviewProposal")
    public ResponseEntity<?> reviewProposal(@RequestParam("status") String status,
                                            @RequestParam("rComment") String rComment,
                                            @RequestParam("conductor")String conductor,
                                            @RequestParam("id")String workshopId,
                                            @RequestParam("image")MultipartFile image
                                            )
    {
        return new ResponseEntity<>(workshopService.reviewProposal(workshopId,status,rComment,conductor,image),HttpStatus.CREATED);
    }
    @GetMapping("/getWorkshopsByConductor/{conductor}")
    public ResponseEntity<?> getWorkshopsForConductor(@PathVariable String conductor){
        return new ResponseEntity<>(workshopService.getWorkshopsByConductor(conductor),HttpStatus.ACCEPTED);
    }
    @GetMapping("/getWorkshopById/{workshopId}")
    public ResponseEntity<?> getWorkshopById(@PathVariable String workshopId){
        return new ResponseEntity<>(workshopService.getWorkshopById(workshopId),HttpStatus.OK);
    }

    @DeleteMapping("/workshop/{email}")
    public ResponseEntity<?> deleteWorkshop(@PathVariable String email) {
        return new ResponseEntity<>(workshopService.deleteWorkshop(email), HttpStatus.OK);
    }

}
