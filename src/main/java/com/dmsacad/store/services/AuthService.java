package com.dmsacad.store.services;

import com.dmsacad.store.entities.User;
import com.dmsacad.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    public User getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            var userId = (Long) authentication.getPrincipal();
            if (userId == null) {
                return null;
            }
            return userRepository.findById(userId).orElse(null);
        }
        return null;
    }
}
