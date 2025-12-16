package com.bisekh.practice.service.Impl;


import com.bisekh.practice.components.JwtUtil;
import com.bisekh.practice.model.User;
import com.bisekh.practice.repositories.UserRepository;
import com.bisekh.practice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public  UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public User createUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser != null){
            return  null;
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(hashedPassword);
        return userRepository.save(newUser);
    }

    @Override
    public User getMyDetails(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public boolean checkPassword(String rawPassword, String hashedPassword){
        return passwordEncoder.matches(rawPassword,hashedPassword);
    }

    @Override
    public Map<String,String> loginUser(User user)  throws  Exception{
        String enteredPassword = user.getPassword();
        User validUser = userRepository.findByEmail(user.getEmail());

        if(validUser == null){
            throw new RuntimeException("User Not found");
        }

        boolean isCorrectPassword = checkPassword(enteredPassword, validUser.getPassword());

        if(!isCorrectPassword){
            throw new RuntimeException("Incorrect password");
        }

        String accessToken = jwtUtil.generateAccessToken(validUser.getEmail(),String.valueOf(validUser.getRole()), validUser.getId());
        String refreshToken = jwtUtil.generateRefreshToken(validUser.getEmail(), String.valueOf(validUser.getRole()), validUser.getId());

        Map<String,String> loginValues = new HashMap<>();
        loginValues.put("id", String.valueOf(validUser.getId()));
        loginValues.put("email", validUser.getEmail());
        loginValues.put("role", String.valueOf(validUser.getRole()));
        loginValues.put("accessToken",accessToken);
        loginValues.put("refreshToken",refreshToken);

        return loginValues;
    }



}
