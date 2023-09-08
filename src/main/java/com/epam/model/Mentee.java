package com.epam.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Entity
@Getter
public class Mentee {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer menteeId;
	private String menteeName;
	private String email;
	@ManyToOne
	private Batch batch;
}
