package com.example.demoasync;

import com.example.demoasync.controller.JokeController;
import com.example.demoasync.model.Joke;
import com.example.demoasync.service.JokeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(value = JokeController.class)
class DemoAsyncApplicationTests {

	private static final Joke testJoke = new Joke (30,"type",  "setup",  "punchline");
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private JokeService jokeService;
	@Test
	void getJokeTest() throws Exception{
		Mockito.when(jokeService.getJoke()).thenReturn(testJoke);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/jokes")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

}
