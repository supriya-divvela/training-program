package com.epam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.model.Batch;
@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer>{

}
