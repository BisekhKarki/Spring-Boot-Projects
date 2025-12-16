package com.ecommerce.Ecommerce.Beginner.projects.components;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;



@Component
public class JWT{

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long accessTokenExpiry = 1000 * 60 * 60 * 7;
    private final long refreshTokenExpiry = 1000 * 60 * 60 * 7;

    public String generateAccessToken(Long userId, String email,String username){

        return generateToken(userId,email,username,accessTokenExpiry);
    }


    public String generateRefreshToken(Long userId, String email,String username){
         return generateToken(userId,email,username,refreshTokenExpiry);
    }

    public String generateToken(Long userId,String email,String username, Long expiredTime){
        return Jwts.builder()
            .setSubject(email)
            .claim("userId", userId)
            .claim("username", username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiredTime)).signWith(key).compact();
    }


    public Claims decodeToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }



}