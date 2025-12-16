package com.blog.bloggin_platform.service;

import com.blog.bloggin_platform.dto.UserDto;
import com.blog.bloggin_platform.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {

    User registerUser(User user) throws  Exception;
    Map<Object,Object> loginUser(UserDto user) throws  Exception;
    User getMyDetails(String accessToken) throws  Exception;

}
