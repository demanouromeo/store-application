package com.dmsacad.store.filters;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dmsacad.store.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;//OncePerRequestFilter Ensure the filter get called once per request

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization"); //Extract the 'Authorisation' header
        System.out.println("JwtAuthenticationFilter.doFilterInternal(): 'JwtAuthenticationFilter' Filter called");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("JwtAuthenticationFilter.doFilterInternal(): Hearder is required and must start with [Bearer ] - Except WHEN LOGIN");
            filterChain.doFilter(request, response);//Pass Control to the next filter in the chain
            return;
        }

        String token = authHeader.replace("Bearer ", "");
        var jwt = jwtService.parseToken(token);
        if (jwt != null && !jwt.isExpired() && !jwtService.isBlackListed(jwt)) {
            //!jwtService.isBlackListed(jwt) ADD more complexity, but is used to make sure when the user logs out, it cannot access endpoints using the blacklisted token. This is a security measure to prevent unauthorized access after logout.
        } else {
            System.out.println("JwtAuthenticationFilter.doFilterInternal(): Invalid token");
            filterChain.doFilter(request, response);//Pass Control to the next filter in the chain
            return;
        }

        //Reaching here means token is valid
        System.out.println("JwtAuthenticationFilter.doFilterInternal(): Connected USER ROLE is [" + jwt.getUserRole() + "]");
        var authentication = new UsernamePasswordAuthenticationToken(
                jwt.getUserId(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + jwt.getUserRole()))
        //List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);//SecurityContextHolder stores information about the currently authenticated user
        filterChain.doFilter(request, response);
    }
}
