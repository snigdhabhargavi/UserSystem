package com.example.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.entity.Address;
import com.example.entity.User;
import com.example.repo.UserRepo;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class UserServiceTest {

	@InjectMocks
	UserService userService;
	
	@Mock
	UserRepo urepo;
	
	Address a1 = new Address(101,"Home Address", "Hyderabad");
	Address a2 = new Address(102,"Office Address", "Bangalore");
	Address a3 = new Address(103,"Present Address", "Bangalore");
	Set<Address> addr1 = new HashSet<Address>();
	Address a4 = new Address(104,"Home Address", "Bangalore");
	Address a5 = new Address(105,"Office Address", "Chennai");
	Address a6 = new Address(106,"Present Address", "Chennai");
	Set<Address> addr2 = new HashSet<Address>();
	
	@Test
	void testFetchAllUsers() throws Exception{
		addr1.add(a1);
		addr1.add(a2);
		addr1.add(a3);
		addr2.add(a4);
		addr2.add(a5);
		addr2.add(a6);
		when(urepo.findAll()).thenReturn(
				Arrays.asList(
						new User(1,"Sara", "sara@gmail.com", "sara", "Examiner", addr1),
						new User(2,"Tara", "tara@gmail.com", "tara", "Reporter", addr2)
					)
				);
		List<User> userlist = userService.fetchAllUsers();
		assertEquals(1,userlist.get(0).getId());
		assertEquals(2,userlist.size());
	}

	@Test
	void testFetchUserById() throws Exception{
		addr1.add(a1);
		addr1.add(a2);
		addr1.add(a3);
		User u1 = new User(1,"Sara", "sara@gmail.com", "sara", "Examiner", addr1);
		when(
				urepo.findByEmail("sara@gmail.com")).thenReturn(u1);
		assertEquals(u1, urepo.findByEmail("sara@gmail.com"));
	}

	@Test
	void testAddUser() {
		addr1.add(a1);
		addr1.add(a2);
		addr1.add(a3);
		User u1 = new User(1,"Sara", "sara@gmail.com", "sara", "Examiner", addr1);
		urepo.save(u1);
		verify(urepo, times(1)).save(u1);
		when(urepo.save(u1)).thenReturn(u1);
		assertEquals(u1,urepo.save(u1));
	}
	

	@Test
	void testDeleteUser() {
		addr1.add(a1);
		addr1.add(a2);
		addr1.add(a3);
		User u1 = new User(1,"Sara", "sara@gmail.com", "sara", "Examiner", addr1);
		urepo.deleteById(1);
		verify(urepo, times(1)).deleteById(1);
	}
}
