package com.ticket.reserve;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ticket.reserve.entity.User;
import com.ticket.reserve.exception.ErrorResponse;

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
		assertEquals(200, status);//verifying good request
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, inputJson);
	}
	
	@Test
	public void deleteUserThrowsExceptionIfNotPresent() throws Exception {
		String uri = "/api/v1/deleteUser/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		ErrorResponse resp = new ErrorResponse(400, "User Purchase details not Present hence cannot delete !!!!");
		String respJson = super.mapToJson(resp);
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);//verifying bad request
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, respJson);
	}

}
