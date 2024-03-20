package com.bettingwebsite.dao;

import com.bettingwebsite.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private EntityManager entityManager;

    public UserDaoImpl(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public User findByUserName(String theUserName) {
        // retrieve/read from database using username
        TypedQuery<User> theQuery = entityManager.createQuery("from User where userName=:uName and enabled=true", User.class);
        theQuery.setParameter("uName", theUserName);

        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }

    @Override
    public User findById(Long userNameId) {
        // retrieve/read from database using username
        TypedQuery<User> theQuery = entityManager.createQuery("from User where id=:userId and enabled=true", User.class);
        theQuery.setParameter("userId", userNameId);

        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }

    @Override
    public User save(User user) {
        return entityManager.merge(user);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        return query.getResultList();
    }

}

