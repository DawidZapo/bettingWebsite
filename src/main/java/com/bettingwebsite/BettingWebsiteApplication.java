package com.bettingwebsite;

import com.bettingwebsite.dao.BetRepository;
import com.bettingwebsite.dao.MatchRepository;
import com.bettingwebsite.dao.PlayerRepository;
import com.bettingwebsite.entity.Bet;
import com.bettingwebsite.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class BettingWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(BettingWebsiteApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(BetRepository betRepository, MatchRepository matchRepository, PlayerRepository playerRepository, UserService userService) {

		return runner -> {
			Optional<Bet> result = betRepository.findById(1L);
			Bet bet = null;

			if(result.isPresent()){
				bet = result.get();
			}
			else{
				throw new RuntimeException();
			}

			betRepository.delete(bet);
			System.out.println(bet);

		};
	}

}
