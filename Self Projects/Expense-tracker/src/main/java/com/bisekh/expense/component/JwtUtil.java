package com.bisekh.expense.component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long accessTokenExpiration = 1000 * 60 * 15;
    private final long refreshTokenExpiration = 1000 * 60 * 60 * 24 *7;


    public String generateAccessToken(String email, Long id){
        return generateToken(email,id,accessTokenExpiration);
    }

    public String generateRefreshToken(String email, Long id){
        return generateToken(email,id,refreshTokenExpiration);
    }


    public String generateToken(String email, Long id, Long expiration){
        return Jwts.builder()
                .setSubject(email)
                .claim("id",id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
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
