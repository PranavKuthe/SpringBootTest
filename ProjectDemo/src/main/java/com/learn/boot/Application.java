package com.learn.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "com.learn.boot.processor", "com.learn.boot.ProjectDemo.controller",
		"com.learn.boot.ProjectDemo.model", "com.learn.boot.ProjectDemo.repository", "com.learn.boot.scheduler" })
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
