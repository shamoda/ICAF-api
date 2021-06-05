package com.application.icafapi.service;

import com.application.icafapi.common.util.EmailUtil;
import com.application.icafapi.model.Attendee;
import com.application.icafapi.model.User;
import com.application.icafapi.repository.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.application.icafapi.common.constant.Email.*;

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
        emailUtil.sendQR(user.getName(), user.getEmail(), QR_SUBJECT, QR_BODY+COMMITTEE_REGISTRATION_END, user.getRole());
        return repository.save(attendee);
    }

    public List<Attendee> retrieveAllAttendees() {
        return repository.findAll();
    }

    public List<Attendee> retrieveByExample(Attendee attendee) {
        Example<Attendee> example = Example.of(attendee);
        return repository.findAll(example);
    }

    public String deleteAttendee(String email) {
        userService.deleteUserByEmail(email);
        repository.deleteById(email);
        emailUtil.sendEmail(email, ACCOUNT_REMOVAL_SUBJECT, ACCOUNT_REMOVAL_BODY+COMMITTEE_REGISTRATION_END);
        return "Attendee deleted";
    }
}
