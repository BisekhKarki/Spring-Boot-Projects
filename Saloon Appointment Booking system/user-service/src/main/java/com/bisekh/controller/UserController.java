package com.bisekh.controller;


import com.bisekh.exception.UserException;
import com.bisekh.model.User;
import com.bisekh.repository.UserRepository;
import com.bisekh.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

   private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user){
        User createdUser = userService.createUser(user);
        return  new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUser(){
        List<User> users = userService.getUser();
        return  new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)  throws  Exception {
    User userById = userService.getUserById(id);
        return  new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) throws  Exception{
        User updatedtedUser = userService.updateUser(id,user);
        return  new ResponseEntity<>(updatedtedUser, HttpStatus.OK);

    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws  Exception{
    userService.deleteUser(id);

        return  new ResponseEntity<>("User Deleted", HttpStatus.ACCEPTED);
    }

}
