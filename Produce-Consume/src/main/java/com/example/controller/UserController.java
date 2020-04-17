package com.example.controller;

import java.net.URL;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.entity.HottestRepositories;
import com.example.entity.User;
import com.example.entity.UserDTO;
import com.example.exceptionhandling.ResourceNotFoundException;
import com.example.repo.UserServInt;
import com.example.service.KafkaConsumer;
import com.example.service.KafkaSender;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="User Controller API")
public class UserController {
	@Autowired
	UserServInt userService; 
	
	@Autowired
	KafkaSender kafkaSender;
	
	@Autowired
	KafkaConsumer kafkaConsumer;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private RestTemplate template=new RestTemplate();
	
	@GetMapping("/hr")
	public HottestRepositories getRestData(){
		

		String hruri = "https://api.github.com/search/repositories?q=created%3E2020-03-03&sort=stars&order=desc";	
//		ObjectMapper mapper = new ObjectMapper();
//		HottestRepositories hr = mapper.readValue(new URL(hruri), HottestRepositories.class);
		return template.getForObject(hruri, HottestRepositories.class);
	}

	@GetMapping(value="/getallusers")
	@ApiOperation(value="Returns list of all users")
	public List<UserDTO> getAllusers(){
		logger.info("List of users {}", userService.fetchAllUsers());
		return userService.fetchAllUsers();
	}
	
	@GetMapping(value="/getuser/{id}")
	@ApiOperation(value="Returns a particular user")
	public UserDTO getUserById(@PathVariable(value="id") int userid) throws ResourceNotFoundException{
		UserDTO user = userService.fetchUserById(userid);
		logger.info("User Data {}", user);
		return user;
	}
	
	@PostMapping(value="/adduser")
	@ApiOperation(value="Add new user")
	public UserDTO addUserData(@RequestBody User user) {
		logger.info("Adding new user");
		UserDTO userdto = new UserDTO();
		BeanUtils.copyProperties(user, userdto);
		return userService.addUser(userdto);
	}
	
	@PutMapping(value="/updateuser/{id}")
	@ApiOperation(value="Updates user information")
	public UserDTO updateUserData(@PathVariable(value="id") int userid,@RequestBody User user) throws ResourceNotFoundException {
		 UserDTO u = userService.updateUser(userid, user);
		 logger.info("User data updated");
		 return u;
	}
	
	@DeleteMapping("/deleteuser/{id}")
	@ApiOperation(value="Deletes a particular user")
    public String deleteUserData(@PathVariable(value = "id") int userid)
         throws ResourceNotFoundException {
        userService.deleteUser(userid);
        logger.info("Deleted User information");
        return "Deleted Successfully";
    }
	
	@PostMapping("/exam/{message}")
	@ApiOperation(value="Publishes Exam details")
    public String sendExamDetails(@PathVariable("message") String message) {
        kafkaSender.sendExamMessage(message);
        logger.info("Exam details published");
        return "Exam details sent successfully";
    }
    
	@PostMapping("/news/{message}")
	@ApiOperation(value="Publishes News Information")
    public String sendNewsInformation(@PathVariable("message") String message) {
        kafkaSender.sendNewsInformation(message);
        logger.info("News Information published");
        return "News Information published successfully";
    }
	
    @GetMapping("/consumemessage")
    @ApiOperation(value="Consumes the published message")
    public String getMessage()
    {
       return  kafkaConsumer.getConsumedmessage();   
    }
}
