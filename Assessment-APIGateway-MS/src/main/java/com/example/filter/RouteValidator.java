package com.example.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class RouteValidator {

    // List of open API endpoints that do not require security
    public static final List<String> openApiEndPoints = List.of(
        "/",
        "/check",
        "/auth/login",
        "/auth/**",
        "/eureka"
    );

    // Predicate to check if the request is for a secured path
    public Predicate<ServerHttpRequest> isSecured = request -> {
        String path = request.getURI().getPath();
        System.out.println("Checking secured status for path: " + path);

        // AntPathMatcher for flexible path matching
        AntPathMatcher pathMatcher = new AntPathMatcher();
        
        // Return true if the path is not in the openApiEndPoints
        boolean isSecuredPath = openApiEndPoints.stream()
            .noneMatch(uri -> pathMatcher.match(uri, path));

        System.out.println("Is secured path: " + isSecuredPath);
        return isSecuredPath;
    };
}
