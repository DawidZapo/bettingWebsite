package com.bettingwebsite.dao;

import com.bettingwebsite.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result,Long> {
}
