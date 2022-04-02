package com.teamY.angryBox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.teamY"})
public class AngryBoxApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngryBoxApplication.class, args);
	}

}
