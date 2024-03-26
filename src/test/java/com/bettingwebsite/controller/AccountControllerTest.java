package com.bettingwebsite.controller;

import com.bettingwebsite.dao.UserDao;
import com.bettingwebsite.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDao userDao;
    @Value("${sql.script.create.user}")
    private String sqlAddUser;
    @Value("${sql.script.create.user2}")
    private String sqlAddUser2;
    @Value("${sql.script.create.user3}")
    private String sqlAddUser3;
    @Value("${sql.script.delete.user}")
    private String sqlDeleteUser;
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

    @BeforeEach
    public void setUpDatabase(){
        jdbc.execute(sqlAddUser);
        jdbc.execute(sqlAddUser2);
        jdbc.execute(sqlAddUser3);
        jdbc.execute(sqlCreateUserDetails);
        jdbc.execute(sqlCreateUserDetails2);
        jdbc.execute(sqlCreateUserDetails3);
        jdbc.execute(sqlCreateUserResult);
        jdbc.execute(sqlCreateUserResult2);
        jdbc.execute(sqlCreateUserResult3);
    }
    @AfterEach
    public void cleanUpDatabase(){
        jdbc.execute(sqlDeleteResult);
        jdbc.execute(sqlDeleteUserDetails);
        jdbc.execute(sqlDeleteUser);
    }

    @Test
    @DisplayName("Test users")
    public void testUses(){
        User user = userDao.findById(1L);
        assertNotNull(user);
        assertNotNull(user.getUserDetails());
        assertNotNull(user.getResult());
    }

    @Test
    @DisplayName("Test /account endpoint")
    @WithMockUser(username = "admin")
    public void testAccountEndPoint() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/account"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("userDetails"))
                .andExpect(model().attributeExists("user"))
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "account");

    }

    @Test
    @DisplayName("Test /account?success=true endpoint with argument")
    @WithMockUser(username = "admin")
    public void testAccountEndPointWithArgument() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/account?success=true"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("userDetails"))
                .andExpect(model().attributeExists("success"))
                .andExpect(model().attributeExists("user"))
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "account");
    }

    @Test
    @DisplayName("Test /account/changePassword enpoint")
    @WithMockUser(username = "admin")
    public void testAccountChangePasswordEndpoint() throws Exception{

        String oldPassword = userDao.findById(1L).getPassword();

        MvcResult mvcResult = mockMvc.perform(post("/account/changePassword")
                        .param("oldPassword","fun123")
                        .param("newPassword", "halo")
                        .param("confirmPassword","halo")
                        .param("userId","1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/account?success=true"))
                .andReturn();
        String newPassword = userDao.findById(1L).getPassword();

        assertNotEquals(oldPassword,newPassword);
    }

    @Test
    @DisplayName("Test /account/changePassword enpoint with bad old password")
    @WithMockUser(username = "admin")
    public void testAccountChangePasswordEndpointWithBadOldPassword() throws Exception{

        MvcResult mvcResult = mockMvc.perform(post("/account/changePassword")
                        .param("oldPassword","halo")
                        .param("newPassword", "halo")
                        .param("confirmPassword","halo")
                        .param("userId","1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/account?success=false"))
                .andReturn();
    }

    @Test
    @DisplayName("Test /account/changePassword enpoint with different passwords")
    @WithMockUser(username = "admin")
    public void testAccountChangePasswordEndpointWithDifferentPasswords() throws Exception{

        MvcResult mvcResult = mockMvc.perform(post("/account/changePassword")
                        .param("oldPassword","fun132")
                        .param("newPassword", "halo")
                        .param("confirmPassword","halooooo")
                        .param("userId","1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/account?success=false"))
                .andReturn();
    }
}
