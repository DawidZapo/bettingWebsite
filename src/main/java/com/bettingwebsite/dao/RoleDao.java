package com.bettingwebsite.dao;

import com.bettingwebsite.entity.Role;

public interface RoleDao {

    public Role findRoleByName(String theRoleName);

}
