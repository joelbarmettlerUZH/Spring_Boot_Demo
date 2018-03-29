package com.example.demo.Repositories;

import com.example.demo.Entities.UniversityEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UniversityRepository extends CrudRepository<UniversityEntity, Long>{
    List<UniversityEntity> findByName(String name);
}
