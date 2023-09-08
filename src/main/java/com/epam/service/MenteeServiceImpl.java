package com.epam.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.MenteeDto;
import com.epam.exception.MenteeException;
import com.epam.model.Mentee;
import com.epam.repository.MenteeRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenteeServiceImpl implements MenteeService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private MenteeRepository menteeRepository;

	@Override
	public MenteeDto addMentee(MenteeDto menteeDto) {
		log.info("MenteeService : Add Mentee Method...");
		return modelMapper.map(menteeRepository.save(modelMapper.map(menteeDto, Mentee.class)), MenteeDto.class);

	}

	@Transactional
	@Override
	public void deleteMentee(int menteeId) {
		log.info("MenteeService : Delete Mentee Method...");
		menteeRepository.deleteById(menteeId);

	}

	@Override
	public List<MenteeDto> getAllMentees() {
		log.info("MenteeService : Get All Mentees Method...");
		return menteeRepository.findAll().stream().map(mentee -> modelMapper.map(mentee, MenteeDto.class)).toList();

	}

	@Override
	public MenteeDto getMentee(int menteeId) throws MenteeException {
		log.info("MenteeService : Get Mentee Method...");
		return menteeRepository.findById(menteeId).map(mentee -> modelMapper.map(mentee, MenteeDto.class))
				.orElseThrow(() -> new MenteeException("Mentee Not Found With This Mentee Id"));

	}

	@Override
	public MenteeDto updateMentee(int menteeId, MenteeDto menteeDto) throws MenteeException {
		log.info("MenteeService : Update Mentee Method...");
		return modelMapper.map(menteeRepository.findById(menteeId).map(mentee -> {
			menteeDto.setMenteeId(menteeId);
			menteeRepository.save(modelMapper.map(menteeDto, Mentee.class));
			return mentee;
		}).orElseThrow(() -> new MenteeException("Mentee not found....")), MenteeDto.class);

	}

}
