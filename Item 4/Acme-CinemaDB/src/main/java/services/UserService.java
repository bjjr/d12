
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import domain.User;

@Service
@Transactional
public class UserService {

	// Managed repository -----------------------------------

	@Autowired
	private UserRepository	userRepository;


	// Constructors -----------------------------------------

	public UserService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Collection<User> findAll() {
		Collection<User> result;

		result = this.userRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public User findOne(final int userId) {
		Assert.notNull(userId);
		Assert.isTrue(userId != 0);

		User result;

		result = this.userRepository.findOne(userId);
		Assert.notNull(result);

		return result;
	}

}
