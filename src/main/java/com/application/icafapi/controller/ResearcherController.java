package com.application.icafapi.controller;

import com.application.icafapi.model.Researcher;
import com.application.icafapi.model.User;
import com.application.icafapi.service.ResearcherService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ResearcherController {

    private final ResearcherService service;

    @Autowired
    public ResearcherController(ResearcherService service) {
        this.service = service;
    }

    @PostMapping("/researcher")
    public ResponseEntity<?> registerResearcher(@RequestParam("name") String name,
                                                @RequestParam("email") String email,
                                                @RequestParam("contact") String contact,
                                                @RequestParam("password") String password,
                                                @RequestParam("title") String title,
                                                @RequestParam("author") String author,
                                                @RequestParam("paperAbstract") String paperAbstract,
                                                @RequestParam("paper") MultipartFile paper
                                                )
    {
        String extension = FilenameUtils.getExtension(paper.getOriginalFilename());
        String fileName = title + "." + extension;
        Researcher researcher = new Researcher(email, name, contact, title, author, paperAbstract, fileName, "pending", "false", "");
        User user = new User(email, name, contact, "researcher", password);

        return new ResponseEntity<>(service.insertResearcher(researcher,user, paper), HttpStatus.CREATED);
    }

    @GetMapping("/researcher")
    public ResponseEntity<?> getAllResearchers() {
        return new ResponseEntity<>(service.retrieveAllResearchers(), HttpStatus.OK);
    }

    @GetMapping("/researcher/{email}")
    public ResponseEntity<?> getAllResearcher(@PathVariable String email) {
        return new ResponseEntity<>(service.retrievePaper(email), HttpStatus.OK);
    }

    @PostMapping("/researcher/filter")
    public ResponseEntity<?> getResearchersByExample(@RequestBody Researcher researcher) {
        return new ResponseEntity<>(service.retrieveByExample(researcher), HttpStatus.OK);
    }
    @PostMapping("/researcher/review")
    public ResponseEntity<?> reviewSubmission(@RequestParam("email") String email,
                                              @RequestParam("status") String status,
                                              @RequestParam("rComment") String rComment
                                            )
    {
        return new ResponseEntity<>(service.reviewSubmission(email, status, rComment), HttpStatus.OK);
    }

    @PostMapping("/researcher/pay/{email}")
    public ResponseEntity<?> updatePayment(@PathVariable String email) {
        return new ResponseEntity<>(service.updatePayment(email), HttpStatus.OK);
    }

    @DeleteMapping("/researcher/{email}")
    public ResponseEntity<?> deleteResearcher(@PathVariable String email) {
        return new ResponseEntity<>(service.deleteResearcher(email), HttpStatus.OK);
    }
}
