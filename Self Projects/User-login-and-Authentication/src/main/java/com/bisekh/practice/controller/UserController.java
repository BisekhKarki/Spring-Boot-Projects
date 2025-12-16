package com.bisekh.practice.controller;


import com.bisekh.practice.components.JwtUtil;
import com.bisekh.practice.model.User;
import com.bisekh.practice.repositories.UserRepository;
import com.bisekh.practice.response.Response;
import com.bisekh.practice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
//@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private  final JwtUtil jwtUtil ;

    public UserController(UserService userService, UserRepository userRepository, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/me/{id}")
    public ResponseEntity<?> getMyDetails(@PathVariable Long id, @RequestHeader("Authorization") String authHeader){
       try{
           String token = authHeader.replace("Bearer ","");
           Claims claims = jwtUtil.decodeToken(token);
           User user = userService.getMyDetails(id);

           Map<String,String> userValues = new HashMap<>();
           userValues.put("id",String.valueOf(user.getId()));
           userValues.put("email",String.valueOf(user.getEmail()));

           userValues.put("role",String.valueOf(user.getRole()));
           if(user == null){
               return  ResponseEntity.status(400).body(new Response(400,"No user found with id: "+id));
           }
           return  ResponseEntity.status(200).body(new Response(200,userValues));
       }catch (ExpiredJwtException e) {
           return ResponseEntity.status(401)
                   .body(new Response(401, "Token has expired"));
       }
       catch (Exception e) {
           return ResponseEntity.status(401)
                   .body(new Response(401, "Invalid token"));
       }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authHeader) throws Exception {

        try{
            String token = authHeader.replace("Bearer ","");
            Claims claims = jwtUtil.decodeToken(token);
            String email = claims.getSubject();
            String role = claims.get("role", String.class);
            Long id = claims.get("id", Long.class);


            if(!role.equals("ADMIN")){
                return  ResponseEntity.status(404).body(new Response(404,"Unauthorized email: "+email + " and role: "+role + " user Id: "+id));

            }

//        if(claims.)
            List<User> users = userService.getAllUser();
            if(users.isEmpty()){
                return  ResponseEntity.status(400).body(new Response(400,"No user found"));
            }
            return  ResponseEntity.status(200).body(new Response(200,users));
        }catch (ExpiredJwtException e) {
            return ResponseEntity.status(401)
                    .body(new Response(401, "Token has expired"));
        }
        catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(new Response(401, "Invalid token"));
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            User newUser = userService.createUser(user);
            if(newUser == null){
                return  ResponseEntity.status(400)
                        .body(new Response(400, "Failed to create user"));
            }
            Map<String, String> us = new HashMap<>();
            us.put("email", newUser.getEmail());
            us.put("id",String.valueOf(newUser.getId()));
            us.put("role",String.valueOf(newUser.getRole()));

            return  ResponseEntity.status(201)
                    .body(new Response(201, us));
        }catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new Response(500, e.getMessage()));
        }


        }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        try{
            Map<?,?> loginData = userService.loginUser(user);
            if( loginData.isEmpty()){
                return  ResponseEntity.status(400)
                        .body(new Response(400, ""));
            }


            return  ResponseEntity.status(200)
                    .body(new Response(200,loginData ));
        } catch (RuntimeException e) {
            return  ResponseEntity.status(400)
                    .body(new Response(400,e.getMessage() ));
        }

        catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new Response(500, e.getMessage()));
        }


    }




}
