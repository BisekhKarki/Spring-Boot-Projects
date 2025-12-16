package com.bisekh.Task.controller;

import com.bisekh.Task.ErrorMesage.ErrorResponse;
import com.bisekh.Task.dto.UserDto;
import com.bisekh.Task.model.User;
import com.bisekh.Task.repository.UserRepository;
import com.bisekh.Task.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        try {
            if (userDto.getName() == null || userDto.getEmail() == null) {
                return ResponseEntity.status(400)
                        .body(new ErrorResponse(400, "Name and Email are required"));
            }

            Boolean existingUser = userService.findExistingUser(userDto.getEmail());

            if(existingUser){
                return  ResponseEntity.status(400).body(new ErrorResponse(400,"User with email "+userDto.getEmail() +" already exists"));
            }

            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());

            User savedUser = userService.createUser(user);

            return ResponseEntity.status(201)
                    .body(new ErrorResponse(201, savedUser));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal Server Error"));
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers(){
        try{
            List<User> users = userService.getAllUsers();
            if(!users.isEmpty()){
                return  ResponseEntity.status(200).body(new ErrorResponse(200,users));
            }else{
                return  ResponseEntity.status(400).body(new ErrorResponse(200,"No users data available"));
            }
        }catch (Exception e){
            return  ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(
            @PathVariable Long userId
    ){
        try{
            User user = userService.getUserById(userId);
            if(user != null){
                return  ResponseEntity.ok(user);
            }else{
                return  ResponseEntity.status(400).body(new ErrorResponse(400,"No user Found"));
            }


        }catch (Exception e){
            return  ResponseEntity.status(500).body(e.getMessage());
        }
    }

}

