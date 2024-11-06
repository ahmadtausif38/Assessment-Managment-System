package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.AssessmentDTO;
import com.example.entity.Assessment;
import com.example.repository.AssessmentRepository;

@Service
public class AssessmentService {
	
	@Autowired
	private AssessmentRepository repository;
	
	
	//add assessment
	public Boolean saveAssessment(String assessmentName, String assessmentType) {
		Assessment assessment =new Assessment(assessmentName, assessmentType);
		// TODO Auto-generated method stub
		if(repository.save(assessment)!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	//view assessment by name
	
	
	//view Assessment by type
	public List<Assessment> viewAssessmentByType(String assessmentType){
		return repository.findByType(assessmentType);
		
	}
	//remove assessment

	public List<AssessmentDTO> getAllAssessments() {
		// TODO Auto-generated method stub
		List<Assessment> assessments= repository.findAll();
		
		return toAssessmentDTO(assessments);
	}

	private List<AssessmentDTO> toAssessmentDTO(List<Assessment> assessments) {
	    return assessments.stream()
	            .map(assessment -> AssessmentDTO.builder()
	                    .id(assessment.getId())
	                    .name(assessment.getName())
	                    .type(assessment.getType())
	                    .build())
	            .collect(Collectors.toList());
	}

	public boolean deleteAssessmentById(Long id) {
		// TODO Auto-generated method stub
		var assesment=repository.findById(id).get();
		assesment.setIsDeleted(true);
		repository.save(assesment);
		return true;
	}

	

}
