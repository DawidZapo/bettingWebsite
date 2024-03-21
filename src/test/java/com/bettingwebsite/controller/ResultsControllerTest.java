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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @Value("${sql.script.create.user2}")
    private String sqlAddUser2;
    @Value("${sql.script.create.user3}")
    private String sqlAddUser3;
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
    @Value("${sql.script.create.user.details2}")
    private String sqlCreateUserDetails2;
    @Value("${sql.script.create.user.details3}")
    private String sqlCreateUserDetails3;
    @Value("${sql.script.delete.user.details}")
    private String sqlDeleteUserDetails;
    @Value("${sql.script.create.user.result}")
    private String sqlCreateUserResult;
    @Value("${sql.script.create.user.result2}")
    private String sqlCreateUserResult2;
    @Value("${sql.script.create.user.result3}")
    private String sqlCreateUserResult3;
    @Value("${sql.script.delete.user.result}")
    private String sqlDeleteResult;
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
        jdbc.execute(sqlAddUser2);
        jdbc.execute(sqlAddUser3);

        jdbc.execute(sqlCreateMatch1Round1);
        jdbc.execute(sqlCreateMatch2Round2);

        jdbc.execute(sqlCreateBet);

        jdbc.execute(sqlCreateUserDetails);
        jdbc.execute(sqlCreateUserDetails2);
        jdbc.execute(sqlCreateUserDetails3);

        jdbc.execute(sqlCreateUserResult);
        jdbc.execute(sqlCreateUserResult2);
        jdbc.execute(sqlCreateUserResult3);
    }
    @AfterEach
    public void cleanUpDatabase(){
        jdbc.execute(sqlDeleteBet);

        jdbc.execute(sqlDeleteMatch);

        jdbc.execute(sqlDeletePlayers);

        jdbc.execute(sqlDeleteResult);

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

    @Test
    @DisplayName("Test find all users")
    public void testFindAllUsers(){
        List<User> users = userDao.findAll();
        assertEquals(3,users.size());
    }

    @Test
    @DisplayName("Test method findAllExceptAdminAndDisabled()")
    public void testFindAllExceptAdminAndDisabled(){
        List<User> users = userDao.findAllExceptAdminAndDisabled();
        assertEquals(1,users.size());
    }

    @Test
    @DisplayName("Test /results endpoint")
    @WithMockUser(username = "admin")
    public void testResultsEndpoint() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/results"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("userDetails"))
                .andExpect(model().attributeExists("users"))
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"results");
    }
}
