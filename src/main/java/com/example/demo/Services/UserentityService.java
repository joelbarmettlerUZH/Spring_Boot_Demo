package com.example.demo.Services;
import com.example.demo.Entities.UserEntity;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserentityService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getUsers(){
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public void addUser(UserEntity user){
        userRepository.save(user);
    }
}
