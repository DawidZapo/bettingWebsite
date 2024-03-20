package com.bettingwebsite.controller;

import com.bettingwebsite.dao.*;
import com.bettingwebsite.entity.Result;
import com.bettingwebsite.entity.User;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class ResultsControllerTest {
    @Autowired
    private ResultRepository resultRepository;
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
    @Value("${sql.script.create.result}")
    private String sqlAddResult;
    @Value("${sql.script.delete.result}")
    private String sqlDeleteResult;
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

        jdbc.execute(sqlAddResult);
    }
    @AfterEach
    public void cleanUpDatabase(){
        jdbc.execute(sqlDeleteBet);

        jdbc.execute(sqlDeleteMatch);

        jdbc.execute(sqlDeletePlayers);

        jdbc.execute(sqlDeleteResult);

        jdbc.execute(sqlDeleteUserDetails);

        jdbc.execute(sqlDeleteUser);

    }

    @Test
    @DisplayName("Test result repository")
    public void testResultRepository(){
        Optional<Result> result = resultRepository.findById(1L);
        assertTrue(result.isPresent());
    }
    @Test
    @DisplayName("Test finding user with details and results")
    public void testFindingUserWithDetailsAndResult(){
        User user = userDao.findById(1L);
        assertNotNull(user.getUserDetails());
        assertNotNull(user.getResult());
    }
}
