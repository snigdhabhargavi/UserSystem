package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.UserDTO;

@Repository
public interface UserRepo extends JpaRepository<UserDTO, Integer>{

	UserDTO findByEmail(String string);

}
