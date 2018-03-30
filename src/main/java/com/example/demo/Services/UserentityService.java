package com.example.demo.Services;
import com.example.demo.Entities.UserEntity;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserentityService {

    @Autowired
    private UserRepository userRepository;
    public List<UserEntity> getUsers() {
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }
    @Autowired
    @Async
    public CompletableFuture<List<UserEntity>> getUsersAsync() throws InterruptedException {
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        Thread.sleep(5000L);
        return CompletableFuture.completedFuture(users);
    }

    public void addUser(UserEntity user){
        userRepository.save(user);
    }
}
