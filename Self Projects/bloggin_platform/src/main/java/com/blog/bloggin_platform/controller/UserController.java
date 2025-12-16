package com.blog.bloggin_platform.controller;


import com.blog.bloggin_platform.components.JWT;
import com.blog.bloggin_platform.dto.UserDto;
import com.blog.bloggin_platform.model.User;
import com.blog.bloggin_platform.repository.UserRepository;
import com.blog.bloggin_platform.response.Response;
import com.blog.bloggin_platform.service.Implementations.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final UserServiceImpl userService;

    public  UserController(
                           UserServiceImpl userService
    ){

        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyDetails(
            @RequestHeader("Authorization") String accessToken
    ) throws  Exception{
        String token = accessToken.replace("Bearer ","");
        User myDetails = userService.getMyDetails(token);
       return ResponseEntity.status(200).body(new Response(200,"My details",myDetails));
    }

    @PostMapping("/register")
    public  ResponseEntity<?> registerUser(
            @RequestBody User user
    )throws  Exception {
        User newUser = userService.registerUser(user);
        return  ResponseEntity.status(201).body(new Response(201,"User created successfully",newUser));
    }

    @PostMapping("/login")
    public  ResponseEntity<?> loginUser(
            @RequestBody UserDto user
    )throws  Exception {
        Map<Object, Object> loggedInUser = userService.loginUser(user);
        return  ResponseEntity.status(200).body(new Response(200,"User logged In successfully",loggedInUser));
    }




}
