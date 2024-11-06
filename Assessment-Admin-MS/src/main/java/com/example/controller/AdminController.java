package com.example.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.dto.AssessmentDTO;
import com.example.dto.AssessmentRegistrationDTO;
import com.example.entity.Employee;
import com.example.services.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService service;

	public List<Employee> loadEmployee() {
		List<Employee> employees = Arrays.asList(new Employee("EMP001", "Java", LocalDate.of(2024, 9, 10), "TECH"),
				new Employee("EMP002", "Email Etiquette", LocalDate.of(2024, 9, 12), "BEH"),
				new Employee("EMP003", "C#", LocalDate.of(2024, 9, 15), "TECH"),
				new Employee("EMP004", "Learning Agility", LocalDate.of(2024, 9, 16), "BEH"),
				new Employee("EMP005", "PHP", LocalDate.of(2024, 9, 17), "TECH"));
		return employees;
	}

	@GetMapping("/home")
	public ModelAndView getRegistrations() {
		
		ModelAndView mv = new ModelAndView("admin-home");
		List<Employee> employees = loadEmployee(); 
		mv.addObject("employees", employees);
		return mv;

	}

	@GetMapping("/add-assessment")
	public ModelAndView showAddAssessmentPage() {

		ModelAndView mv = new ModelAndView("add-assessment");
		 List<String> assessmentTypes = Arrays.asList("Technical", "Behavioral", "Analytical", "Leadership", "Communication", "Creative");
		 mv.addObject("assessmentTypes", assessmentTypes);
		return mv;

	}
	// Add Assignment
	@PostMapping("/add-assessment")
	public ModelAndView addAssessment(@RequestParam("name") String assessmentName, @RequestParam("type") String assessmentType) {
		
		ModelAndView mv = new ModelAndView("add-assessment");
		Boolean isSaved=service.saveAssessment(assessmentName.toLowerCase(), assessmentType.toLowerCase());
		if(isSaved) {
			mv.addObject("message", "Assessment saved successfully..!!");
		}else {
			mv.addObject("message", "Unable to save assessment...!!");
		}
		return mv;
		
	}
	
	
	// view All Assignemnt
	@GetMapping("/assessments")
	public ModelAndView getAllAssessments() {
		
		ModelAndView mv= new ModelAndView("view-assessment");
		List<AssessmentDTO> assessments=service.getAllAssessments();
		System.out.println(assessments);
		mv.addObject("assessments", assessments);
		
		return mv; 

	}

	@GetMapping("/registered-user")
	public ModelAndView getAllRegisteredUser() {

		ModelAndView mv = new ModelAndView("view-registered-user");
		mv.addObject("registrations", new ArrayList<AssessmentRegistrationDTO>());
		return mv; 
	}

	// Delete Assignment

	@PostMapping("/delete-assessment")
	public String deleteAssessment(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
	    // Perform the deletion using the service
	    if (service.deleteAssessmentById(id)) {
	        redirectAttributes.addFlashAttribute("message", "Assessment with Id : " + id + " deleted successfully..!!");
	    } else {
	        redirectAttributes.addFlashAttribute("message", "Unable to delete assessment with Id : " + id);
	    }

	    return "redirect:/admin/assessments";
	}

	// view assignment by type

	// Show registered user for assignment

}
