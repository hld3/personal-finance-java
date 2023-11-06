package com.dodson.personalfinance.repository;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import com.dodson.personalfinance.model.UserModel;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryUnitTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void test_saveUser() {
		UserModel user = new UserModel();
		user.setUserId(UUID.randomUUID());

		UserModel savedUser = userRepository.save(user);

		assertThat(user, is(savedUser));
	}
}
