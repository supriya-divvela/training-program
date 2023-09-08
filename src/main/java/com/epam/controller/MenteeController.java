package com.epam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.MenteeDto;
import com.epam.exception.BatchException;
import com.epam.service.MenteeService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/mentee")
@Slf4j
public class MenteeController {
	@Autowired
	private MenteeService menteeService;

	@GetMapping
	public ResponseEntity<List<MenteeDto>> getAllMentees() {
		log.info("MenteeApi : Get All Mentees Method..");
		return new ResponseEntity<>(menteeService.getAllMentees(), HttpStatus.OK);
	}

	@GetMapping("/{menteeId}")
	public ResponseEntity<MenteeDto> getMentee(@PathVariable("menteeId") int menteeId) throws BatchException {
		log.info("MenteeApi : Get Mentee Method..");
		return new ResponseEntity<>(menteeService.getMentee(menteeId), HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<MenteeDto> addMentee(@RequestBody @Valid MenteeDto menteeDto) throws BatchException {
		log.info("MenteeApi : Add Mentee Method..");
		return new ResponseEntity<>(menteeService.addMentee(menteeDto), HttpStatus.CREATED);
	}

	@DeleteMapping("{menteeId}")
	public ResponseEntity<Void> deleteMentee(@PathVariable("menteeId") int menteeId) {
		log.info("MenteeApi : delete Mentee Method..");
		menteeService.deleteMentee(menteeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{menteeId}")
	public ResponseEntity<MenteeDto> updateMentee(@PathVariable("menteeId") int menteeId, @RequestBody @Valid MenteeDto menteeDto)
			throws BatchException {
		log.info("MenteeApi : update Mentee Method..");
		return new ResponseEntity<>(menteeService.updateMentee(menteeId, menteeDto), HttpStatus.OK);
	}
}
