package com.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
//import com.app.config.RibbonConfiguration;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
//import org.springframework.cloud.netflix.ribbon.RibbonClients;

//@AutoConfigureAfter(RibbonAutoConfiguration.class)
//@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
@SpringBootApplication(scanBasePackages = "com.app")
//@EnableDiscoveryClient
public class GTHApplication {
	public static void main(String[] args) {
		SpringApplication.run(GTHApplication.class, args);
	}

}
