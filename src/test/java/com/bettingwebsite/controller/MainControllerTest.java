package com.bettingwebsite.controller;

import com.bettingwebsite.dao.BetRepository;
import com.bettingwebsite.dao.MatchRepository;
import com.bettingwebsite.dao.PlayerRepository;
import com.bettingwebsite.dao.UserDao;
import com.bettingwebsite.entity.Bet;
import com.bettingwebsite.entity.Match;
import com.bettingwebsite.entity.Player;
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
import org.springframework.mock.web.MockHttpServletRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {
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
    @DisplayName("Find player/players with PlayerRepository")
    public void findPlayers(){
        Optional<Player> player = playerRepository.findById(1L);
        assertTrue(player.isPresent());
        assertEquals("Nick",player.get().getFirstName());
        assertEquals("Kyrgios", player.get().getLastName());
        assertTrue(player.get().getAtp());

        List<Player> players = playerRepository.findAll();
        assertEquals(4,players.size());
    }

    @Test
    @DisplayName("Find user not null")
    public void findUser(){
        User user = userDao.findByUserName("admin");
        assertNotNull(user);
    }

    @Test
    @DisplayName("Find match not null")
    public void findMatchNotNull(){
        Optional<Match> match = matchRepository.findById(1L);
        assertTrue(match.isPresent());

        Optional<Player> player1 = playerRepository.findById(1L);
        Optional<Player> player2 = playerRepository.findById(2L);
        assertTrue(player1.isPresent());
        assertTrue(player2.isPresent());

        assertEquals(player1.get().getId(),match.get().getPlayer1().getId());
        assertEquals(player2.get().getId(),match.get().getPlayer2().getId());
    }

    @Test
    @DisplayName("Find distinct rounds")
    public void findDistinctRounds(){
        List<String> foundRounds = matchRepository.findDistinctByRound();
        assertTrue(foundRounds.contains("round1"));
        assertTrue(foundRounds.contains("round2"));
    }

    @Test
    @DisplayName("Test '/' endpoint")
    @WithMockUser(username = "admin")
    public void testHomeEndpoint() throws Exception{
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andReturn();
        MvcResult mvcResult = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("matches"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("userDetails"))
                .andExpect(model().attributeExists("rounds"))
                .andExpect(model().attributeExists("round"))
                .andExpect(model().attributeExists("matchForHTML"))
                .andExpect(model().attributeExists("matches"))
                .andExpect(model().attributeExists("atpChecked"))
                .andExpect(model().attributeExists("wtaChecked")).andReturn();


        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"matches");
    }

    @Test
    @DisplayName("Test '/' endpoint with valid arguments")
    @WithMockUser(username = "admin")
    public void testHomeEndpointWithValidArgument() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/?round=round1"))
                .andExpect(status().isOk())
                .andExpect(view().name("matches"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("userDetails"))
                .andExpect(model().attributeExists("rounds"))
                .andExpect(model().attributeExists("round"))
                .andExpect(model().attributeExists("matchForHTML"))
                .andExpect(model().attributeExists("matches"))
                .andExpect(model().attributeExists("atpChecked"))
                .andExpect(model().attributeExists("wtaChecked")).andReturn();

        MockHttpServletRequest request = mvcResult.getRequest();
        assertEquals("round1",request.getParameter("round"));

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"matches");
    }

    @Test
    @DisplayName("Test /processPointsSubmission without argument")
    @WithMockUser(username = "admin")
    public void testProcessPointsSubmissionWithoutArgument() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/processPointsSubmission"))
                .andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    @DisplayName("Test /processPointsSubmission with argument")
    @WithMockUser(username = "admin")
    public void testProcessPointsSubmissionWithArgument() throws Exception{
        betRepository.deleteAll();
        MvcResult mvcResult = mockMvc.perform(get("/processPointsSubmission?arguments=10inputMatch1player1;10inputMatch2player1"))
                .andExpect(status().is3xxRedirection()).andReturn();
        List<Bet> bets = betRepository.findAll();

        assertEquals(2,bets.size());
    }

    @Test
    @DisplayName("Test /processPointsSubmission edit existing bet")
    @WithMockUser(username = "admin")
    public void testProcessPointsSubmissionEditExistingBet() throws Exception{
        betRepository.deleteAll();
        MvcResult mvcResult = mockMvc.perform(get("/processPointsSubmission?arguments=10inputMatch1player1;10inputMatch2player1"))
                .andExpect(status().is3xxRedirection()).andReturn();
        List<Bet> bets = betRepository.findAll();
        assertEquals(2,bets.size());
        assertEquals(10, bets.get(0).getAmount());
        assertEquals(10,bets.get(1).getAmount());

        mockMvc.perform(get("/processPointsSubmission?arguments=30inputMatch1player1;60inputMatch2player1"))
                .andExpect(status().is3xxRedirection()).andReturn();

        List<Bet> updatedBets = betRepository.findAll();
        assertEquals(2,updatedBets.size());
        assertEquals(30,updatedBets.get(0).getAmount());
        assertEquals(60,updatedBets.get(1).getAmount());
    }

    @Test
    @DisplayName("Test own query in BetRepository")
    public void testBetRepository(){
        Bet bet = betRepository.findBetByUserIdAndMatchToBetIdAndBetOnPlayerId(1L,1L,1L);
        assertNotNull(bet);

        Bet bet2 = betRepository.findBetByUserIdAndMatchToBetIdAndBetOnPlayerId(2L,1L,1L);
        assertNull(bet2);

        Bet bet3 = betRepository.findBetByUserIdAndMatchToBetIdAndBetOnPlayerId(1L,2L,1L);
        assertNull(bet3);

        Bet bet4 = betRepository.findBetByUserIdAndMatchToBetIdAndBetOnPlayerId(1L,1L,2L);
        assertNull(bet4);
    }

}
