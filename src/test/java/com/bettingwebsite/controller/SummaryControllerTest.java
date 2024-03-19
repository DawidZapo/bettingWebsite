package com.bettingwebsite.controller;

import com.bettingwebsite.dao.BetRepository;
import com.bettingwebsite.dao.MatchRepository;
import com.bettingwebsite.dao.PlayerRepository;
import com.bettingwebsite.dao.UserDao;
import com.bettingwebsite.entity.Match;
import com.bettingwebsite.service.UserService;
import com.bettingwebsite.service.match.MatchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class SummaryControllerTest {
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
    @Value("${sql.script.create.match1.round1}")
    private String sqlCreateMatch1Round1;
    @Value("${sql.script.create.match2.round2}")
    private String sqlCreateMatch2Round2;
    @Value("${sql.script.delete.match}")
    private String sqlDeleteMatch;
    @Value("${sql.script.create.user.details}")
    private String sqlCreateUserDetails;
    @Value("${sql.script.delete.user.details}")
    private String sqlDeleteUserDetails;
    @Value("${sql.script.create.bet1}")
    private String sqlCreateBet;
    @Value("${sql.script.delete.bet}")
    private String sqlDeleteBet;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private BetRepository betRepository;
    @Autowired
    private UserDao userDao;
    @Mock
    private MatchService matchService;
    @Mock
    private UserService userService;
    @InjectMocks
    private MainController mainController;


    @BeforeEach
    public void setUpDatabase(){
        jdbc.execute(sqlAddPlayer1Atp);
        jdbc.execute(sqlAddPlayer2Atp);
        jdbc.execute(sqlAddPlayer1Wta);
        jdbc.execute(sqlAddPlayer2Wta);

        jdbc.execute(sqlAddUser);

        jdbc.execute(sqlCreateMatch1Round1);
        jdbc.execute(sqlCreateMatch2Round2);

        jdbc.execute(sqlCreateBet);

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
    @DisplayName("Test matchRepository method findAllByBetsIsNotEmpty")
    public void testFindALlByBetsIsNotEmpty(){
        List<Match> allMatches = matchRepository.findAll();
        List<Match> matchesWithBets = matchRepository.findAllByBetsIsNotEmpty();
        List<Match> newListToTestMatches = new ArrayList<>();

        for(var match : allMatches){
            if(!match.getBets().isEmpty()){
                newListToTestMatches.add(match);
            }
        }

        assertEquals(matchesWithBets.size(),newListToTestMatches.size());
    }

    @Test
    @DisplayName("Test /summary endpoint")
    @WithMockUser(username = "admin")
    public void testSummaryEndpoint() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/summary"))
                .andExpect(status().isOk())
                .andExpect(view().name("summary"))
                .andExpect(model().attributeExists("matchForHTML"))
                .andExpect(model().attributeExists("rounds"))
                .andExpect(model().attributeExists("matchesWithBets"))
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"summary");
    }

}
