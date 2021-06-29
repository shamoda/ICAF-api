package com.application.icafapi.controller;

import com.application.icafapi.exception.ICAFException;
import com.application.icafapi.model.User;
import com.application.icafapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(service.insertUser(user), HttpStatus.CREATED);
        } catch (ICAFException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(service.retrieveAllUsers(), HttpStatus.OK);
    }
    @PostMapping("/user/filter")
    public ResponseEntity<?> getUsersByExample(@RequestBody User user) {
        return new ResponseEntity<>(service.retrieveByExample(user), HttpStatus.OK);
    }

    @GetMapping("/user/login/{email}")
    public ResponseEntity<?> login( @PathVariable String email )
    {
        return new ResponseEntity<>(service.login(email), HttpStatus.OK);
    }

    @PostMapping("/user/changepassword")
    public ResponseEntity<?> changePassword(@RequestParam("email") String email,
                                            @RequestParam("password") String password
                                            )
    {
        return new ResponseEntity<>(service.changePassword(email, password), HttpStatus.OK);
    }

    @PostMapping("/user/filter/admin")
    public ResponseEntity<?> getUsersByExampleAdmin(@RequestBody User user) {
        return new ResponseEntity<>(service.retrieveUsersByExample(user), HttpStatus.OK);
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        return new ResponseEntity<>(service.deleteUserByEmail(email), HttpStatus.OK);
    }

}
