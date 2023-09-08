package com.epam.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.BatchDto;
import com.epam.exception.BatchException;
import com.epam.model.Batch;
import com.epam.repository.BatchRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BatchServiceImpl implements BatchService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private BatchRepository batchRepository;

	@Override
	public BatchDto addBatch(BatchDto batchDto) {
		log.info("BatchService : Add Batch Method...");
		return modelMapper.map(batchRepository.save(modelMapper.map(batchDto, Batch.class)), BatchDto.class);

	}

	@Transactional
	@Override
	public void deleteBatch(int batchId) {
		log.info("BatchService : Delete Batch Method...");
		batchRepository.deleteById(batchId);

	}

	@Override
	public List<BatchDto> getAllBatches() {
		log.info("BatchService : Get All Batches Method...");
		return batchRepository.findAll().stream().map(batch -> modelMapper.map(batch, BatchDto.class)).toList();
	}

	@Override
	public BatchDto getBatch(int batchId) throws BatchException {
		log.info("BatchService : Get Batch Method...");
		return batchRepository.findById(batchId).map(batch -> modelMapper.map(batch, BatchDto.class))
				.orElseThrow(() -> new BatchException("Batch Not Found With This Book Number"));

	}

	@Override
	public BatchDto updateBatch(int batchId, BatchDto batchDto) throws BatchException {
		log.info("BatchService : Update Batch Method...");
		return modelMapper.map(batchRepository.findById(batchId)
				.map(book -> batchRepository.save(modelMapper.map(batchDto, Batch.class)))
				.orElseThrow(() -> new BatchException("Book not found....")), BatchDto.class);

	}

}
