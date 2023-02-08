package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.response.CommonResponse;
import com.bezkoder.springjwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.bezkoder.springjwt.constant.Constant.SUCCESS;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping()
    public ResponseEntity<?> getProfile(Principal principal) throws NoSuchFieldException {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message(SUCCESS)
                .data(userService.getProfile(principal.getName()))
                .build(), HttpStatus.OK);
    }
}
