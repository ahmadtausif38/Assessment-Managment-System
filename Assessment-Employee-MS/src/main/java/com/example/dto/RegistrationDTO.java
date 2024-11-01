package com.example.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
	
		private Long userId;
	    private String email;
	    private String assessmentType;
	    private String assessmentName;
	    private LocalDate assessmentDate; // Changed to LocalDate

}
