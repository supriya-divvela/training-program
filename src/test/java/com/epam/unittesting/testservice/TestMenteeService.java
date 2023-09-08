package com.epam.unittesting.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.epam.dto.BatchDto;
import com.epam.dto.MenteeDto;
import com.epam.exception.MenteeException;
import com.epam.model.Batch;
import com.epam.model.Mentee;
import com.epam.repository.MenteeRepository;
import com.epam.service.MenteeServiceImpl;

@ExtendWith(MockitoExtension.class)
class TestMenteeService {
	@Mock
	private MenteeRepository menteeRepository;
	@Mock
	private ModelMapper modelMapper;
	@InjectMocks
	private MenteeServiceImpl menteeService;
	MenteeDto menteeDto;
	BatchDto batchDto;
	Batch batch;
	Mentee mentee;
	List<MenteeDto> menteeDtos = new ArrayList<>();
	List<Mentee> mentees = new ArrayList<>();

	@BeforeEach
	void menteeDetails() {
		menteeDto = new MenteeDto();
		menteeDto.setMenteeId(1);
		menteeDto.setMenteeName("Supriya");
		menteeDto.setEmail("supriya_divvela@epam.com");
		mentee = new Mentee();
		mentee.setMenteeId(1);
		mentee.setMenteeName("Supriya");
		mentee.setEmail("supriya_divvela@epam.com");
		menteeDtos.add(menteeDto);
		mentees.add(mentee);
	}

	@Test
	void testAddMentee() throws MenteeException {
		when(modelMapper.map(menteeDto, Mentee.class)).thenReturn(mentee);
		when(menteeRepository.save(Mockito.any(Mentee.class))).thenReturn(mentee);
		menteeService.addMentee(menteeDto);
		verify(menteeRepository).save(mentee);
	}

	@Test
	void testDeleteMentee() {
		doNothing().when(menteeRepository).deleteById(1);
		menteeService.deleteMentee(1);
		verify(menteeRepository).deleteById(1);
	}

	@Test
	void testGetAllMentees() {
		when(menteeRepository.findAll()).thenReturn(mentees);
		when(modelMapper.map(mentee, MenteeDto.class)).thenReturn(menteeDto);
		assertEquals(1, menteeService.getAllMentees().size());

	}

	@Test
	void testGetMentee() throws MenteeException {
		when(menteeRepository.findById(1)).thenReturn(Optional.of(mentee));
		when(modelMapper.map(mentee, MenteeDto.class)).thenReturn(menteeDto);
		assertEquals(menteeDto, menteeService.getMentee(1));
	}

	@Test
	void testGetMenteeWithMenteeException() throws MenteeException {
		when(menteeRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(MenteeException.class,()->menteeService.getMentee(1));
	}

	@Test
	void testUpdateMentee() throws MenteeException {
		when(menteeRepository.findById(0)).thenReturn(Optional.of(mentee));
		when(modelMapper.map(menteeDto, Mentee.class)).thenReturn(mentee);
		when(menteeRepository.save(Mockito.any(Mentee.class))).thenReturn(mentee);
		menteeService.updateMentee(0, menteeDto);
		verify(menteeRepository).save(mentee);
	}

	@Test
	void testUpdateMenteeWithException() throws MenteeException {
		when(menteeRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(MenteeException.class,()->menteeService.updateMentee(1,menteeDto));
	}
}