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
        Researcher researcher = new Researcher(email, name, contact, title, author, paperAbstract, fileName, false, false);
        User user = new User(email, name, contact, "researcher", password);

        return new ResponseEntity<>(service.insertResearcher(researcher,user, paper), HttpStatus.CREATED);
    }

}
