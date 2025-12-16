package com.bisekh.expense.controller;


import com.bisekh.expense.component.JwtUtil;
import com.bisekh.expense.dto.LoginRequest;
import com.bisekh.expense.model.User;
import com.bisekh.expense.repository.UserRepository;
import com.bisekh.expense.response.Response;
import com.bisekh.expense.serrvice.Impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private  final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public  UserController(UserServiceImpl userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder,UserRepository userRepository){
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try{
            if(user.getEmail().isEmpty() || user.getUsername().isEmpty()|| user.getPassword().isEmpty()){
                return ResponseEntity.status(500).body(new Response(500,"Field cannot be empty",null));
            }
            User newUser = userService.createUser(user);
            if(newUser == null){
                return ResponseEntity.status(500).body(new Response(500,"Failed to register user",null));

            }

            return ResponseEntity.status(201).body(new Response(201,"Field cannot be empty",newUser));

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(500).body(new Response(500,errorMessage,null));
        }
    }


    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(
            @RequestBody LoginRequest loginRequest
    ){
        try{
            User findUser = userRepository.findByEmail(loginRequest.getEmail());
            boolean isCorrectPassword = passwordEncoder.matches(loginRequest.getPassword(),findUser.getPassword());
            if(!isCorrectPassword){

                return ResponseEntity.status(400).body(
                        new Response(400,
                                "Incorrect password",
                                null)
                );
            }
            Map<Object,Object> user =  userService.loginUser(
                    loginRequest.getEmail(),
                    loginRequest.getPassword(),
                    findUser.getId(),
                    findUser.getUsername());

            if(user.isEmpty()){
                return ResponseEntity.status(500).body(
                        new Response(500,
                                "Something went wrong",
                                null)
                );
            }
            return ResponseEntity.status(200).body(
                    new Response(200,
                    "Logged in successful",
                    user)
            );

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(500).body(new Response(500,errorMessage,null));
        }
    }


    @GetMapping("/me")
    public ResponseEntity<?> getMyDetails(
            @RequestHeader("Authorization") String accessToken
    ){
        try{
            String token = accessToken.replace("Bearer ","");
            Map<?,?> userDetails = userService.getMyDetails(token);
            if(userDetails.isEmpty()){
                return  ResponseEntity.status(400).body(new Response(400,"No user found",null));
            }
            return  ResponseEntity.status(200).body(new Response(200,"User Details",userDetails));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return  ResponseEntity.status(500).body(new Response(500,errorMessage,null));
        }
    }



}
