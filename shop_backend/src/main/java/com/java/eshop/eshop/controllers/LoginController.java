package com.java.eshop.eshop.controllers;

import com.java.eshop.eshop.dto.ResponseDTO;
import com.java.eshop.eshop.dto.UserDTO;
import com.java.eshop.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<ResponseDTO<String>> getUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(ResponseDTO.<String>builder()
                .data(userService.getUserRole(userDTO.getUserName()))
                .build(), HttpStatus.OK);
    }
}
