package com.ticket.reserve;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ticket.reserve.entity.User;

public class UserControllerTests extends AbstractTest {


	@Test
	public void createUser() throws Exception {
		String uri = "/api/v1/saveUser";
		User user = new User();
		user.setUserId(1L);
		user.setFirstName("Balaji");
		user.setLastName("OSM");
		user.setEmailAddress("balajiosm@gmail.com");
		String inputJson = super.mapToJson(user);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		//assertEquals(content, "User is created successfully");
		assertEquals(content, inputJson);
	}

}
