package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.UserPrincipal;
import com.example.entity.Users;
import com.example.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Users> user=repository.findByEmail(username);
		if(user.isEmpty()){
			System.err.println("User Not Found.");
			throw new UsernameNotFoundException("User Not Found...");
		}
		return new UserPrincipal(user.get());
		
	}

}
