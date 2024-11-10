package com.example.filter;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Value("${spring.application.name}")
    private String applicationName;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("*********** Inside Custom JwtAuthFilter ********");
            if (validator.isSecured.test(exchange.getRequest())) {
                System.out.println("*********** Its Secured path / now retrieving jwt from cookie ********");
                String jwtToken = null;

                // Retrieve JWT token from HttpOnly cookie
                if (exchange.getRequest().getCookies().containsKey("JWT_TOKEN")) {
                    jwtToken = exchange.getRequest().getCookies().getFirst("JWT_TOKEN").getValue();
                    System.out.println("Token from cookies: " + jwtToken);
                } else {
                    System.out.println("JWT_TOKEN cookie not found, redirecting to login.");
                    return redirectToLogin(exchange);
                }

                String username = jwtUtil.extractUserName(jwtToken);
                try {
                    jwtUtil.validateToken(jwtToken, username);
                } catch (Exception e) {
                    System.out.println("******** Exception occurs while validating token in JWT filter: " + e.getMessage());
                    return redirectToLogin(exchange);
                }
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> redirectToLogin(ServerWebExchange exchange) {
    	
    	System.out.println("In redirecting method..");
        // Redirect to the login page of your authentication service
        ServiceInstance instance = loadBalancerClient.choose(applicationName);
        
        if (instance != null) {
            String loginUrl = String.format("http://%s:%s/auth/login",
                    instance.getHost(),
                    instance.getPort());
            System.out.println("Redirecting to login URL: " + loginUrl);
            exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
            exchange.getResponse().getHeaders().setLocation(URI.create(loginUrl));
            return exchange.getResponse().setComplete();
        } else {
            // Handle case where the auth service is not available
            exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
            return exchange.getResponse().setComplete();
        }
    }



    public static class Config {
        // Configuration properties if needed
    }
}
