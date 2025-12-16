package com.blog.bloggin_platform.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JWT {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long accessTokenExpiration = 1000 * 60 * 2;
    private final long refreshTokenExpiration = 1000 * 60 * 60 * 24;

    public String generateAccessToken(String email, Long userId){
        return  generateToken(email,userId,accessTokenExpiration);
    }

    public String generateRefreshToken(String email, Long userId){
        return  generateToken(email,userId,refreshTokenExpiration);
    }


    public String generateToken(String email, Long userId, Long expiredTime){
        return Jwts.builder()
                .setSubject(email)
                .claim("id",userId)
                .setIssuedAt( new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(key)
                .compact();
    }


    public Claims decodeToken(String token){
        return  Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }



}
