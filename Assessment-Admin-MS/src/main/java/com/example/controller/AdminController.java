package com.example.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.dto.AssessmentRegistrationDTO;
import com.example.dto.UserDTO;
import com.example.entity.Employee;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	public List<Employee> loadEmployee(){
		 List<Employee> employees = Arrays.asList(
	                new Employee("EMP001", "Java", LocalDate.of(2024, 9, 10), "TECH"),
	                new Employee("EMP002", "Email Etiquette", LocalDate.of(2024, 9, 12), "BEH"),
	                new Employee("EMP003", "C#", LocalDate.of(2024, 9, 15), "TECH"),
	                new Employee("EMP004", "Learning Agility", LocalDate.of(2024, 9, 16), "BEH"),
	                new Employee("EMP005", "PHP", LocalDate.of(2024, 9, 17), "TECH")
	        );
		 return employees;
	}

	@GetMapping("/home")
	public ModelAndView getRegistrations() {
		
		
		ModelAndView mv= new ModelAndView("admin-home");
		List<Employee> employees = loadEmployee(); // Fetch from service
		mv.addObject("employees", employees);
		return mv; // Name of Thymeleaf page

	}
	
	@GetMapping("/add-assessment")
	public ModelAndView addAssessment() {
		
		
		ModelAndView mv= new ModelAndView("add-assessment");
		
		return mv; // Name of Thymeleaf page

	}
	
	@GetMapping("/assessments")
	public ModelAndView getAllAssessments() {
		
		
		ModelAndView mv= new ModelAndView("view-assessment");
		mv.addObject("assignments", new ArrayList<UserDTO>());
		
		return mv; // Name of Thymeleaf page

	}
	
	@GetMapping("/registered-user")
	public ModelAndView getAllRegisteredUser() {
			

		ModelAndView mv= new ModelAndView("view-registered-user");
		mv.addObject("registrations",new ArrayList<AssessmentRegistrationDTO>());
		return mv; // Name of Thymeleaf page
	}
	//Add Assignment
	
	//Delete Assignment
	
	//view All Assignemnt
	
	//view assignment by type
	
	//Show registered user for assignment
	


}
