package com.application.icafapi.service;

import com.application.icafapi.model.Attendee;
import com.application.icafapi.model.User;
import com.application.icafapi.repository.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendeeService {

    private final AttendeeRepository repository;
    private final UserService userService;

    @Autowired
    public AttendeeService(AttendeeRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public Attendee insertAttendee(Attendee attendee, User user) {
        userService.insertUser(user);
        return repository.save(attendee);
    }
}
