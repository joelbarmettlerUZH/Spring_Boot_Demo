package com.example.demo.Repositories;

import com.example.demo.Entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long>{
    List<UserEntity> findByName(String name);
    List<UserEntity> findByUniversityName(String name);
}
