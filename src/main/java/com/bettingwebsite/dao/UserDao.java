package com.bettingwebsite.dao;

import com.bettingwebsite.entity.User;

public interface UserDao {

    User findByUserName(String userName);
    User findById(Long userNameId);
    User save(User user);

}
