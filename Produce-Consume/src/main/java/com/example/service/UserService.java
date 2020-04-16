package com.example.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.exceptionhandling.ResourceNotFoundException;
import com.example.repo.UserRepo;
import com.example.repo.UserServInt;

@Service
public class UserService implements UserServInt{
	
	@Autowired
	UserRepo urepo;
	
	@Override
	public List<User> fetchAllUsers() {
		return urepo.findAll();
	}

	@Override
	public User fetchUserById(int id) throws ResourceNotFoundException {
		User user = urepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this Id :: "+id));
		return user;
	}

	@Override
	public User addUser(User user) {
		User u = urepo.save(user);
		return u;
	}

	@Override
	public User updateUser(int id, User user) throws ResourceNotFoundException {
		User u = urepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this Id :: "+id));
		if(u!=null) {
			u.setName(user.getName());
			u.setEmail(user.getEmail());
			u.setPassword(user.getPassword());
			u.setRole(user.getRole());
			u.setAddress(user.getAddress());
			urepo.save(u);
		}
		return u;
	}

	@Override
	public void deleteUser(int id) throws ResourceNotFoundException {
		urepo.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User not found for this Id :: "+id));
		urepo.deleteById(id);
	}
}
