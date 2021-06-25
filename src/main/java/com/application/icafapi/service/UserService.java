package com.application.icafapi.service;

import com.application.icafapi.common.util.EmailUtil;
import com.application.icafapi.model.CustomUserDetails;
import com.application.icafapi.model.User;
import com.application.icafapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.application.icafapi.common.constant.Email.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final MongoTemplate mongoTemplate;
    private final EmailUtil emailUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository repository, MongoTemplate mongoTemplate, EmailUtil emailUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
        this.emailUtil = emailUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String insertUser(User user) {
        User user1 = repository.findByEmail(user.getEmail());
        //checking whether user is preregistered
        if(user1 != null){
            return null; //could throw an exception also
        }
        //encoding password and setting
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repository.save(user);
        return "Registration of user is successful!!";
    }

    public List<User> retrieveAllUsers() {
        return repository.findAll();
    }

    public List<User> retrieveByExample(User user) {
        Example<User> example = Example.of(user);
        return repository.findAll(example);
    }

    public User login(String email) {
        User user = repository.findByEmail(email);
        if(user == null) {
            return null;
        } else
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
        user.setPassword(bCryptPasswordEncoder.encode(password));
        mongoTemplate.save(user);
        return "Password Changed";
    }

    @Override //overriding loadUserByName method
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if(user==null){
            throw  new UsernameNotFoundException("User Not Found");
        }
        return new CustomUserDetails(user);
    }
}
