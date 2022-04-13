package com.teamY.angryBox;

import com.teamY.angryBox.config.properties.AppProperties;
import com.teamY.angryBox.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class
})
@ComponentScan(basePackages = {"com.teamY"})
public class AngryBoxApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AngryBoxApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(AngryBoxApplication.class, args);
	}

}
