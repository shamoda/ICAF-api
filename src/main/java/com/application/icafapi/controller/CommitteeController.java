package com.application.icafapi.controller;

import com.application.icafapi.model.User;
import com.application.icafapi.service.CommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class CommitteeController {

    private final CommitteeService service;

    @Autowired
    public CommitteeController(CommitteeService service) {
        this.service = service;
    }

    @PostMapping("/committee")
    public ResponseEntity<?> insertCommitteeMember(@RequestParam("name") String name,
                                                   @RequestParam("email") String email,
                                                   @RequestParam("contact") String contact,
                                                   @RequestParam("role") String role,
                                                   @RequestParam("password") String password
                                                   )
    {
        User user = new User(email, name, contact, role, password);
        return new ResponseEntity<>(service.insertCommitteeMember(user), HttpStatus.CREATED);
    }
}
