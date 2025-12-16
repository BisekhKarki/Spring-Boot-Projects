package com.bisekh.Task.service.Impl;


import com.bisekh.Task.repository.UserRepository;
import com.bisekh.Task.service.UserService;
import lombok.RequiredArgsConstructor;
import com.bisekh.Task.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private  final UserRepository userRepository;

//    private final JdbcTemplate jdbcTemplate;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found with id "+ id));
    }

    @Override
    public Boolean findExistingUser(String email) {
       User existing =  userRepository.findByEmail(email);
       System.out.println("User: "+existing);

        return existing != null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public User createUser(User user) {

        userRepository.save(user);
        return user;
    }

//    @Override
//    public User updateUser(User user, Long id) {
//        User updateUser = new User();
//        updateUser.setName(user.getName());
//        updateUser.setEmail(user.getEmail());
//        return updateUser;
//    }
}
