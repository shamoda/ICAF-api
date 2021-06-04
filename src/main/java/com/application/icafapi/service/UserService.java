package com.application.icafapi.service;

import com.application.icafapi.common.util.EmailUtil;
import com.application.icafapi.model.User;
import com.application.icafapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.application.icafapi.common.constant.Email.*;

@Service
public class UserService {

    private final UserRepository repository;
    private final MongoTemplate mongoTemplate;
    private final EmailUtil emailUtil;

    @Autowired
    public UserService(UserRepository repository, MongoTemplate mongoTemplate, EmailUtil emailUtil) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
        this.emailUtil = emailUtil;
    }

    public User insertUser(User user) {
        return repository.save(user);
    }

    public List<User> retrieveAllUsers() {
        return repository.findAll();
    }

    public List<User> retrieveByExample(User user) {
        Example<User> example = Example.of(user);
        return repository.findAll(example);
    }

    public User login(String email, String password) {
        User user = repository.findByEmail(email);
        if(user == null) {
            return null;
        } else if (!user.getPassword().equals(password)) {
            return null;
        }
        else
            return user;
    }
     //test
    public void deleteUser(User user){
        repository.delete(user);
    }

    public List<User> retrieveUsersByExample(User user) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withMatcher("name", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
        Example<User> example = Example.of(user, matcher);
        return repository.findAll(example);
    }

    public String deleteUserByEmail(String email) {
        repository.deleteById(email);
        emailUtil.sendEmail(email, ACCOUNT_REMOVAL_SUBJECT, ACCOUNT_REMOVAL_BODY+COMMITTEE_REGISTRATION_END);
        return "User deleted";
    }

    public String changePassword(String email, String password) {
        User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(email)), User.class);
        user.setPassword(password);
        mongoTemplate.save(user);
        return "Password Changed";
    }
}
