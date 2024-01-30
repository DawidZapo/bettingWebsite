package com.bettingwebsite.service;

import com.bettingwebsite.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public User findByUserName(String userName);
    void save (User user);
}
