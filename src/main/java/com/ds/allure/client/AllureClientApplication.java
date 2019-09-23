package com.ds.allure.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class AllureClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllureClientApplication.class, args);
	}

}
