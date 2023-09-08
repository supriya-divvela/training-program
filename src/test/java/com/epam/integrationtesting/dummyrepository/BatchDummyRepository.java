package com.epam.integrationtesting.dummyrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.model.Batch;

public interface BatchDummyRepository extends JpaRepository<Batch, Integer> {

	void deleteByBatchName(String batchName);
}
