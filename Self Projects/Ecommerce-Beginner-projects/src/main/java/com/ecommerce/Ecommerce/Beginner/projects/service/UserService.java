package com.ecommerce.Ecommerce.Beginner.projects.service;

import com.ecommerce.Ecommerce.Beginner.projects.dto.UserDto;
import com.ecommerce.Ecommerce.Beginner.projects.model.UserModel;

import java.util.Map;

public interface UserService {
    UserModel createUser(UserDto userDto) throws  Exception;
    UserModel updateUser(UserDto userDto,String accessToken) throws  Exception;
    Map<Object,Object> loginUser(UserDto userDto) throws  Exception;


}
