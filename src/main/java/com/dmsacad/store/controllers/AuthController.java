package com.dmsacad.store.controllers;

import java.util.Map;

import com.dmsacad.store.services.AuthService;
import com.dmsacad.store.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.dmsacad.store.configuration.JwtConfig;
import com.dmsacad.store.dtos.request.AuthUserRequest;
import com.dmsacad.store.dtos.response.JwtResponse;
import com.dmsacad.store.mappers.UserMapper;
import com.dmsacad.store.repositories.UserRepository;
import com.dmsacad.store.services.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@Tag(name = "Login")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtConfig jwtConfig;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user via email and password")
    public ResponseEntity<JwtResponse> connect(
            @Valid @RequestBody AuthUserRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse response
    ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var cookie = new Cookie("refreshToken", refreshToken.toString());
        cookie.setHttpOnly(true);//To make cookie not accessible to Javascript. Make it more secured
        cookie.setPath("/auth");//Share cookie across auth endpoints during login/refresh flows
        //cookie.setPath("/auth/refresh"); //NOT WORKING WHEN COOKIE PATH IS SET TO /auth/refresh. IT WILL NOT BE SHARED WITH /auth/login ENDPOINT
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());//7days
        cookie.setSecure(httpServletRequest.isSecure());//Secure only when request is HTTPS (local Postman on http should still receive the cookie)
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }


    @PostMapping("/logout")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        var jwt = jwtService.parseToken(token);
        if(jwt.isExpired()){
            jwtService.clearBlackList();
            return ResponseEntity.ok().build();
        }
        //jwt.setExpiration(new Date());
        jwtService.addToBlackList(jwt);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(
            @CookieValue(value = "refreshToken") String refreshToken) {
        //The value 'refreshToken' has been set when creating the cookie in connect endPoint
        var jwt = jwtService.parseToken(refreshToken);
        if (jwt == null || jwt.isExpired()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//401
        }

        var user = userRepository.findById(jwt.getUserId()).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        /*
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            var userId = (Long) authentication.getPrincipal();
            if (userId == null) {
                return ResponseEntity.badRequest().build();
            }
            User u = userRepository.findById(userId).orElse(null);
            if (u == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(userMapper.toDto(u));
        }*/
        var u = authService.getCurrentUser();
        if(u!=null){
            return ResponseEntity.ok(userMapper.toDto(u));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Not authenticated", "User has not yet authenticate to the the system"));
    }
}
