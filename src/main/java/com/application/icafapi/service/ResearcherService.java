package com.application.icafapi.service;

import com.application.icafapi.common.util.EmailUtil;
import com.application.icafapi.model.Researcher;
import com.application.icafapi.model.User;
import com.application.icafapi.repository.ResearcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.application.icafapi.common.util.constant.Email.*;

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

}
