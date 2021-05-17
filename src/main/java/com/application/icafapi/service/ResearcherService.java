package com.application.icafapi.service;

import com.application.icafapi.model.Researcher;
import com.application.icafapi.model.User;
import com.application.icafapi.repository.ResearcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResearcherService {

    private final ResearcherRepository repository;
    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public ResearcherService(ResearcherRepository repository, UserService userService, FileService fileService) {
        this.repository = repository;
        this.userService = userService;
        this.fileService = fileService;
    }

    public Researcher insertResearcher(Researcher researcher, User user, MultipartFile mFile) {
        fileService.uploadFile(mFile, researcher.getFileName(), "paper");
        userService.insertUser(user);
        return repository.save(researcher);
    }

}
