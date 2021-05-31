package com.application.icafapi.service;

import com.application.icafapi.model.User;
import com.application.icafapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserService(UserRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
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

    public String changePassword(String email, String password) {
        User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(email)), User.class);
        user.setPassword(password);
        mongoTemplate.save(user);
        return "Password Changed";
    }
}
