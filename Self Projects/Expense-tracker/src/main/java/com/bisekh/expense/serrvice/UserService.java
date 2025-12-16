package com.bisekh.expense.serrvice;

import com.bisekh.expense.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {


    Map<String, Object> getMyDetails(String accessToken) throws  Exception;
    User createUser(User user);
    Map<Object,Object> loginUser(String email,String password, Long id,String username) throws  Exception;

}
