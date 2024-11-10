package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Assessment;
import com.example.entity.AssessmentLink;
import com.example.repository.AssesmentLinkRepository;
import com.example.repository.AssessmentRepository;

@Service
public class AssessmentLinkService {
	
	@Autowired
	private AssesmentLinkRepository repo;
	
	@Autowired
	private AssessmentRepository assessmentRepo;
	
	public Boolean addAssessmentLink(String assessmentName, String testLink) {
		// TODO Auto-generated method stub
		Assessment assessment=assessmentRepo.findByName(assessmentName).get();
		AssessmentLink link=new AssessmentLink();
		link.setAssessment(assessment);
		link.setLinkUrl(testLink);
		repo.save(link);
		return true;
	}

}
