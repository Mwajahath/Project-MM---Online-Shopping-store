package com.massmutual.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaRepositories
@EnableSwagger2
public class MMOnlineShoppingApplication {

	public static void main(String[] args) {
		
//		SpringApplication.run(MMOnlineShoppingApplication.class, args);
		ApplicationContext context= SpringApplication.run(MMOnlineShoppingApplication.class, args);
	
	}

}
 