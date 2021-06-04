package com.application.icafapi.service;

import com.application.icafapi.common.util.EmailUtil;
import com.application.icafapi.model.User;
import com.application.icafapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.application.icafapi.common.constant.Email.*;

@Service
public class CommitteeService {

    private final UserRepository repository;
    private final EmailUtil emailUtil;

    @Autowired
    public CommitteeService(UserRepository repository, EmailUtil emailUtil) {
        this.repository = repository;
        this.emailUtil = emailUtil;
    }

    public User insertCommitteeMember(User user) {
        if (user.getRole().equals("reviewer")) {
            emailUtil.sendEmail(user.getEmail(), COMMITTEE_REGISTRATION_SUBJECT, REVIEWER_REGISTRATION_BODY+user.getPassword()+COMMITTEE_REGISTRATION_END);
        } else if (user.getRole().equals("editor")) {
            emailUtil.sendEmail(user.getEmail(), COMMITTEE_REGISTRATION_SUBJECT, EDITOR_REGISTRATION_BODY+user.getPassword()+COMMITTEE_REGISTRATION_END);
        } else if (user.getRole().equals("admin")) {
            emailUtil.sendEmail(user.getEmail(), COMMITTEE_REGISTRATION_SUBJECT, ADMIN_REGISTRATION_BODY+user.getPassword()+COMMITTEE_REGISTRATION_END);
        }
        return repository.save(user);
    }

}
