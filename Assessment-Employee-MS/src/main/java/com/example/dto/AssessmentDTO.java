package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssessmentDTO {

	private Long id;
    private String name;
    private String type;
}
