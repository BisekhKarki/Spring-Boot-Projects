package com.ecommerce.Ecommerce.Beginner.projects.controller;

import com.ecommerce.Ecommerce.Beginner.projects.components.JWT;
import com.ecommerce.Ecommerce.Beginner.projects.dto.UserDto;
import com.ecommerce.Ecommerce.Beginner.projects.model.UserModel;
import com.ecommerce.Ecommerce.Beginner.projects.repository.UserRepository;
import com.ecommerce.Ecommerce.Beginner.projects.response.Response;
import com.ecommerce.Ecommerce.Beginner.projects.service.Impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;
    public UserController(
            UserServiceImpl userService
    ){
        this.userService = userService;

    }


    @PostMapping("/register")
    public ResponseEntity<?> createUserProfile(
            @RequestBody UserDto userDto
            )throws  Exception{
        UserModel newUser = userService.createUser(userDto);
        if(newUser == null){
            return  ResponseEntity.status(400).body(new Response(400,"Failed to create user",null));

        }
        return  ResponseEntity.status(201).body(new Response(201,"User created successfully",newUser));

    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestBody UserDto userDto,
            @RequestHeader("Authorization") String accessToken
    )throws  Exception{
        String token = accessToken.replace("Bearer ","");
        UserModel newUser = userService.updateUser(userDto,token);
        if(newUser == null){
            return  ResponseEntity.status(400).body(new Response(400,"Failed to update user",null));

        }
        return  ResponseEntity.status(200).body(new Response(200,"User updated successfully",newUser));

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUSer(
            @RequestBody UserDto userDto
    )throws  Exception{
        Map<Object, Object> loggedInUser = userService.loginUser(userDto);
        if(loggedInUser == null){
            return  ResponseEntity.status(400).body(new Response(400,"Failed to login user",null));

        }
        return  ResponseEntity.status(200).body(new Response(200,"User logged in successfully",loggedInUser));

    }


}
