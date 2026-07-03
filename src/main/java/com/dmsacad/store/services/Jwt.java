package com.dmsacad.store.services;

import java.util.Date;

import javax.crypto.SecretKey;

import com.dmsacad.store.entities.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;

@Data
public class Jwt {

    private final Claims claims;
    private final SecretKey secretKey;

    public boolean isExpired() {
        return claims.getExpiration().before(new Date());//Before current date means the token is not expired
    }

    public Long getUserId() {
        //return claims.get("userId", Long.class);
        return Long.valueOf(claims.getSubject());// is same as above
    }

    public Role getUserRole() {
        return Role.valueOf(claims.get("role", String.class));
    }

//    public void setExpiration(Date expiration) {
//        claims.put("exp", expiration.getTime() / 1000);
//    }

    @Override
    public String toString() {
        return Jwts.builder().claims(claims)
                .signWith(secretKey)
                .compact();
    }

}
