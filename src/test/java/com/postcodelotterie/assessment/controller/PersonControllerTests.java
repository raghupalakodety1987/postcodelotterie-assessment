package com.postcodelotterie.assessment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postcodelotterie.assessment.config.TestConfig;
import com.postcodelotterie.assessment.model.Person;
import com.postcodelotterie.assessment.repository.PersonRepository;
import com.postcodelotterie.assessment.service.PersonService;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Optional;


import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
//@WebMvcTest(value = PersonController.class, secure = false)
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
public class PersonControllerTests {


	private static Logger logger = LoggerFactory.getLogger(PersonControllerTests.class);

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PersonService personService;

	@Autowired
	private PersonRepository personRepository;

	@MockBean
	private PersonController personController;

	private Person person;

	private String personJson;

	@Autowired
	private WebApplicationContext webApplicationContext;
	private ResponseEntity ResponseEntity;

	@Before
	public void setUp() throws JsonProcessingException {
		//mockMvc = MockMvcBuilders.standaloneSetup(personService).build();
		//when(personService.findAll()).thenReturn(List.of(new
		//		Person(1L, "Raghunandan", "Palakodety", "07.11.1987"),
		//		new Person(2L, "Damian", "Deßler", "07.11.1987")));
		//Mockito.when(personService.findOne(any(Long.class))).thenReturn(person1).toString();
		person = new Person(1L, "Raghunandan", "Palakodety", "07.11.1987");
		ObjectMapper mapper = new ObjectMapper();
		personJson = mapper.writeValueAsString(person);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		//when(this.personService.save(any(Person.class))).thenReturn(person);
		when(personService.findById(1L)).thenReturn(Optional.ofNullable(person));


	}



	@Test
	public void testCreatePersons() throws Exception{

		//Mockito.when(personService.save(any(Person.class))).thenReturn(person1);
		given(this.personService.save(any(Person.class))).willReturn(person);

		this.mockMvc.perform(post("/api/person/create").content(personJson).
				contentType(MediaType.APPLICATION_JSON)).
				andExpect(status().isOk()).
				andDo(print()).andReturn();

		/*RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/person/create").contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = text;//"{id:1,firstName:Raghunandan,lastName:Palakodety,dob:07.11.1987}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString()., false);*/
		/*mockMvc.perform(MockMvcRequestBuilders.get("/api/person/").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(jsonObject.toString()));//string("{\"id\":2, \"firstName\":\"Raghunandan\", \"lastName\":\"Palakodety\",\"dob\":\"07.11.1987\"}"));*/
	}

	@Test
	public void testGetPersonById() throws Exception {
		///personRepository.save(person);
		//when(personService.findById(1L)).thenReturn(Optional.ofNullable(person));
		var expectedJson = "{\"id\" : 1, \"firstName\":\"Raghu\", \"lastName\":\"Palakodety\", \"dob\":\"07.11.1987\"}";
		//personRepository.save(person);
/*
		MvcResult result = this.mockMvc.perform(get("/api/person/{Id}", 1).
				accept(MediaType.APPLICATION_JSON)).
		andExpect(MockMvcResultMatchers.status().isOk()).
		andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.firstName", is("Raghunandan")))
				.andExpect(jsonPath("$.lastName", is("Palakodety")))
				.andExpect(jsonPath("$.dob", is("07.11.1987"))).andReturn();
		String response = result.getResponse().getContentAsString();
		//Integer id = JsonPath.parse(response).read("$.id");
		logger.info(response);*/

		MvcResult result = (MvcResult) this.mockMvc.perform(MockMvcRequestBuilders
				.get("/api/person/{Id}", 1).characterEncoding(StandardCharsets.UTF_8.name())
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(/*MockMvcResultMatchers.jsonPath("$.id").value(1L)*/ content().string(expectedJson));

		String content = result.getResponse().getContentAsString();

		logger.info(content);


		/*mockMvc.perform(get("/api/person/{Id}", 1))
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(jsonPath("$.id").value("1"))
				.andExpect(jsonPath("$.firstName").value("Raghunandan"))
				.andExpect(jsonPath("$.lastName").value("Palakodety"))
		.andExpect(jsonPath("$.dob").value("07.11.1987"));*/
	}

	@Test
	public void whenDeleteFromCustomQuery_thenDeletingShouldBeSuccessful() throws Exception {
/*		this.personService.deleteById(1L);
		assertThat(this.personService.findById(1L)).isEqualTo(Optional.empty());*/
		//when(this.personService.save(any(Person.class))).thenReturn(person);
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/person/{id}/delete", "1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void whenSaved_thenShouldbeSuccessful() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/person/save").contentType(MediaType.APPLICATION_JSON)
				.content(personJson))
				.andExpect(status().isOk())
				.andDo(print());
	}

}