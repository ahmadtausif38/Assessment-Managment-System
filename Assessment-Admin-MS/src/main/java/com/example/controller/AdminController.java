package com.example.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
import com.example.dto.RegistrationResponseDTO;
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
		List<String> assessmentTypes = Arrays.asList("Technical", "Behavioral", "Analytical", "Leadership",
				"Communication", "Creative");
		mv.addObject("assessmentTypes", assessmentTypes);
		return mv;

	}

	// Add Assignment
	@PostMapping("/add-assessment")
	public ModelAndView addAssessment(@RequestParam("name") String assessmentName,
			@RequestParam("type") String assessmentType) {

		ModelAndView mv = new ModelAndView("message");
		if (service.saveAssessment(assessmentName.toLowerCase(), assessmentType.toLowerCase())) {
			mv.addObject("message", "Assessment Added successfully...");
		} else {
			mv.addObject("message",
					"Unable to add Assessment.. either Assessment Already Exist .. !! Or Something went wrong..!!");
		}
		return mv;

	}

	// view All Assessment
	@GetMapping("/assessments")
	public ModelAndView getAllAssessments() {

		ModelAndView mv = new ModelAndView("view-assessment");
		mv.addObject("message", "List Of All Assessments");
		List<AssessmentDTO> assessments = service.getAllAssessments();

		mv.addObject("assessments", assessments);

		return mv;

	}
	
	//view Deleted/Archived Assessment
	
	
		@GetMapping("/archived-assessments")
		public ModelAndView getArchivedAssessments() {

			ModelAndView mv = new ModelAndView("view-archived-assessment");
			mv.addObject("message", "List Of Deleted/Archived Assessments");
			List<AssessmentDTO> assessments = service.getArchivedAssessments();

			mv.addObject("assessments", assessments);

			return mv;

		}

	// Fetch Registered Users
	@GetMapping("/assessment-registeredUsers")
	public ModelAndView getAllRegisteredUser() {

		ModelAndView mv = new ModelAndView("view-registered-user");
		List<RegistrationResponseDTO>registeredUsers= service.getAllRegisteredUser();
		
		System.out.println(registeredUsers);
		mv.addObject("registrations",registeredUsers);

		return mv;
	}

	// Delete Assignment

	@PostMapping("/delete-assessment")
	public ModelAndView deleteAssessment(@RequestParam("id") Long id) {
		// Perform the deletion using the service
		ModelAndView mv = new ModelAndView("message");
		if (service.deleteAssessmentById(id)) {
			mv.addObject("message", "Assessment Deleted successfully...");
		} else {
			mv.addObject("message", "There is some problem in Deleting assessment...");
		}

		return mv;

	}
	
	//Add Archieved Assessment	addArchived-assessment
	

	@PostMapping("/addArchived-assessment")
	public ModelAndView addArchivedAssessment(@RequestParam("id") Long id) {
		// Perform the deletion using the service
		ModelAndView mv = new ModelAndView("message");
		if (service.addArchivedAssessment(id)) {
			mv.addObject("message", "Assessment Added-back successfully...");
		} else {
			mv.addObject("message", "There is some problem in Adding assessment...");
		}

		return mv;

	}
	// view assignment by type

	// Add Assessment link
	@GetMapping("/add-assessmentLink")
	public ModelAndView showAssessmentLink() {

		ModelAndView mv = new ModelAndView("add-assessmentLink");

		Set<String> assessmentTypes = service.getAllAssessmentNames();
		mv.addObject("assessmentTypes", assessmentTypes);
		return mv;

	}

	@PostMapping("/add-assessmentLink")
	public ModelAndView addAssessmentLink(@RequestParam("assessmentName") String assessmentName,
			@RequestParam("testLink") String testLink) {

		ModelAndView mv = new ModelAndView("message");
		if (service.addAssessmentLink(assessmentName.toLowerCase(), testLink)) {
			mv.addObject("message", "Assessment Link Added successfully...");
		} else {
			mv.addObject("message", "Unable to add Assessment Link..!!");
		}

		return mv;

	}

	// Show registered user for assignment

}
