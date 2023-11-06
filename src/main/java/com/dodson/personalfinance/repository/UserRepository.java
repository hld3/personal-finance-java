package com.dodson.personalfinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dodson.personalfinance.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
