package com.bisekh.expense.serrvice.Impl;

import com.bisekh.expense.component.JwtUtil;
import com.bisekh.expense.model.User;
import com.bisekh.expense.repository.UserRepository;
import com.bisekh.expense.serrvice.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {

    private  final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public  UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder){
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, Object>  getMyDetails(String accessToken)  throws  Exception{
        try{
            Claims token = jwtUtil.decodeToken(accessToken);
            String email = token.getSubject();
            Long id = token.get("id",Long.class);
            User details = userRepository.findById(id).orElse(null);
            if(details == null){
                return  null;
            }
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("id",details.getId());
            userDetails.put("username",details.getUsername());
            userDetails.put("email",details.getEmail());
            userDetails.put("createdAt",details.getCreatedAt());
            userDetails.put("updatedAt",details.getUpdatedAt());
            return  userDetails;
        }catch (ExpiredJwtException expiredJwtException){
            throw new RuntimeException(expiredJwtException.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public User createUser(User user) {
        User newUSer = new User();
        newUSer.setEmail(user.getEmail());
        newUSer.setUsername(user.getUsername());
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        newUSer.setPassword(hashedPassword);
        newUSer.setCreatedAt(LocalDateTime.now());
        newUSer.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUSer);
    }

    @Override
    public Map<Object, Object> loginUser(String email, String password, Long id, String username) throws  Exception {
        try{
            if(email.isEmpty() || password.isEmpty()){
               throw  new Exception("Email and password cannot be empty");
            }

            String accessToken = jwtUtil.generateAccessToken(email,id);
            String refreshToken = jwtUtil.generateRefreshToken(email,id);

            Map<Object, Object > response = new HashMap<>();
            response.put("id",id);
            response.put("email",email);
            response.put("username",username);
            response.put("accessToken",accessToken);
            response.put("refreshToken",refreshToken);
            return  response;
        }catch (ExpiredJwtException expiredJwtException){
            throw new RuntimeException(expiredJwtException.getMessage());
        }

    }
}
