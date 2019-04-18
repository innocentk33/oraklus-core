package com.ebenyx.oraklus.security.web;

import com.ebenyx.oraklus.Application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class AuthenticationRestControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
	}

	private String obtainAccessToken(String username, String password) throws Exception {

		StringBuilder sb = new StringBuilder();
		sb.append("{\"username\":\"").append(username).append("\",\"password\":\"").append(password).append("\"}");
		String body = sb.toString();

		ResultActions result = mockMvc.perform(post("/auth")
			.contentType("application/json;charset=UTF-8")
			.content(body)
			.accept("application/json;charset=UTF-8"))
			.andExpect(status().isOk());

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("token").toString();
	}

	@Test
	public void test() throws Exception {

		String body = "{\"username\":\"brice.beda@gmail.com\",\"password\":\"12345\"}";

		mockMvc.perform(post("/auth")
			.contentType("application/json;charset=UTF-8")
			.content(body)
			.accept("application/json;charset=UTF-8"))
			.andExpect(status().is2xxSuccessful());

		body = "{\"username\":\"brice.beda@gmail.com\",\"password\":\"012345\"}";

		mockMvc.perform(post("/auth")
			.contentType("application/json;charset=UTF-8")
			.content(body)
			.accept("application/json;charset=UTF-8"))
			.andExpect(status().isUnauthorized());

		String accessToken = obtainAccessToken("brice.beda@gmail.com", "12345");

		mockMvc.perform(get("/refresh")
			.header("X-Auth-Token", "X-Token-Bearer " + accessToken))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$.username", is("brice.beda@gmail.com")))
			.andExpect(jsonPath("$.fullName", is("Brice-Boris BEDA")))
			.andExpect(jsonPath("$.email", is("brice.beda@gmail.com")))
			.andExpect(jsonPath("$.authorities", is("ROLE_ADMIN,ROLE_USER")));
	}
}
