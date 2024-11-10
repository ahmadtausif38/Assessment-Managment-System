
package com.example.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationResponseDTO {

	private Long userId;
	private LocalDate assessmentDate;
	private LocalDate registrationDate;
	private Boolean isCompleted;
	private Long assessmentId;
	private String assessmentName;

}
