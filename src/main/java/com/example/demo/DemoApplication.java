package com.example.demo;

import com.example.demo.Entities.UniversityEntity;
import com.example.demo.Repositories.UniversityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import java.util.concurrent.Executor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /*
    @Bean
    public CommandLineRunner loadData(UniversityRepository universityRepository) {
        return (args) -> {
            universityRepository.save(new UniversityEntity("UZH", 20000, 720));
            universityRepository.save(new UniversityEntity("ETHZ", 18000, 980));
            universityRepository.save(new UniversityEntity("ZHAW", 9000, 530));
            universityRepository.save(new UniversityEntity("EPFL", 12000, 600));
            universityRepository.save(new UniversityEntity("UNILU", 120, 440));
        };
    }
    */

}
