package com.example.demo.Controllers;

import com.example.demo.Entities.UserEntity;
import com.example.demo.Services.UserentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserentityAsyncController {
    private final String CONTEXT = "/api/v1/Userentityasync";

    @Autowired
    private UserentityService userentityService;

    @GetMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<List<UserEntity>> getUsersAsync() throws InterruptedException {
        userentityService.getUsersAsync();
        userentityService.getUsersAsync();
        return userentityService.getUsersAsync();
    }

}
