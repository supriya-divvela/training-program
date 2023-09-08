package com.epam.integrationtesting.dummyrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.model.Mentee;

public interface MenteeDummyRepository extends JpaRepository<Mentee, Integer> {

	void deleteByMenteeName(String menteeName);
}
