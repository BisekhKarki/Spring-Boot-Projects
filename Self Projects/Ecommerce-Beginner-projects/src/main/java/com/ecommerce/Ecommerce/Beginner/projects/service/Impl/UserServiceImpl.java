package com.ecommerce.Ecommerce.Beginner.projects.service.Impl;

import com.ecommerce.Ecommerce.Beginner.projects.components.JWT;
import com.ecommerce.Ecommerce.Beginner.projects.dto.UserDto;
import com.ecommerce.Ecommerce.Beginner.projects.exception.ApiException;
import com.ecommerce.Ecommerce.Beginner.projects.model.UserModel;
import com.ecommerce.Ecommerce.Beginner.projects.repository.UserRepository;
import com.ecommerce.Ecommerce.Beginner.projects.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWT jwt;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
        UserRepository userRepository,
        JWT jwt,
        PasswordEncoder passwordEncoder
    ){
        this.jwt = jwt;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel createUser(UserDto userDto) throws Exception {
        try{
            UserModel existingUser = userRepository.findByEmail(userDto.getEmail());
            if(existingUser != null){
                throw new ApiException("User Already exists with the given email: "+userDto.getEmail());
            }

            if(userDto.getPassword().length() < 8){
                throw new ApiException("Password cannot be less than 8 characters.");
            }

            UserModel newUser = new UserModel();
            String hashedPassword = passwordEncoder.encode(userDto.getPassword());
            newUser.setEmail(userDto.getEmail());
            newUser.setUsername(userDto.getUsername());
            newUser.setPassword(hashedPassword);
            return userRepository.save(newUser);
        }catch (ApiException apiException){
            throw  new ApiException("Internal Server Error");
        }

    }

    @Override
    public UserModel updateUser(UserDto userDto,String accessToken) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId = token.get("userId",Long.class);
            UserModel existingUser = userRepository.findById(userId).orElseThrow(()-> new ApiException("User Already exists with the given email: "+userDto.getEmail()));
            if(userDto.getPassword().length() < 8){
                throw new ApiException("Password cannot be less than 8 characters.");
            }
            String hashedPassword = passwordEncoder.encode(userDto.getPassword());
            existingUser.setPassword(hashedPassword);
            return userRepository.save(existingUser);
        }catch (ApiException apiException){
            throw  new ApiException("Internal Server Error");
        }
    }

    @Override
    public Map<Object, Object> loginUser(UserDto userDto) throws Exception {
        try{
            UserModel existingUser = userRepository.findByEmail(userDto.getEmail());
            if(existingUser == null){
                throw new ApiException("User does not exists with the given email: "+userDto.getEmail());
            }
            boolean validPassword = passwordEncoder.matches(userDto.getPassword(),existingUser.getPassword());
            if(!validPassword){
                throw new ApiException("Invalid password. Please try again!!");

            }
            String accessToken = jwt.generateAccessToken(existingUser.getId(), userDto.getEmail(), existingUser.getUsername());
            String refreshToken = jwt.generateRefreshToken(existingUser.getId(), userDto.getEmail(), existingUser.getUsername());
            Map<Object,Object> val = new HashMap<>();
            val.put("username",existingUser.getUsername());
            val.put("email",existingUser.getEmail());
            val.put("accessToken",accessToken);
            val.put("refreshToken",refreshToken);
            return val;
        }catch (ApiException apiException){
            throw  new ApiException("Internal Server Error");
        }
    }
}
