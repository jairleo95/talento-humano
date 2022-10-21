package com.app.gthconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class GthConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GthConfigServerApplication.class, args);
	}

}
