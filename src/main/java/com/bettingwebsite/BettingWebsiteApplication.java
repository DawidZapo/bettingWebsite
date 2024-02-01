package com.bettingwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BettingWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(BettingWebsiteApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(BetRepository betRepository, MatchRepository matchRepository, PlayerRepository playerRepository, UserService userService) {
//
//		return runner -> {
//			Optional<Bet> result = betRepository.findById(1L);
//			Bet bet = null;
//
//			if(result.isPresent()){
//				bet = result.get();
//			}
//			else{
//				throw new RuntimeException();
//			}
//
//			betRepository.delete(bet);
//			System.out.println(bet);
//
//		};
//	}

}
