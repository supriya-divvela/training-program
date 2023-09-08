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

import com.epam.dto.BatchDto;
import com.epam.exception.BatchException;
import com.epam.service.BatchService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/batch")
@Slf4j
public class BatchController {
	@Autowired
	private BatchService batchService;

	@GetMapping
	public ResponseEntity<List<BatchDto>> getAllBatches() {
		log.info("BatchApi : Get All Batches Method..");
		return new ResponseEntity<>(batchService.getAllBatches(), HttpStatus.OK);
	}

	@GetMapping("/{batchId}")
	public ResponseEntity<BatchDto> getBatch(@PathVariable("batchId") int batchId) throws BatchException {
		log.info("BatchApi : Get Batch Method..");
		return new ResponseEntity<>(batchService.getBatch(batchId), HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<BatchDto> addBatch(@RequestBody @Valid BatchDto batchDto) throws BatchException {
		log.info("BatchApi : Add Batch Method..");
		return new ResponseEntity<>(batchService.addBatch(batchDto), HttpStatus.CREATED);
	}

	@DeleteMapping("{batchId}")
	public ResponseEntity<Void> deleteBatch(@PathVariable("batchId") int batchId) {
		log.info("BatchApi : Delete Batch Method..");
		batchService.deleteBatch(batchId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{batchId}")
	public ResponseEntity<BatchDto> updateBatch(@PathVariable("batchId") int batchId, @RequestBody @Valid BatchDto batchDto)
			throws BatchException {
		log.info("BatchApi : Update Batch Method..");
		return new ResponseEntity<>(batchService.updateBatch(batchId, batchDto), HttpStatus.OK);
	}
}
