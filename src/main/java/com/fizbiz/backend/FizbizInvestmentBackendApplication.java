package com.fizbiz.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.TemplateEngine;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
public class FizbizInvestmentBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FizbizInvestmentBackendApplication.class, args);
	}

	@Bean
	public TemplateEngine templateEngine(){
		return new TemplateEngine();
	}

}
