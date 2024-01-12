package com.java.eshop.eshop.services;

import com.java.eshop.eshop.model.UserEntity;
import com.java.eshop.eshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String getUserRole(String userName){
        Optional<UserEntity> user = userRepository.findByUserName(userName);
        if(user.isPresent()){
            return user.get().getRole();
        } else {
            return "";
        }

    }

}
