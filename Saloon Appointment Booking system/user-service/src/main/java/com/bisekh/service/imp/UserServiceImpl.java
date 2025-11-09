package com.bisekh.service.imp;

import com.bisekh.exception.UserException;
import com.bisekh.model.User;
import com.bisekh.repository.UserRepository;
import com.bisekh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserException {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("User Not found") ;
    }

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserException("User with the given Id not found");
        }

        userRepository.deleteById(user.get().getId());

    }

    @Override
    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFullName(user.getFullName());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPhone(user.getPhone());
                    existingUser.setRole(user.getRole());
                    existingUser.setUpdatedAt(LocalDateTime.now());
                    existingUser.setUsername(user.getUsername());
                    User updatedUser = userRepository.save(existingUser);
                    return ResponseEntity.ok(updatedUser);
                }).orElseGet(() -> ResponseEntity.notFound().build()).getBody();


    }
}
