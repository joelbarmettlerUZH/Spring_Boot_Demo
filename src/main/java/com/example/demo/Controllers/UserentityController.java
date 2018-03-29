package com.example.demo.Controllers;

import com.example.demo.Entities.UniversityEntity;
import com.example.demo.Entities.UserEntity;
import com.example.demo.Services.UserentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserentityController {
    private final String CONTEXT = "/api/v1/Userentity";

    @Autowired
    private UserentityService userentityService;

    @GetMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<UserEntity> getUsers(){
        return userentityService.getUsers();
    }

    @PostMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserEntity user){
        userentityService.addUser(user);
    }
}
