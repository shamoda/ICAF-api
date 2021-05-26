package com.application.icafapi.service;

import com.application.icafapi.common.util.EmailUtil;
import com.application.icafapi.model.Researcher;
import com.application.icafapi.model.User;
import com.application.icafapi.repository.ResearcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

    @Autowired
    public ResearcherService(ResearcherRepository repository, UserService userService, FileService fileService, EmailUtil emailUtil) {
        this.repository = repository;
        this.userService = userService;
        this.fileService = fileService;
        this.emailUtil = emailUtil;
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
        Example<Researcher> example = Example.of(researcher);
        return repository.findAll(example);
    }

}
