package com.example.clients;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import com.example.dto.AssessmentDTO;
import com.example.dto.RegistrationResponseDTO;

@FeignClient(name = "assessment-employee-ms", path = "/employee")
public interface AssessmentFeignClient {

	@GetMapping("/")
	public String test();

	@PostMapping("/assessment-add")
	public Boolean addAssessment(@RequestParam("name") String assessmentName,
			@RequestParam("type") String assessmentType);

	@GetMapping("/assessments")
	public List<AssessmentDTO> getAllAssessments();
	
	@GetMapping("/archived-assessment")
	public List<AssessmentDTO> getArchivedAssessments();

	@GetMapping("/assessment-names")
	public  Set<String> getAllAssessmentNames();

	@PostMapping("/delete-assessment")
	public Boolean deleteAssessmentById(@RequestParam("id") Long id);

	@PostMapping("/add-assessmentLink")
	public Boolean addAssessmentLink(@RequestParam("assessmentName") String assessmentName,
			@RequestParam("testLink") String testLink);
	@PostMapping("/addArchived-assessment")
	public Boolean addArchivedAssessment(@RequestParam("id") Long id);

	@GetMapping("/assessment-registeredUsers")
	public List<RegistrationResponseDTO> getAllRegisteredUser();

	

}
