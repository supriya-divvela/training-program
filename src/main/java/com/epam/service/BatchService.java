package com.epam.service;

import java.util.List;

import com.epam.dto.BatchDto;
import com.epam.exception.BatchException;

public interface BatchService {
	BatchDto addBatch(BatchDto batchDto);

	void deleteBatch(int batchId);

	List<BatchDto> getAllBatches();

	BatchDto getBatch(int batchId) throws BatchException;

	BatchDto updateBatch(int batchId,BatchDto batchDto) throws BatchException;
}
