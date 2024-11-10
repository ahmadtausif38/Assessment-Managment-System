package com.example.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Clear JWT token cookie
        Cookie jwtCookie = new Cookie("JWT_TOKEN", null);
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);

        // Clear user information cookies
        Cookie userIdCookie = new Cookie("USER_ID", null);
        userIdCookie.setPath("/");
        userIdCookie.setHttpOnly(true);
        userIdCookie.setMaxAge(0);
        response.addCookie(userIdCookie);

        Cookie usernameCookie = new Cookie("USERNAME", null);
        usernameCookie.setPath("/");
        usernameCookie.setHttpOnly(true);
        usernameCookie.setMaxAge(0);
        response.addCookie(usernameCookie);

        Cookie emailCookie = new Cookie("EMAIL", null);
        emailCookie.setPath("/");
        emailCookie.setHttpOnly(true);
        emailCookie.setMaxAge(0);
        response.addCookie(emailCookie);
    }

}
