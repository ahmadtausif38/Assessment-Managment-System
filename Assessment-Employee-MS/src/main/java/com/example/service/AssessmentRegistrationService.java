package com.example.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.AssessmentDTO;
import com.example.dto.RegistrationDTO;
import com.example.dto.RegistrationResponseDTO;
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
	
	public String registerassessment(RegistrationDTO request) {
		
		Optional<Assessment> existingAssessment = assessmentRepo.findByName(request.getAssessmentName());
		
		if(existingAssessment.isPresent()) {
			
			//check user already register in same assessment
			Optional<AssessmentRegistration> alreadyRegistered= registrationRepo.findByuserIdAndAssessment(request.getUserId(),existingAssessment.get());
			if(alreadyRegistered.isPresent()) {
				
				log.error("User Already registered for -> {} <- assessment.",request.getAssessmentName());
//				throw new DataAlreadyExistException("User Already Registerd for this Assessment...!!");
				return "You Already Registerd for this Assessment : "+request.getAssessmentName()+" ...!!";
				
			}else {
				log.info("Registring for assessment");
				
				if(registrationRepo.existsByUserIdAndAssessmentDate(request.getUserId(),request.getAssessmentDate())) {
					
					log.error("Assessment Date Conflict : User already have Assessment on this date..!!");
					
					//return "You already have Assessment on this date, Please choose other date...";
					//throw new RuntimeException(" Assessment Date Conflict ");
					return "You already have Assessment on this date, Please choose other date...";
				}else {
					AssessmentRegistration register=AssessmentRegistration.builder()
							.assessment(existingAssessment.get())
							.assessmentDate(request.getAssessmentDate())
							.userId(request.getUserId())
							.build();
					register.setIsCompleted(false);
					
					if(registrationRepo.save(register)==null) {
						
						log.error("Not able to register for assessment");
						
						//throw new RuntimeException("Not able to Register for Assessment..!!");
						return "Someting went wrong....Not able to register for assessment..";
					}
					log.info("Registration successfully completed...!!");
					
					return "Registration successfully completed...!!";
				}			
			}
		}else {
			log.error("Assessment not exist with this name :-> {}.",request.getAssessmentName());
			//throw new DataNotFoundException("Assessment with name : "+request.getAssessmentName()+" doesn't exist ");
			return "Assessment not exist with this name : "+request.getAssessmentName();
		}
		

		
	}
	
	//Fetch registered User lists
	public List<RegistrationResponseDTO> getAssessmentRegisteredUsers() {
		// TODO Auto-generated method stub
		List<AssessmentRegistration>registeredUsers= registrationRepo.findAll();
		
		return toResponseDTO(registeredUsers);
	}
	

	public List<RegistrationResponseDTO> getRegisteredAssessmentByUserId(Long id) {
		// TODO Auto-generated method stub
		List<AssessmentRegistration>assessments=registrationRepo.findByUserId(id);
		return toResponseDTO(assessments);
	}

	private List<RegistrationResponseDTO> toResponseDTO(List<AssessmentRegistration> registeredUsers) {
		// TODO Auto-generated method stub
		return registeredUsers.stream()
				.map(user->RegistrationResponseDTO.builder()
						.userId(user.getUserId())
						.assessmentDate(user.getAssessmentDate())
						.assessmentId(user.getAssessment().getId())
						.registrationDate(user.getRegistrationDate())
						.isCompleted(user.getIsCompleted())
						.assessmentName(user.getAssessment().getName())
						.build()).toList();
	}


}
