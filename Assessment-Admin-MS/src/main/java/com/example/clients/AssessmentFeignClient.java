package com.example.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import com.example.dto.AssessmentDTO;

@FeignClient(name = "assessment-employee-ms",path = "/employee")
public interface AssessmentFeignClient {
	
	@GetMapping("/")
	public String test();
	
	 @PostMapping("/assessment-add")
	    public Boolean addAssessment(@RequestParam("name") String assessmentName, 
	                                 @RequestParam("type") String assessmentType);

		 @GetMapping("/assessments")
		public List<AssessmentDTO> getAllAssessments();

		 @PostMapping("/delete-assessment")
		public Boolean deleteAssessmentById(@RequestParam("id") Long id);

}
