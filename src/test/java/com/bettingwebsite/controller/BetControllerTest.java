package com.bettingwebsite.controller;

import com.bettingwebsite.dao.BetRepository;
import com.bettingwebsite.entity.Bet;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class BetControllerTest {
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private MockMvc mockMvc;
    @Value("${sql.script.create.player1atp}")
    private String sqlAddPlayer1Atp;
    @Value("${sql.script.create.player2atp}")
    private String sqlAddPlayer2Atp;
    @Value("${sql.script.create.player1wta}")
    private String sqlAddPlayer1Wta;
    @Value("${sql.script.create.player2wta}")
    private String sqlAddPlayer2Wta;
    @Value("${sql.script.delete.player}")
    private String sqlDeletePlayers;
    @Value("${sql.script.create.user}")
    private String sqlAddUser;
    @Value("${sql.script.delete.user}")
    private String sqlDeleteUser;
    @Value("${sql.script.create.match.round1}")
    private String sqlCreateMatchRound1;
    @Value("${sql.script.create.match.round2}")
    private String sqlCreateMatchRound2;
    @Value("${sql.script.delete.match}")
    private String sqlDeleteMatch;
    @Value("${sql.script.create.user.details}")
    private String sqlCreateUserDetails;
    @Value("${sql.script.delete.user.details}")
    private String sqlDeleteUserDetails;
    @Value("${sql.script.create.bet1}")
    private String sqlCreateBet1;
    @Value("${sql.script.create.bet2}")
    private String sqlCreateBet2;
    @Value("${sql.script.create.bet3}")
    private String sqlCreateBet3;
    @Value("${sql.script.create.bet4}")
    private String sqlCreateBet4;
    @Value("${sql.script.delete.bet}")
    private String sqlDeleteBet;
    @Autowired
    private BetRepository betRepository;

    @BeforeEach
    public void setUpDatabase(){
        jdbc.execute(sqlAddPlayer1Atp);
        jdbc.execute(sqlAddPlayer2Atp);
        jdbc.execute(sqlAddPlayer1Wta);
        jdbc.execute(sqlAddPlayer2Wta);

        jdbc.execute(sqlAddUser);

        jdbc.execute(sqlCreateMatchRound1);
        jdbc.execute(sqlCreateMatchRound2);

        jdbc.execute(sqlCreateBet1);
        jdbc.execute(sqlCreateBet2);
        jdbc.execute(sqlCreateBet3);
        jdbc.execute(sqlCreateBet4);

        jdbc.execute(sqlCreateUserDetails);
    }
    @AfterEach
    public void cleanUpDatabase(){
        jdbc.execute(sqlDeleteBet);

        jdbc.execute(sqlDeleteMatch);

        jdbc.execute(sqlDeletePlayers);

        jdbc.execute(sqlDeleteUserDetails);

        jdbc.execute(sqlDeleteUser);

    }

    @Test
    @DisplayName("Find all bets for user")
    public void findAllBetsForUser(){
        List<Bet> bets = betRepository.findByUserId(1L);
        assertEquals(4,bets.size());
        for(var bet : bets){
            assertEquals(1,bet.getUser().getId());
        }
    }
}
