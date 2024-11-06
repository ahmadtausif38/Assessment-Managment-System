package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clients.AssessmentFeignClient;
import com.example.dto.AssessmentDTO;

@Service
public class AdminService {
	
	@Autowired
	private AssessmentFeignClient assessmentClient;

	public Boolean saveAssessment(String assessmentName, String assessmentType) {
		// TODO Auto-generated method stub
		System.out.println("Saving..");
		return assessmentClient.addAssessment(assessmentName,assessmentType);
	}

	public List<AssessmentDTO> getAllAssessments() {
		// TODO Auto-generated method stub
		return assessmentClient.getAllAssessments();
	}

	public Boolean deleteAssessmentById(Long id) {
		return assessmentClient.deleteAssessmentById(id);
		// TODO Auto-generated method stub
		
	}


}
