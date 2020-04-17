package com.example.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.UserDTO;
import com.example.exceptionhandling.ResourceNotFoundException;
import com.example.repo.UserRepo;
import com.example.repo.UserServInt;

@Service
public class UserService implements UserServInt{
	
	@Autowired
	UserRepo urepo;
	
	@Override
	public List<UserDTO> fetchAllUsers() {
		return urepo.findAll();
	}

	@Override
	public UserDTO fetchUserById(int id) throws ResourceNotFoundException {
		return urepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this Id :: "+id));
	}

	@Override
	public UserDTO addUser(UserDTO user) {
		return urepo.save(user);
	}

	@Override
	public UserDTO updateUser(int id, UserDTO user) throws ResourceNotFoundException {
		UserDTO u = urepo.findById(id)
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
