package com.bettingwebsite.controller;

import com.bettingwebsite.dao.BetRepository;
import com.bettingwebsite.dao.MatchRepository;
import com.bettingwebsite.entity.Bet;
import com.bettingwebsite.entity.Match;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @Value("${sql.script.create.player3atp}")
    private String sqlAddPlayer3Atp;
    @Value("${sql.script.create.player4atp}")
    private String sqlAddPlayer4Atp;
    @Value("${sql.script.create.player1wta}")
    private String sqlAddPlayer1Wta;
    @Value("${sql.script.create.player2wta}")
    private String sqlAddPlayer2Wta;
    @Value("${sql.script.create.player3wta}")
    private String sqlAddPlayer3Wta;
    @Value("${sql.script.create.player4wta}")
    private String sqlAddPlayer4Wta;

    @Value("${sql.script.delete.player}")
    private String sqlDeletePlayers;
    @Value("${sql.script.create.user}")
    private String sqlAddUser;
    @Value("${sql.script.delete.user}")
    private String sqlDeleteUser;
    @Value("${sql.script.create.match1.round1}")
    private String sqlCreateMatch1Round1;
    @Value("${sql.script.create.match2.round2}")
    private String sqlCreateMatch2Round2;
    @Value("${sql.script.create.match3.round1}")
    private String sqlCreateMatch3Round1;
    @Value("${sql.script.create.match4.round2}")
    private String sqlCreateMatch4Round2;
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
    @Autowired
    private MatchRepository matchRepository;

    @BeforeEach
    public void setUpDatabase(){
        jdbc.execute(sqlAddPlayer1Atp);
        jdbc.execute(sqlAddPlayer2Atp);
        jdbc.execute(sqlAddPlayer3Atp);
        jdbc.execute(sqlAddPlayer4Atp);

        jdbc.execute(sqlAddPlayer1Wta);
        jdbc.execute(sqlAddPlayer2Wta);
        jdbc.execute(sqlAddPlayer3Wta);
        jdbc.execute(sqlAddPlayer4Wta);

        jdbc.execute(sqlAddUser);

        jdbc.execute(sqlCreateMatch1Round1);
        jdbc.execute(sqlCreateMatch2Round2);
        jdbc.execute(sqlCreateMatch3Round1);
        jdbc.execute(sqlCreateMatch4Round2);

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

    @Test
    @DisplayName("Find if round is eligible for betting")
    public void findIfRoundIsEligibleForBetting(){
        List<Match> matchesRound2 = matchRepository.findAllByRoundAndScoreIsNullAndWinnerIsNull("round2");
        List<Match> allRound2Matches = matchRepository.findAllByRound("round2");
        assertEquals(allRound2Matches.size(),matchesRound2.size());

        List<Match> matchesRound1 = matchRepository.findAllByRoundAndScoreIsNullAndWinnerIsNull("round1");

        assertEquals(0,matchesRound1.size());
    }

    @Test
    @DisplayName("Test /bets endpoint")
    @WithMockUser(username = "admin")
    public void testBetsEndpoint() throws Exception{
        MvcResult mvcResult =  mockMvc.perform(get("/bets"))
                .andExpect(status().isOk())
                .andExpect(view().name("bets"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("userDetails"))
                .andExpect(model().attributeExists("bets"))
                .andExpect(model().attributeExists("matchForHTML"))
                .andExpect(model().attributeExists("rounds"))
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"bets");
    }

    @Test
    @DisplayName("Test /bets/delete delete")
    @WithMockUser(username = "admin")
    public void testBetsDeletePreMatch() throws Exception{
        MvcResult mvcResult = mockMvc.perform(post("/bets/delete").param("id","3").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bets"))
                .andReturn();

        List<Bet> bets = betRepository.findAll();
        assertEquals(3,bets.size());

    }
}
