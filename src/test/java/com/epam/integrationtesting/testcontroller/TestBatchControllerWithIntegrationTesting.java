package com.epam.integrationtesting.testcontroller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import com.epam.dto.BatchDto;
import com.epam.dto.MenteeDto;
import com.epam.integrationtesting.dummyrepository.BatchDummyRepository;
import com.epam.integrationtesting.dummyrepository.MenteeDummyRepository;
import com.epam.model.Batch;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestBatchControllerWithIntegrationTesting {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private BatchDummyRepository dummyBatchRepository;
	@Autowired
	private MenteeDummyRepository dummyMenteeRepository;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/batch");
	}

	@Test
	void testAddBatch() {
		BatchDto batchDto = new BatchDto();
		batchDto.setBatchName("c+++");
		MenteeDto menteeDto = new MenteeDto();
		menteeDto.setEmail("sup@gmail.com");
		menteeDto.setMenteeName("supriya");
		;
		batchDto.setMentee(new ArrayList<>(List.of(menteeDto)));
		BatchDto response = restTemplate.postForObject(baseUrl, batchDto, BatchDto.class);
		assertEquals("c+++", response.getBatchName());
		assertEquals(1, dummyBatchRepository.findAll().size());
		dummyMenteeRepository.deleteAll();
		dummyBatchRepository.deleteAll();
	}

	@Test
	@Sql(statements = "INSERT INTO Batch (batch_id,batch_name) VALUES (1,'c#')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "INSERT INTO Batch (batch_id,batch_name) VALUES (2,'python')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM Batch WHERE batch_name='c#'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@Sql(statements = "DELETE FROM Batch WHERE batch_name='python'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testGetBatches() {
		@SuppressWarnings("unchecked")
		List<BatchDto> batches = restTemplate.getForObject(baseUrl, List.class);
		assertEquals(2, batches.size());
		assertEquals(2, dummyBatchRepository.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO Batch (batch_id,batch_name) VALUES (1,'c#')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM Batch WHERE batch_name='c#'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testGetBatchById() {
		BatchDto response = restTemplate.getForObject(baseUrl + "/{id}", BatchDto.class, 1);
		assertAll(() -> assertNotNull(response), () -> assertEquals("c#", response.getBatchName()));

	}

	@Test
	@Sql(statements = "INSERT INTO Batch (batch_id,batch_name) VALUES (1,'c#')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testDeleteBatch() {
		assertEquals(1, dummyBatchRepository.findAll().size());
		restTemplate.delete(baseUrl + "/{id}", 1);
		assertEquals(0, dummyBatchRepository.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO Batch (batch_id,batch_name) VALUES (1,'c#')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM Batch WHERE batch_name='python'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testUpdateBatchById() {
		BatchDto batchDto = new BatchDto();
		batchDto.setBatchId(1);
		batchDto.setBatchName("python");
		MenteeDto menteeDto = new MenteeDto();
		menteeDto.setEmail("sup@gmail.com");
		menteeDto.setMenteeName("supriya");
		batchDto.setMentee(new ArrayList<>(List.of(menteeDto)));
		restTemplate.put(baseUrl + "/1", batchDto, BatchDto.class);
		Batch response = dummyBatchRepository.findById(1).get();
		assertAll(() -> assertNotNull(response), () -> assertEquals("python", response.getBatchName()),
				() -> assertEquals(1, dummyBatchRepository.findAll().size()));
		dummyMenteeRepository.deleteAll();
		dummyBatchRepository.deleteAll();
	}

}
