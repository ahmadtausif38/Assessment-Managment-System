package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
	

	// Add or Restore Assessment
	public Boolean addOrRestoreAssessment(String assessmentName, String assessmentType) {
		Optional<Assessment> existingAssessment = repository.findByName(assessmentName);

		if (existingAssessment.isEmpty()) {
			// If assessment does not exist, create a new one
			Assessment newAssessment = new Assessment(assessmentName, assessmentType);
			repository.save(newAssessment);
			return true;
		}

		Assessment assessment = existingAssessment.get();

		if (assessment.getIsDeleted()) {
			// If the assessment exists but is marked as deleted, restore it
			assessment.setIsDeleted(false);
			repository.save(assessment);
			return true;
		}

		// If assessment already exists and is not deleted, return false
		return false;
	}

	// view assessment by name

	// view Assessment by type
	public List<Assessment> viewAssessmentByType(String assessmentType) {
		return repository.findByType(assessmentType);

	}
	// remove assessment

	public List<AssessmentDTO> getAllAssessments() {
		// TODO Auto-generated method stub
		List<Assessment> assessments = repository.findAll().stream().filter(a -> a.getIsDeleted() == false).toList();

		return toAssessmentDTO(assessments);
	}

	public List<AssessmentDTO> getArchivedAssessments() {
		// TODO Auto-generated method stub
		List<Assessment> assessments = repository.findAll().stream().filter(a -> a.getIsDeleted() == true).toList();

		return toAssessmentDTO(assessments);
	}

	public boolean deleteAssessmentById(Long id) {
		// TODO Auto-generated method stub
		Assessment assesment = repository.findById(id).get();
		assesment.setIsDeleted(true);
		repository.save(assesment);
		return true;
	}
	
	public boolean addArchivedAssessment(Long id) {
		// TODO Auto-generated method stub
		Assessment assesment = repository.findById(id).get();
		assesment.setIsDeleted(false);
		repository.save(assesment);
		return true;
	}

	public Set<String> getAllAssessmentName() {
		// TODO Auto-generated method stub
		return repository.findAll().stream().map(a -> a.getName()).collect(Collectors.toSet());
	}

	public Set<String> getAssessmentTypes() {
		// TODO Auto-generated method stub
		return repository.findAll().stream().map(a -> a.getType()).collect(Collectors.toSet());
	}

	

	private List<AssessmentDTO> toAssessmentDTO(List<Assessment> assessments) {
		return assessments.stream().map(assessment -> AssessmentDTO.builder().id(assessment.getId())
				.name(assessment.getName()).type(assessment.getType()).build()).collect(Collectors.toList());
	}

	
	

}
