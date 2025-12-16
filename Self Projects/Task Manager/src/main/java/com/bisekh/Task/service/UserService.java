package com.bisekh.Task.service;


import com.bisekh.Task.dto.UserDto;
import com.bisekh.Task.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User getUserById(
            Long id
    );

    Boolean findExistingUser(String email);

    List<User> getAllUsers();
    User createUser(User user);

}
