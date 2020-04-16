package com.example.repo;

import java.util.List;


import com.example.entity.User;
import com.example.exceptionhandling.ResourceNotFoundException;

public interface UserServInt {
	
	public List<User> fetchAllUsers();
	
	public User fetchUserById(int id) throws ResourceNotFoundException;
	
	public User addUser(User user);
	
    public User updateUser(int id, User user) throws ResourceNotFoundException;
    
    public void deleteUser(int id) throws ResourceNotFoundException;

}
