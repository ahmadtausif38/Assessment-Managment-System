package com.example.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentRegistration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	private LocalDate assessmentDate;
	
	@Column(updatable = false)
	private LocalDate registrationDate;
	
	private Boolean isCompleted=false;

	@ManyToOne
	@JoinColumn(name="assessment_id", nullable = false)
	@ToString.Exclude
	private Assessment assessment;
	
	
	
	@PrePersist
	public void init() {
		this.registrationDate=LocalDate.now();
	}

}
