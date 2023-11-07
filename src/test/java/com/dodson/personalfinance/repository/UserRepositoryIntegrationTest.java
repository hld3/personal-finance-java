package com.dodson.personalfinance.repository;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.model.UserModelBuilder;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void test_saveUser() {
		UserModel user = new UserModelBuilder().build();

		UserModel savedUser = userRepository.save(user);

		assertThat(user, is(savedUser));
	}

	@Test
	public void test_retrieveUserByUserId() {
		UUID userId = UUID.randomUUID();
		UserModel user = new UserModelBuilder().withUserId(userId).build();
		userRepository.save(user);

		UserModel foundUser = userRepository.findUserByUserId(userId);

		assertNotNull(foundUser);
		assertThat(user, is(foundUser));
	}
}
