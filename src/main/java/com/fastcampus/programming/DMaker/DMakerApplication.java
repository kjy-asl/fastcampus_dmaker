package com.fastcampus.programming.DMaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class DMakerApplication {

	public static void main(String[] args) {

		SpringApplication.run(DMakerApplication.class, args);
	}

}