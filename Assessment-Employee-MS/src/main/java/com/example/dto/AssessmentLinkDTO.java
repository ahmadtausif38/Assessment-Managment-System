package com.example.dto;

import lombok.Data;

@Data
public class AssessmentLinkDTO {
	private Long id;
    private String linkUrl;
    private Long assessmentId; 

}
