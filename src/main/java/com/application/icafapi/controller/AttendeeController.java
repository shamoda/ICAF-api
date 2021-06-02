package com.application.icafapi.controller;

import com.application.icafapi.model.Attendee;
import com.application.icafapi.model.User;
import com.application.icafapi.service.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class AttendeeController {

    private final AttendeeService service;

    @Autowired
    public AttendeeController(AttendeeService service) {
        this.service = service;
    }

    @PostMapping("/attendee")
    public ResponseEntity<?> registerAttendee(@RequestParam("email") String email,
                                              @RequestParam("name") String name,
                                              @RequestParam("contact") String contact,
                                              @RequestParam("password") String password,
                                              @RequestParam("organization") String organization,
                                              @RequestParam("address") String address
                                              )
    {
        Attendee attendee = new Attendee(email, name, contact, organization, address);
        User user = new User(email, name, contact, "attendee", password);

        return new ResponseEntity<>(service.insertAttendee(attendee, user), HttpStatus.CREATED);
    }

    @GetMapping("/attendee")
    public ResponseEntity<?> getAllAttendees() {
        return new ResponseEntity<>(service.retrieveAllAttendees(), HttpStatus.OK);
    }


    @PostMapping("/attendee/filter")
    public ResponseEntity<?> getAttendeesByExample(@RequestBody Attendee attendee) {
        return new ResponseEntity<>(service.retrieveByExample(attendee), HttpStatus.OK);
    }

    @DeleteMapping("/attendee/{email}")
    public ResponseEntity<?> deleteAttendee(@PathVariable String email) {
        return new ResponseEntity<>(service.deleteAttendee(email), HttpStatus.OK);
    }

}
