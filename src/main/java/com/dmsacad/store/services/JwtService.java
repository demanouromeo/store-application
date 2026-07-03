package com.dmsacad.store.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dmsacad.store.configuration.JwtConfig;
import com.dmsacad.store.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JwtService {

    /*
    @Value("${spring.jwt.secret}")
    //@Value("${JWT_SECRET}")
    private String secret;
     */ //We read this directly from jwtConfig bean
    private final JwtConfig jwtConfig;
    private List<Jwt> blackList = new ArrayList();

    public void clearBlackList() {
        if (blackList != null) {
            blackList = blackList.stream()
                    .filter(Jwt::isExpired) //ie (jwr->jwt.isExpired())
                    .toList();
        }
    }

    public Jwt generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private Jwt generateToken(User user, long tokenExpiration) {
        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    private Claims getClaims(String token) {
        //The payload are claims. ie properties we know about the payload like sub, iat, expiration ...
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Jwt parseToken(String token) {
        try {
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isBlackListed(Jwt jwt) {
        return blackList.contains(jwt);
    }

    public void addToBlackList(Jwt jwt) {
        blackList.add(jwt);
    }
}
