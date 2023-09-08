package com.epam.service;

import java.util.List;

import com.epam.dto.MenteeDto;
import com.epam.exception.MenteeException;

public interface MenteeService {
	MenteeDto addMentee(MenteeDto menteeDto);

	void deleteMentee(int menteeId);

	List<MenteeDto> getAllMentees();

	MenteeDto getMentee(int menteeId) throws MenteeException;

	MenteeDto updateMentee(int menteeId,MenteeDto menteeDto) throws MenteeException;
}
