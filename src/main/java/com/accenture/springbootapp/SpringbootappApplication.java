package com.accenture.springbootapp;

import com.accenture.springbootapp.DAO.StudentDAO;
import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableMongoRepositories(basePackageClasses = StudentDAO.class)
public class SpringbootappApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootappApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {

        return new RestTemplate();
    }


}
