package com.microservices.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;


@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServer {
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigServer.class, args);
	}
	
}
