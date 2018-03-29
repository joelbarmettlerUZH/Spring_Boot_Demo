package com.example.demo.Services;

import com.example.demo.Resources.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>(Arrays.asList(
            new User("Joel"),
            new User("Marius"),
            new User("Daniela")
        )
    );

    public List<User> getUsers(){
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }

}
