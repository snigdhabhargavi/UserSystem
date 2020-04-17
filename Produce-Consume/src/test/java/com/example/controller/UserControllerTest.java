package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.entity.Address;
import com.example.entity.UserDTO;
import com.example.repo.UserServInt;
import com.example.service.KafkaConsumer;
import com.example.service.KafkaSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserServInt userint;
	
	@MockBean
	KafkaSender kafkaSender;
	
	@MockBean
	KafkaConsumer kafkaConsumer;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	Address a1 = new Address(101,"Home Address", "Hyderabad");
	Address a2 = new Address(102,"Office Address", "Bangalore");
	Address a3 = new Address(103,"Present Address", "Bangalore");
	Set<Address> addr1 = new HashSet<Address>();
	Address a4 = new Address(104,"Home Address", "Bangalore");
	Address a5 = new Address(105,"Office Address", "Chennai");
	Address a6 = new Address(106,"Present Address", "Chennai");
	Set<Address> addr2 = new HashSet<Address>();
	
	@Test
	void testGetAllusers() throws Exception {
		addr1.add(a1);
		addr1.add(a2);
		addr1.add(a3);
		UserDTO u1 = new UserDTO(1,"Sara", "sara@gmail.com", "sara", "Examiner", addr1);
		addr2.add(a4);
		addr2.add(a5);
		addr2.add(a6);
		UserDTO u2 = new UserDTO(2,"Tara", "tara@gmail.com", "tara", "Reporter", addr2);
		List<UserDTO> userlist = new ArrayList();
		userlist.add(u1);
		userlist.add(u2);
		ObjectMapper Obj = new ObjectMapper();
		String str1 = Obj.writeValueAsString(u1)+Obj.writeValueAsString(u2);
		when(userint.fetchAllUsers()).thenReturn(userlist);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/getallusers")
				.accept(MediaType.APPLICATION_JSON);
		
		
		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().json("[{\"id\":1,\"name\":\"Sara\",\"email\":\"sara@gmail.com\",\"password\":\"sara\",\"role\":\"Examiner\",\"address\":[{\"addressid\":103,\"addresstype\":\"Present Address\",\"address\":\"Bangalore\"},{\"addressid\":102,\"addresstype\":\"Office Address\",\"address\":\"Bangalore\"},{\"addressid\":101,\"addresstype\":\"Home Address\",\"address\":\"Hyderabad\"}]},{\"id\":2,\"name\":\"Tara\",\"email\":\"tara@gmail.com\",\"password\":\"tara\",\"role\":\"Reporter\",\"address\":[{\"addressid\":105,\"addresstype\":\"Office Address\",\"address\":\"Chennai\"},{\"addressid\":106,\"addresstype\":\"Present Address\",\"address\":\"Chennai\"},{\"addressid\":104,\"addresstype\":\"Home Address\",\"address\":\"Bangalore\"}]}]"))
				.andReturn();
		String str2 = result.getResponse().getContentAsString();
		System.out.println(str2);
	}

	@Test
	void testGetUserById() throws Exception {
		addr1.add(a1);
		addr1.add(a2);
		addr1.add(a3);
		UserDTO u1 = new UserDTO(1,"Sara", "sara@gmail.com", "sara", "Examiner", addr1);
		ObjectMapper Obj = new ObjectMapper();
		String str1 = Obj.writeValueAsString(u1);
		when(userint.fetchUserById(1)).thenReturn(u1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/getuser/1")
				.accept(MediaType.APPLICATION_JSON);
		
		
		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().json("{\"name\":\"Sara\",\"email\":\"sara@gmail.com\",\"password\":\"sara\",\"role\":\"Examiner\",\"address\":[{\"addressid\":101,\"addresstype\":\"Home Address\",\"address\":\"Hyderabad\"},{\"addressid\":102,\"addresstype\":\"Office Address\",\"address\":\"Bangalore\"},{\"addressid\":103,\"addresstype\":\"Present Address\",\"address\":\"Bangalore\"}]}"))
				.andReturn();
		assertEquals(str1, result.getResponse().getContentAsString());
	}

	@Test
	void testAddUser() throws Exception {
		
		addr1.add(a1);
		addr1.add(a2);
		addr1.add(a3);
		UserDTO u1 = new UserDTO(1,"Sara", "sara@gmail.com", "sara", "Examiner", addr1);
		ObjectMapper Obj = new ObjectMapper();
		Obj.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = Obj.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(u1);
		when(userint.addUser(u1)).thenReturn(u1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/adduser")
				.contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson)
				.accept(MediaType.APPLICATION_JSON);
		
		
		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk());
		System.out.println(requestJson);
	}

	@Test
	void testDeleteUserData() throws Exception {
		addr1.add(a1);
		addr1.add(a2);
		addr1.add(a3);
		UserDTO u1 = new UserDTO(1,"Sara", "sara@gmail.com", "sara", "Examiner", addr1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/deleteuser/1")
				.accept(MediaType.TEXT_PLAIN);

		mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Successfully"))
                .andReturn();
	}
}
