package com.application.icafapi.service;

import com.application.icafapi.common.util.EmailUtil;
import com.application.icafapi.model.Researcher;
import com.application.icafapi.model.User;
import com.application.icafapi.repository.ResearcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.application.icafapi.common.constant.Email.*;

@Service
public class ResearcherService {

    private final ResearcherRepository repository;
    private final UserService userService;
    private final FileService fileService;
    private final EmailUtil emailUtil;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ResearcherService(ResearcherRepository repository, UserService userService, FileService fileService, EmailUtil emailUtil, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.userService = userService;
        this.fileService = fileService;
        this.emailUtil = emailUtil;
        this.mongoTemplate = mongoTemplate;
    }

    public Researcher insertResearcher(Researcher researcher, User user, MultipartFile mFile) {
        fileService.uploadFile(mFile, researcher.getFileName(), "paper");
        userService.insertUser(user);
        emailUtil.sendEmail(user.getEmail(), USER_REGISTRATION_SUBJECT, RESEARCHER_REGISTRATION_BODY);
        return repository.save(researcher);
    }

    public List<Researcher> retrieveAllResearchers() {
        return repository.findAll();
    }

    public List<Researcher> retrieveByExample(Researcher researcher) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withMatcher("title", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
        Example<Researcher> example = Example.of(researcher, matcher);
        return repository.findAll(example);
    }

    public String reviewSubmission(String email, String status, String rComment) {
        if(status.equals("approved")) {
            emailUtil.sendEmail(email, SUBMISSION_STATUS_SUBJECT, SUBMISSION_APPROVED_BODY+COMMITTEE_REGISTRATION_END);
        } else if (status.equals("rejected")) {
            emailUtil.sendEmail(email, SUBMISSION_STATUS_SUBJECT, SUBMISSION_REJECTED_BODY+COMMITTEE_REGISTRATION_END);
        }
        Researcher researcher = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(email)), Researcher.class);
        researcher.setStatus(status);
        researcher.setRComment(rComment);
        mongoTemplate.save(researcher);
        return "reviewed";
    }

    public Researcher retrievePaper(String email) {
        return repository.findById(email).get();
    }

}
