package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	

}