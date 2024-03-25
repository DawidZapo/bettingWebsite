package com.bettingwebsite.service;

import com.bettingwebsite.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findByUserName(String userName);
    void save (User user);
    List<User> findAllExceptAdminAndDisabled();
    User findById(Long id);

}
