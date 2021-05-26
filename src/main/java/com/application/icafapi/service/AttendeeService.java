package com.application.icafapi.service;

import com.application.icafapi.common.util.EmailUtil;
import com.application.icafapi.model.Attendee;
import com.application.icafapi.model.User;
import com.application.icafapi.repository.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.application.icafapi.common.util.constant.Email.*;

@Service
public class AttendeeService {

    private final AttendeeRepository repository;
    private final UserService userService;
    private final EmailUtil emailUtil;

    @Autowired
    public AttendeeService(AttendeeRepository repository, UserService userService, EmailUtil emailUtil) {
        this.repository = repository;
        this.userService = userService;
        this.emailUtil = emailUtil;
    }

    public Attendee insertAttendee(Attendee attendee, User user) {
        userService.insertUser(user);
        emailUtil.sendEmail(user.getEmail(), USER_REGISTRATION_SUBJECT, ATTENDEE_REGISTRATION_BODY);
        return repository.save(attendee);
    }
}
