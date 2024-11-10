package com.example.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clients.AssessmentFeignClient;
import com.example.dto.AssessmentDTO;
import com.example.dto.RegistrationResponseDTO;

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

	public  Set<String> getAllAssessmentNames() {
		// TODO Auto-generated method stub
		return assessmentClient.getAllAssessmentNames();
	}

	public Boolean deleteAssessmentById(Long id) {
		return assessmentClient.deleteAssessmentById(id);
		// TODO Auto-generated method stub
		
	}

	public boolean addAssessmentLink(String assessmentName, String testLink) {
		return assessmentClient.addAssessmentLink(assessmentName,testLink);
		
	}

	public List<AssessmentDTO> getArchivedAssessments() {
		// TODO Auto-generated method stub
		return assessmentClient.getArchivedAssessments();
	}

	public Boolean addArchivedAssessment(Long id) {
		// TODO Auto-generated method stub
		return assessmentClient.addArchivedAssessment(id);
	}

	public List<RegistrationResponseDTO> getAllRegisteredUser() {
		// TODO Auto-generated method stub
		return assessmentClient.getAllRegisteredUser();
	}


}
