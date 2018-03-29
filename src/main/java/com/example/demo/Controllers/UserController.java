package com.example.demo.Controllers;

import com.example.demo.Resources.User;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final String CONTEXT = "/api/v1/User";

    @Autowired
    private UserService userService;

    @GetMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody User user){
        userService.addUser(user);
    }
}
