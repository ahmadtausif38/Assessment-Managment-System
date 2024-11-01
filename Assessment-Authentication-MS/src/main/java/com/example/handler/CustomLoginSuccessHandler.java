package com.example.handler;

import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.entity.Users;
import com.example.repository.UserRepository;
import com.example.service.JwtService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{
	
	private static final Logger log=LoggerFactory.getLogger(CustomLoginSuccessHandler.class);

	@Autowired
    private JwtService jwtService;
	
	@Autowired
	private UserRepository repo;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Value("${pivotal.apigateway.name}")
    private String apiGatewayName;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response, 
                                        Authentication authentication) throws IOException {
        // Generate the JWT token based on user details from the authentication object
    	Users user=null;
        if(authentication.isAuthenticated()) {
        	
        	user= repo.findByEmail(authentication.getName()).get();
        	log.info("User is : {}",user);
        	String token = jwtService.generateToken(user);
        	
        	// Set the JWT token in a cookie
        	 Cookie cookie = new Cookie("JWT_TOKEN", token);
             cookie.setHttpOnly(true);
             cookie.setSecure(false); // Change to true in production
             cookie.setPath("/");
             response.addCookie(cookie);
             
          // Set user information in separate cookies
             Cookie userIdCookie = new Cookie("USER_ID", String.valueOf(user.getId()));
             userIdCookie.setHttpOnly(true);
             userIdCookie.setSecure(false); // Set to true in production
             userIdCookie.setPath("/");
             response.addCookie(userIdCookie);

             Cookie usernameCookie = new Cookie("USERNAME", user.getFirstName());
             usernameCookie.setHttpOnly(true);
             usernameCookie.setSecure(false); // Set to true in production
             usernameCookie.setPath("/");
             response.addCookie(usernameCookie);

             Cookie emailCookie = new Cookie("EMAIL", user.getEmail());
             emailCookie.setHttpOnly(true);
             emailCookie.setSecure(false); // Set to true in production
             emailCookie.setPath("/");
             response.addCookie(emailCookie);

             String url=callApiGateway(authentication,response);
             
             response.sendRedirect(url);
        }
        
       
    }
    // Apply circuit breaker to this method
    @CircuitBreaker(name = "apiGatewayCircuitBreaker", fallbackMethod = "fallbackUrl")
    public String callApiGateway(Authentication authentication, HttpServletResponse response) {
        ServiceInstance instance = loadBalancerClient.choose(apiGatewayName);
        URI uri = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort()));
        log.info("API-Gateway Base URL: {}", uri);

        String url = null;

        for (GrantedAuthority authority : authentication.getAuthorities()) {
        	
        	 log.info("Role {}",authority.getAuthority());
        	 
            if (authority.getAuthority().equals("ADMIN")) {
                url = uri + "/admin/home";
                log.info("Admin MS URL: {}", url);
            } else if (authority.getAuthority().equals("USER")) {
                url = uri + "/employee/employee-home";
                log.info("Employee MS URL: {}", url);
            }
        }

       
        if (url == null) {
            url = uri + "/";
            log.warn("No Role matched, redirecting to API-Gateway homepage.");
        }

        return url;
    }

    // Fallback method in case the circuit breaker opens
    public String fallbackUrl(Authentication authentication, HttpServletResponse response, Throwable throwable) {
        log.error("Fallback executed due to: {}", throwable.getMessage());
        // Redirect to a default page or display an error page
        return "/error";
    }
}
