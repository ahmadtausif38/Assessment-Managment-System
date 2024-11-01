package com.example.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.AssessmentEmployeeMsApplication;
import com.example.dto.RegistrationDTO;
import com.example.entity.Assessment;
import com.example.entity.AssessmentRegistration;
import com.example.exception.DataAlreadyExistException;
import com.example.exception.DataNotFoundException;
import com.example.handler.GlobalExceptionHandler;
import com.example.repository.AssessmentRegistrationRepository;
import com.example.repository.AssessmentRepository;
import com.example.service.AssessmentRegistrationService;
import com.example.service.AssessmentService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/employee")
public class AssessmentController {

	private static final Logger log=LoggerFactory.getLogger(AssessmentController.class);
	
	@Autowired
	private AssessmentRepository repo;
	
	
	@Autowired
	private AssessmentService service;
	
	@Autowired
	private AssessmentRegistrationService registrationService;
	
	@GetMapping("/")
	@ResponseBody
	public String test() {
		return "working fine ...";
	}
	
	@GetMapping("/employee-home")
	public ModelAndView employeeHomePages() {
		ModelAndView mv= new ModelAndView();
		mv.setViewName("employee-home");
		return mv;
	}
	
	@GetMapping("/assessment-register")
	public ModelAndView assessmentRegister(@RequestParam("assessmentType") String assessmentType, HttpServletRequest request) {
		
		ModelAndView mv= new ModelAndView("assessment-registration");
		
		List<Assessment> assessments=repo.findByType(assessmentType);
		Long userId=10L;
		String email=null;
		String username=null;
		// Retrieve cookies from the request
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            switch (cookie.getName()) {
	                case "USER_ID":
	                    userId = Long.valueOf(cookie.getValue());
	                    break;
	                case "USERNAME":
	                    username = cookie.getValue();
	                    break;
	                case "EMAIL":
	                    email = cookie.getValue();
	                    break;
	            }
	        }
	    }
		
		

		mv.addObject("userId",userId);
		mv.addObject("email",email);
		mv.addObject("username",username);
		mv.addObject("assessments", assessments);
		mv.addObject("assessmentType",assessmentType);
//		mv.addObject("register", register);
		mv.addObject("today", LocalDate.now());
		return mv;
	}

	@PostMapping("/assessment-register")
	public ModelAndView handleFormSubmission(@RequestParam("userId") Long userId,@RequestParam("email") String email,@RequestParam("assessmentType") String assessmentType,@RequestParam("assessmentName") String assessmentName,@RequestParam("assessmentDate") String assessmentDate) {
		
		ModelAndView mv= new ModelAndView("message");
		RegistrationDTO request= new RegistrationDTO(userId, email, assessmentType, assessmentName, LocalDate.parse(assessmentDate));
		
		
			registrationService.registerassessment(request);
			mv.addObject("message","Registration done successfullly...");
			return mv;
		
	}

}
