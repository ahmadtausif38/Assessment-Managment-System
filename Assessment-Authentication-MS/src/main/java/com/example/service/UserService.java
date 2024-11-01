package com.example.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.controller.UserController;
import com.example.dto.UsersDto;
import com.example.entity.Role;
import com.example.entity.Users;
import com.example.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {
	
	private static final Logger log=LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager manager;	
	
	
	public List<Users> getUsers(){
		List<Users> users= repo.findAll();
		//System.out.println("User : "+repo.findById(1).get()+" role :- "+repo.findById(1).get().getRole().name());
		return users;
	}
	
	public String registerUser(@RequestBody UsersDto userDto){
		log.info("Checking user already exist or not..");
		Optional<Users> existingUser=repo.findByEmail(userDto.getEmail());
		if(!existingUser.isPresent()) {
			Users user=new Users();
			user.setEmail(userDto.getEmail());
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setRole(Role.USER);
			user.setPassword(encoder.encode(user.getPassword()));
			repo.save(user);
			log.info("User registered successfully...");
			return "";
		}else {
			log.error("User with this email : {} already registered..!!",userDto.getEmail());
			return "User with this email : "+userDto.getEmail()+" already registered..!!";
		}
		
	}
	
	

}
