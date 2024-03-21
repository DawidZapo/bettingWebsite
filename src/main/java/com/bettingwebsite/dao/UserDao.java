package com.bettingwebsite.dao;

import com.bettingwebsite.entity.User;

import java.util.List;

public interface UserDao {

    User findByUserName(String userName);
    User findById(Long userNameId);
    User save(User user);
    List<User> findAll();
    List<User> findAllExceptAdminAndDisabled();

}
