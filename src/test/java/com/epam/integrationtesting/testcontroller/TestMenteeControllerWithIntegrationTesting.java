package com.epam.integrationtesting.testcontroller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import com.epam.dto.MenteeDto;
import com.epam.integrationtesting.dummyrepository.MenteeDummyRepository;
import com.epam.model.Mentee;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMenteeControllerWithIntegrationTesting {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private MenteeDummyRepository dummyMenteeRepository;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/mentee");
	}

	@Test
	void testAddMentee() {
		MenteeDto menteeDto = new MenteeDto();
		menteeDto.setMenteeId(1);
		menteeDto.setEmail("sup@gmail.com");
		menteeDto.setMenteeName("supriya");
		MenteeDto response = restTemplate.postForObject(baseUrl, menteeDto, MenteeDto.class);
		assertEquals("sup@gmail.com", response.getEmail());
		assertEquals(1, dummyMenteeRepository.findAll().size());
		dummyMenteeRepository.deleteAll();
	}

	@Test
	@Sql(statements = "INSERT INTO Mentee (mentee_id,mentee_name,email) VALUES (1,'supriya','sup@gmail.com')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "INSERT INTO Mentee (mentee_id,mentee_name,email) VALUES (2,'pavan','pavan@gmail.com')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM Mentee WHERE mentee_name='supriya'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@Sql(statements = "DELETE FROM Mentee WHERE mentee_name='pavan'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testGetMentees() {
		@SuppressWarnings("unchecked")
		List<MenteeDto> mentees = restTemplate.getForObject(baseUrl, List.class);
		assertEquals(2, mentees.size());
		assertEquals(2, dummyMenteeRepository.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO Mentee (mentee_id,mentee_name,email) VALUES (1,'supriya','sup@gmail.com')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM Mentee WHERE mentee_name='supriya'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testGetMenteeById() {
		MenteeDto response = restTemplate.getForObject(baseUrl + "/{menteeId}", MenteeDto.class, 1);
		assertAll(() -> assertNotNull(response), () -> assertEquals("supriya", response.getMenteeName()));

	}

	@Test
	@Sql(statements = "INSERT INTO Mentee (mentee_id,mentee_name,email) VALUES (1,'supriya','sup@gmail.com')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testDeleteMentee() {
		assertEquals(1, dummyMenteeRepository.findAll().size());
		restTemplate.delete(baseUrl + "/{menteeId}", 1);
		assertEquals(0, dummyMenteeRepository.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO Mentee (mentee_id,mentee_name,email) VALUES (1,'supriya','sup@gmail.com')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM Mentee WHERE mentee_name='sup'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testUpdateMenteeById() {
		MenteeDto menteeDto = new MenteeDto();
		menteeDto.setEmail("supriyadivvela@gmail.com");
		menteeDto.setMenteeName("sup");
		restTemplate.put(baseUrl + "/1", menteeDto, MenteeDto.class);
		Mentee response = dummyMenteeRepository.findById(1).get();
		assertAll(() -> assertNotNull(response), () -> assertEquals("supriyadivvela@gmail.com", response.getEmail()),
				() -> assertEquals(1, dummyMenteeRepository.findAll().size()));
		dummyMenteeRepository.deleteAll();
	}

}
