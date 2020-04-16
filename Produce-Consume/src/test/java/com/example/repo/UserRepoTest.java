package com.example.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.entity.Address;
import com.example.entity.User;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepoTest {

	@Autowired
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
	void testFindByEmail() {
		addr1.add(a1);
		addr1.add(a2);
		addr1.add(a3);
		User u1 = new User();
		u1.setName("Sara");
		u1.setEmail("sara@gmail.com");
		u1.setPassword("sara");
		u1.setRole("Examiner");
		u1.setAddress(addr1);
		urepo.save(u1);
		assertThat(u1.getId()).isNotNull();
		assertThat(urepo.findByEmail("sara@gmail.com")).isEqualTo(u1);
	}

	@Test
	void testFindAll() {
		addr1.add(a1);
		addr1.add(a2);
		addr1.add(a3);
		
		addr2.add(a4);
		addr2.add(a5);
		addr2.add(a6);
		
		User u1 = new User();
		u1.setName("Sara");
		u1.setEmail("sara@gmail.com");
		u1.setPassword("sara");
		u1.setRole("Examiner");
		u1.setAddress(addr1);
		
		User u2 = new User();
		u1.setName("Tara");
		u1.setEmail("tara@gmail.com");
		u1.setPassword("tara");
		u1.setRole("Reporter");
		u1.setAddress(addr2);
		
		List<User> userlist = new ArrayList();
		userlist.add(u1);
		userlist.add(u2);

		urepo.save(u1);
		urepo.save(u2);
		
		assertThat(urepo.findAll()).isEqualTo(userlist);
		assertThat(urepo.findAll().size()).isEqualTo(2);
	}

	@Test
	void testSave() {
		addr1.add(a1);
		addr1.add(a2);
		addr1.add(a3);
		
		User u1 = new User();
		u1.setName("Sara");
		u1.setEmail("sara@gmail.com");
		u1.setPassword("sara");
		u1.setRole("Examiner");
		u1.setAddress(addr1);
	
		assertThat(urepo.save(u1)).isEqualTo(u1);
	}

	@Test
	void testDelete() {
		addr1.add(a1);
		addr1.add(a2);
		addr1.add(a3);
		
		addr2.add(a4);
		addr2.add(a5);
		addr2.add(a6);	
		
		User u1 = new User();
		u1.setName("Sara");
		u1.setEmail("sara@gmail.com");
		u1.setPassword("sara");
		u1.setRole("Examiner");
		u1.setAddress(addr1);
		urepo.save(u1);
		
		User u2 = new User();
		u1.setName("Tara");
		u1.setEmail("tara@gmail.com");
		u1.setPassword("tara");
		u1.setRole("Reporter");
		u1.setAddress(addr2);
		urepo.save(u2);
		
		urepo.delete(u1);
		assertThat(urepo.findAll().size()).isEqualTo(1);
	}
}
