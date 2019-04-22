package com.learn.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.learn.boot.ProjectDemo.repository")
public class ApplicationConfig {

}
