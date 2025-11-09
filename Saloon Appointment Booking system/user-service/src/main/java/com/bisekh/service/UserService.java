package com.bisekh.service;

import com.bisekh.exception.UserException;
import com.bisekh.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id) throws UserException;
    List<User> getUser();
    void deleteUser(Long id) throws UserException;
    User updateUser(Long id, User user);
}
