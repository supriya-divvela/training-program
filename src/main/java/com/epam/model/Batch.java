package com.epam.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Batch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer batchId;
	@Column(unique = true)
	private String batchName;
	@OneToMany(mappedBy = "batch", cascade = CascadeType.ALL)
	List<Mentee> mentee;

	public void setMentee(List<Mentee> mentee) {
		mentee.forEach(menteee -> menteee.setBatch(this));
		this.mentee = mentee;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
}
