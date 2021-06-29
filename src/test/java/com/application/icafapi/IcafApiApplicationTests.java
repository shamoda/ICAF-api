package com.application.icafapi;

import com.application.icafapi.model.User;
import com.application.icafapi.repository.UserRepository;
import com.application.icafapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class IcafApiApplicationTests {

//	@Autowired
//	private UserService userService;
//	@MockBean
//	private UserRepository userRepository;
//	//[GET -TEST CASE 01] Retrieving all the users
//	@Test
//	public void retrieveUsersTest(){
//		 //Calling repository method and returning output
//		 when(userRepository.findAll()).thenReturn(Stream
//		 .of(new User("test1@gmail.com","Jack","0777772323","Admin","test123"),
//				 new User("test1@gmail.com","Jack","0777772323","Admin","test123")).collect(Collectors.toList()));
//		 //comparing the no of outputs
//		 assertEquals(2, userService.retrieveAllUsers().size());
//	}
//
//	//[POST -TEST CASE 02] Creating a user
//	@Test
//	public void saveUser(){
//		//User object definition
//		User user = new User("test1@gmail.com","Jack","0777772323","Admin","test123");
//		//saving the user with MongoRepository method save()
//		when(userRepository.save(user)).thenReturn(user);
//		//Comparing return user and returned insert user through service method
//		assertEquals(user, userService.insertUser(user));
//	}
//
//	//[DELETE -TEST CASE 03] Deleting a user
//	@Test
//	public void deleteUser(){
//		//User object definition
//		User user = new User("test1@gmail.com","Jack","0777772323","Admin","test123");
//		//Calling delete method in service
//		userService.deleteUser(user);
//		//Verifying time of method calls through repository
//		verify(userRepository ,times(1)).delete(user); //verify how many times called
//	}
}
