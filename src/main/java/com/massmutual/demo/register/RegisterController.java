package com.massmutual.demo.register;

import com.massmutual.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public User saveUser(@RequestBody RegisterRequest user){

            return registerService.addUser(user);

    }



}
