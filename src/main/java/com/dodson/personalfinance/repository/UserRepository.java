package com.dodson.personalfinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dodson.personalfinance.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {

	@Query("SELECT u FROM UserModel u WHERE userId = :userId")
	UserModel findUserByUserId(@Param("userId") String userId);

	@Query("SELECT u FROM UserModel u WHERE email = :email")
	UserModel retrievePasswordHashByEmail(@Param("email") String email);
}
