package com.dmsacad.store.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LoginFilter extends OncePerRequestFilter {//OncePerRequestFilter Ensure the filter get called once per request

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("LoginFilter.doFilterInternal(): Request: "+request.getRequestURI());
        filterChain.doFilter(request, response);
        System.out.println("LoginFilter.doFilterInternal(): Response: "+response.getStatus());
    }
}
