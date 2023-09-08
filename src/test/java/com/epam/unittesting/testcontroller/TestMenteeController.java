package com.epam.unittesting.testcontroller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.controller.MenteeController;
import com.epam.dto.MenteeDto;
import com.epam.exception.MenteeException;
import com.epam.service.MenteeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MenteeController.class)
class TestMenteeController {
	@MockBean
	private MenteeService menteeService;
	@Autowired
	private MockMvc mockMvc;
	MenteeDto menteeDto;
	List<MenteeDto> mentees = new ArrayList<>();

	@BeforeEach
	void menteeDetails() {
		menteeDto = new MenteeDto();
		menteeDto.setMenteeId(1);
		menteeDto.setMenteeName("Supriya");
		menteeDto.setEmail("supriya_divvela@epam.com");
		mentees.add(menteeDto);
	}

	@Test
	void testGetAllMentees() throws Exception {
		when(menteeService.getAllMentees()).thenReturn(mentees);
		mockMvc.perform(get("/mentee").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testGetMentee() throws Exception {
		when(menteeService.getMentee(1)).thenReturn(menteeDto);
		mockMvc.perform(get("/mentee/{id}",1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testGetMenteeWithException() throws Exception {
		when(menteeService.getMentee(12)).thenThrow(MenteeException.class);
		mockMvc.perform(get("/mentee/{id}",12).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	void testAddMentee() throws JsonProcessingException, Exception {
		when(menteeService.addMentee(Mockito.any(MenteeDto.class))).thenReturn(menteeDto);
		mockMvc.perform(post("/mentee").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(menteeDto))).andExpect(status().isCreated());
	}

	@Test
	void testDeleteMentee() throws JsonProcessingException, Exception {
		doNothing().when(menteeService).deleteMentee(1);
		mockMvc.perform(delete("/mentee/{id}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	void testUpdateMentee() throws JsonProcessingException, Exception {
		when(menteeService.updateMentee(1, menteeDto)).thenReturn(menteeDto);
		mockMvc.perform(put("/mentee/{id}", 1).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(menteeDto))).andExpect(status().isOk());
	}
}
