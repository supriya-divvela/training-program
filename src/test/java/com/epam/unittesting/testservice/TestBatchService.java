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
import com.epam.exception.BatchException;
import com.epam.model.Batch;
import com.epam.model.Mentee;
import com.epam.repository.BatchRepository;
import com.epam.service.BatchServiceImpl;

@ExtendWith(MockitoExtension.class)
class TestBatchService {
	@Mock
	private BatchRepository batchRepository;
	@Mock
	private ModelMapper modelMapper;
	@InjectMocks
	private BatchServiceImpl batchService;
	MenteeDto menteeDto;
	BatchDto batchDto;
	Batch batch;
	Mentee mentee;
	List<MenteeDto> menteeDtos = new ArrayList<>();
	List<Mentee> mentees = new ArrayList<>();

	@BeforeEach
	void batchDetails() {
		menteeDto = new MenteeDto();
		menteeDto.setMenteeId(1);
		menteeDto.setMenteeName("Supriya");
		menteeDto.setEmail("supriya_divvela@epam.com");
		mentee = new Mentee();
		mentee.setMenteeId(1);
		mentee.setMenteeName("Supriya");
		mentee.setEmail("supriya_divvela@epam.com");
		menteeDtos.add(menteeDto);
		batchDto = new BatchDto();
		batchDto.setBatchId(1);
		batchDto.setBatchName("Java");
		batchDto.setMentee(menteeDtos);
		batch = new Batch();
		mentees.add(mentee);
		batch.setBatchId(1);
		batch.setBatchName("Java");
		batch.setMentee(mentees);
	}

	@Test
	void testAddBatch() throws BatchException {
		when(modelMapper.map(batchDto, Batch.class)).thenReturn(batch);
		when(batchRepository.save(Mockito.any(Batch.class))).thenReturn(batch);
		batchService.addBatch(batchDto);
		verify(batchRepository).save(batch);
	}

	@Test
	void testDeleteBatch() {
		doNothing().when(batchRepository).deleteById(1);
		batchService.deleteBatch(1);
		verify(batchRepository).deleteById(1);
	}

	@Test
	void testGetAllBatches() {
		List<BatchDto> batchDtos = new ArrayList<>();
		batchDtos.add(batchDto);
		List<Batch> batches = new ArrayList<>();
		batches.add(batch);
		when(batchRepository.findAll()).thenReturn(batches);
		when(modelMapper.map(batch, BatchDto.class)).thenReturn(batchDto);
		assertEquals(1, batchService.getAllBatches().size());

	}

	@Test
	void testGetBatch() throws BatchException {
		when(batchRepository.findById(1)).thenReturn(Optional.of(batch));
		when(modelMapper.map(batch, BatchDto.class)).thenReturn(batchDto);
		assertEquals(batchDto, batchService.getBatch(1));
	}

	@Test
	void testGetBatchWithException() throws BatchException {
		when(batchRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(BatchException.class,()->batchService.getBatch(1));
	}

	@Test
	void testUpdateBatch() throws BatchException {
		when(batchRepository.findById(0)).thenReturn(Optional.of(batch));
		when(modelMapper.map(batchDto, Batch.class)).thenReturn(batch);
		when(batchRepository.save(Mockito.any(Batch.class))).thenReturn(batch);
		batchService.updateBatch(0, batchDto);
		verify(batchRepository).save(batch);
	}

	@Test
	void testUpdateBatchWithBatchException() throws BatchException {
		when(batchRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(BatchException.class,()->batchService.updateBatch(1,batchDto));
	}

}
