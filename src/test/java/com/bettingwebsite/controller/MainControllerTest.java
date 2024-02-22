package com.bettingwebsite.controller;

import com.bettingwebsite.dao.MatchRepository;
import com.bettingwebsite.dao.PlayerRepository;
import com.bettingwebsite.dao.UserDao;
import com.bettingwebsite.entity.Match;
import com.bettingwebsite.entity.Player;
import com.bettingwebsite.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class MainControllerTest {
    @Autowired
    private JdbcTemplate jdbc;
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
    @Value("${sql.script.create.match}")
    private String sqlCreateMatch;
    @Value("${sql.script.delete.match}")
    private String sqlDeleteMatch;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MainController mainController;


    @BeforeEach
    public void setUpDatabase(){
        jdbc.execute(sqlAddPlayer1Atp);
        jdbc.execute(sqlAddPlayer2Atp);
        jdbc.execute(sqlAddPlayer1Wta);
        jdbc.execute(sqlAddPlayer2Wta);

        jdbc.execute(sqlAddUser);

        jdbc.execute(sqlCreateMatch);
    }
    @AfterEach
    public void cleanUpDatabase(){
        jdbc.execute(sqlDeleteMatch);

        jdbc.execute(sqlDeletePlayers);

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
    @DisplayName("Test private method in MainController")
    public void testPrivateMethods(){
        Optional<Player> player = playerRepository.findById(1L);
        assertTrue(player.isPresent());

//        ReflectionTestUtils.invokeMethod(mainController,"getOdds",player.get(),new Match());
    }
}
