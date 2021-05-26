package com.application.icafapi.service;

import com.application.icafapi.model.User;
import com.application.icafapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
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
}
