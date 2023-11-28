package com.dodson.personalfinance.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.model.UserModelBuilder;

@DataJpaTest
@ActiveProfiles("h2")
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
		String userId = UUID.randomUUID().toString();
		UserModel user = new UserModelBuilder().withUserId(userId).build();
		UserModel noiseUser = new UserModelBuilder().build();
		userRepository.saveAllAndFlush(List.of(user, noiseUser));

		UserModel foundUser = userRepository.findUserByUserId(userId);

		assertNotNull(foundUser);
		assertThat(user, is(foundUser));
	}

	@Test
	public void test_retrievePasswordHashByEmail() {
		UserModel user = new UserModelBuilder().build();
		UserModel noise = new UserModelBuilder().build();
		userRepository.saveAllAndFlush(List.of(user, noise));

		UserModel foundUser = userRepository.retrievePasswordHashByEmail(user.getEmail());

		assertThat(user, is(foundUser));
	}
}
