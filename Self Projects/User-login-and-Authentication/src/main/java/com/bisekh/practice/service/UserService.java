package com.bisekh.practice.service;

import com.bisekh.practice.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public interface UserService {

    User createUser(User user);
    User getMyDetails(Long id);
    List<User> getAllUser();
    Map<?,?> loginUser(User user) throws  Exception;

}
