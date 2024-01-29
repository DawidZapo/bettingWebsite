package com.bettingwebsite;

import com.bettingwebsite.entity.User;
import com.bettingwebsite.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BettingWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(BettingWebsiteApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserService userService) {

		return runner -> {
			User user = userService.findByUserName("admin");
			System.out.println(user);
		};
	}

}
