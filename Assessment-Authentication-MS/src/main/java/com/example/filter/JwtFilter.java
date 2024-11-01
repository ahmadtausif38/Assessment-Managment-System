package com.example.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.service.JwtService;
import com.example.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ApplicationContext context;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzI2ODUwNDQ5LCJleHAiOjE3MjY4NTA1NTd9.UiNWSy8n_XO26RKhKRh1b1Cmh6ytNZp_o6DufjeRN48

		String authHeader=request.getHeader("Authorization");
		
		String username=null;
		String token=null;
		
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			token=authHeader.substring(7);
			username=jwtService.extractUserName(token);
		}
		//Checking username is not null and is not already authenticated
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails=context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
			//validating token- we need to check two things 1. token 2. username provided in toek is exist in DB
			if(jwtService.validateToken(token, userDetails)) {
				
				//after validating token we have to work on UsernamePasswordAuthenticationFilter, 
				//as this will work after jwtfilter, since we mention it in SecurityFilterChain in our SecurityConfig class
				
				UsernamePasswordAuthenticationToken authToken= 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
