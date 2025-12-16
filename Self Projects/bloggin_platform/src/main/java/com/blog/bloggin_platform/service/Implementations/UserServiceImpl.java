package com.blog.bloggin_platform.service.Implementations;

import com.blog.bloggin_platform.components.JWT;
import com.blog.bloggin_platform.dto.UserDto;
import com.blog.bloggin_platform.exception.ApiException;
import com.blog.bloggin_platform.exception.JwtExpiredException;
import com.blog.bloggin_platform.model.User;
import com.blog.bloggin_platform.repository.UserRepository;
import com.blog.bloggin_platform.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private  final JWT jwt;
    private final PasswordEncoder passwordEncoder;

    public  UserServiceImpl(UserRepository userRepository,
                            JWT jwt,
                            PasswordEncoder passwordEncoder
                            ){
        this.jwt = jwt;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) throws  Exception {
        if(user.getPassword().isEmpty()
                || user.getEmail().isEmpty()
                || user.getUsername().isEmpty()
        ){
            throw  new RuntimeException("Fields cannot be empty");
        }

        if(user.getPassword().length() < 8){
            throw  new ApiException("Password length cannot be less than 8 character");
        }
        User newUser = new User();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(hashedPassword);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    @Override
    public Map<Object, Object> loginUser(UserDto user)  throws  Exception{
       try{
           if(user.getPassword().isEmpty()
                   || user.getEmail().isEmpty()
           ){
               throw  new ApiException("Fields cannot be empty");
           }
           User userDetails = userRepository.findByEmail(user.getEmail());
           boolean isCorrectPassword = passwordEncoder.matches(user.getPassword(),userDetails.getPassword());
           if(!isCorrectPassword){
               throw  new ApiException("Incorrect password");
           }
           String accessToken = jwt.generateAccessToken(user.getEmail(), userDetails.getId());
           String refreshToken = jwt.generateRefreshToken(user.getEmail(), userDetails.getId());
           Map<Object,Object> loggedInUser = new HashMap<>();
           loggedInUser.put("id",userDetails.getId());
           loggedInUser.put("email",user.getEmail());
           loggedInUser.put("accessToken",accessToken);
           loggedInUser.put("refreshToken",refreshToken);
           loggedInUser.put("createdAt",userDetails.getCreatedAt());
           loggedInUser.put("updatedAt",userDetails.getUpdatedAt());

           return loggedInUser;
       } catch (Exception e) {
           throw new RuntimeException(e.getMessage());
       }
    }

    @Override
    public User getMyDetails(String accessToken) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("id",Long.class);
            User userDetails = userRepository.findById(userId).orElse(null);
            if(userDetails == null){
                throw  new ApiException("No user found");
            }
            User user = new User();
            user.setId(userDetails.getId());
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            return  user;
        }catch (ExpiredJwtException expiredJwtException){
            throw  new JwtExpiredException("Token has expired. Please login again");
        }catch (JwtException jwtException){
            throw  new JwtException("Invalid token. Please login again");
        }

    }
}
