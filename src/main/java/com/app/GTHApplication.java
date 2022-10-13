package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages = "com.app")
public class GTHApplication {
	public static void main(String[] args) {
		SpringApplication.run(GTHApplication.class, args);
	}

}
