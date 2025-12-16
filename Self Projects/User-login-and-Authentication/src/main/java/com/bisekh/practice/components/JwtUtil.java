package com.bisekh.practice.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

        private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        //    15 min expiry for access token
        private final long accessTokenExpiration = 1000 * 60 * 15;

        //    7 days for refresh token
        private  final long refreshTokenExpiration = 1000 * 60 * 60 * 24 * 7;


        public String generateAccessToken(String email,String role,Long id){
                return generateToken(email,role,id,accessTokenExpiration);
        }

        public String generateRefreshToken(String email, String role,Long id){
                return generateToken(email,role,id,refreshTokenExpiration);
        }

        public String generateToken(String email, String role,Long id, Long expirationTime){
            return Jwts.builder()
                    .setSubject(email)
                    .claim("role",role)
                    .claim("id",id)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(key)
                    .compact();
        }


        public Claims decodeToken(String token){
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

}
