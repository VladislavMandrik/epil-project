package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EpilProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(EpilProjectApplication.class, args);
	}

}
