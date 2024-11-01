package com.example.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AssessmentRegistrationDTO {

	 	private Long id;
	    private Long userId;
	    private LocalDate assessmentDate;
	    private LocalDate registrationDate;
	    private Boolean isCompleted;
	    private Long assessmentId;  // Reference to the associated assessment
}
