package com.example.repo;

import java.util.List;


import com.example.entity.UserDTO;
import com.example.exceptionhandling.ResourceNotFoundException;

public interface UserServInt {
	
	public List<UserDTO> fetchAllUsers();
	
	public UserDTO fetchUserById(int id) throws ResourceNotFoundException;
	
	public UserDTO addUser(UserDTO user);
	
    public UserDTO updateUser(int id, UserDTO user) throws ResourceNotFoundException;
    
    public void deleteUser(int id) throws ResourceNotFoundException;

}
