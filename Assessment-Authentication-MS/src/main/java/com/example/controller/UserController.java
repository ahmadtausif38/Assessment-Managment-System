package com.example.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.dto.UsersDto;
import com.example.entity.Users;
import com.example.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/auth")
public class UserController {

	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService service;
	
	@Autowired
	private LoadBalancerClient lb;
	
	@Value("${pivotal.apigateway.name}")
	protected String apigateway;
	
	@GetMapping("/check")
	@ResponseBody
	public String check(HttpServletRequest request) {
//		ModelAndView mv= new ModelAndView("index");
		
	/*	  Print all cookies from the request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            System.out.println("Stored Cookies:");
            for (Cookie storedCookie : cookies) {
                System.out.println("Name: " + storedCookie.getName() + ", Value: " + storedCookie.getValue());
            }
        } else {
            System.out.println("No cookies found.");
        }
        */
		return "Working...";
	}
	
	@GetMapping("/")
	public ModelAndView homePage() {
		ModelAndView mv= new ModelAndView("index");
		return mv;
	}
	
	@GetMapping("/login")
	public ModelAndView loginPage() {
		ModelAndView mv= new ModelAndView("login");
		return mv;
	}
	
	@GetMapping("/register")
	public ModelAndView registerPage() {
		
		ModelAndView mv= new ModelAndView("register");
		UsersDto userDto=new UsersDto();
		mv.addObject("userDto", userDto);
		return mv;
	}

	
	@PostMapping("/login")
	public ModelAndView loginPage(@RequestParam("username") String username, @RequestParam("password") String password,HttpServletResponse response){
	    log.info("Login Endpoint called...");  
	    
	    // send dynamic assessment name from db along with model 
	    
		ModelAndView mv = new ModelAndView("login");
	    
	    return mv;
	}
	
	@GetMapping("/users")
	@ResponseBody
	public List<Users> getUsers(){
		
		return service.getUsers();
	}

	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("userDto") UsersDto userDto, Model m) {
		
		    // Logic to register the user
			String message=null;
			if(userDto.getPassword().equals(userDto.getConfirmPassword())){

				message=service.registerUser(userDto);
				if(message.length()!=0) {
					m.addAttribute("message",message);
					return "register";
				}else {
					 return "redirect:/auth/login"; // Redirect to login page after registration
				}
				
			}else {
				m.addAttribute("message", "Password and Confirm Password must be same !!!");
				return "register";
			}

	}


}
