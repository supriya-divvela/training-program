package com.epam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.model.Mentee;
@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Integer>{

}
