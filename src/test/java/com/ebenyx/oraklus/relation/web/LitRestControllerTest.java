package com.ebenyx.oraklus.relation.web;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class LitRestControllerTest {

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

		String accessToken = obtainAccessToken("brice.beda@gmail.com", "12345");

		String body = "{\"key1\":\"value1\",\"key2\":\"value2\"}";

		mockMvc.perform(post("/relation/lit/add")
			.header("X-Auth-Token", "X-Token-Bearer " + accessToken)
			.contentType("application/json;charset=UTF-8")
			.content(body)
			.accept("application/json;charset=UTF-8"))
			.andExpect(status().isOk());

		mockMvc.perform(delete("/relation/lit/delete")
			.header("X-Auth-Token", "X-Token-Bearer " + accessToken)
			.param("id", "1"))
			.andExpect(status().is2xxSuccessful());

		mockMvc.perform(get("/relation/lit/find-one")
			.header("X-Auth-Token", "X-Token-Bearer " + accessToken)
			.param("id", "1"))
			.andExpect(status().isOk());

		mockMvc.perform(get("/relation/lit/load")
			.header("X-Auth-Token", "X-Token-Bearer " + accessToken))
			.andExpect(status().isOk());
	}
}
