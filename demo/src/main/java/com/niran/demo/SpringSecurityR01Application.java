package com.niran.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringSecurityR01Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityR01Application.class, args);
	}

}
