package com.example.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.RegistrationDTO;
import com.example.entity.Assessment;
import com.example.entity.AssessmentRegistration;
import com.example.exception.DataAlreadyExistException;
import com.example.exception.DataNotFoundException;
import com.example.repository.AssessmentRegistrationRepository;
import com.example.repository.AssessmentRepository;

@Service
public class AssessmentRegistrationService {

	private static final Logger log= LoggerFactory.getLogger(AssessmentRegistrationService.class);
	
	@Autowired
	private AssessmentRegistrationRepository registrationRepo;
	
	@Autowired
	private AssessmentRepository assessmentRepo;
	//Register for assessment
	
	public void registerassessment(RegistrationDTO request) {
		
		Optional<Assessment> existingAssessment = assessmentRepo.findByName(request.getAssessmentName());
		
		if(existingAssessment.isPresent()) {
			
			//check user already register in same assessment
			Optional<AssessmentRegistration> alreadyRegistered= registrationRepo.findByuserIdAndAssessment(request.getUserId(),existingAssessment.get());
			if(alreadyRegistered.isPresent()) {
				
				log.error("User Already registered for -> {} <- assessment.",request.getAssessmentName());
				throw new DataAlreadyExistException("User Already Registerd for this Assessment...!!");
				
			}else {
				log.info("Registring for assessment");
				
				if(registrationRepo.existsByUserIdAndAssessmentDate(request.getUserId(),request.getAssessmentDate())) {
					
					log.error("Assessment Date Conflict");
					
					//return "You already have Assessment on this date, Please choose other date...";
					throw new RuntimeException(" Assessment Date Conflict ");
				}else {
					AssessmentRegistration register=AssessmentRegistration.builder()
							.assessment(existingAssessment.get())
							.assessmentDate(request.getAssessmentDate())
							.userId(request.getUserId())
							.build();
					register.setIsCompleted(false);
					
					if(registrationRepo.save(register)==null) {
						
						log.error("Not able to register for assessment");
						
						throw new RuntimeException("Not able to Register for Assessment..!!");
					}
					log.info("Registration successfully completed...!!");
				}
				
				
				
			}
		}else {
			log.error("Assessment not exist with this name :-> {}.",request.getAssessmentName());
			throw new DataNotFoundException("Assessment with name : "+request.getAssessmentName()+" doesn't exist ");
		}

		
	}

}
