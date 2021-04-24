package com.shan.mydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydemoApplication.class, args);
	}

}
