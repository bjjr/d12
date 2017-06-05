
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import domain.User;
import forms.ActorForm;

@Service
@Transactional
public class UserService {

	// Managed repository -----------------------------------

	@Autowired
	private UserRepository	userRepository;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------

	public UserService() {
		super();
	}


	// Validator --------------------------------------------

	@Autowired
	private Validator	validator;


	// Simple CRUD methods ----------------------------------

	public User create() {
		User res;
		UserAccount userAccount;

		userAccount = new UserAccount();
		res = new User();

		res.setUserAccount(userAccount);

		return res;
	}

	public User save(final User user) {
		Assert.notNull(user);
		User res;

		if (user.getId() == 0) {
			String password, hashedPassword;
			password = user.getUserAccount().getPassword();
			hashedPassword = this.actorService.hashCodePassword(password);

			user.getUserAccount().setPassword(hashedPassword);
		}

		res = this.userRepository.save(user);
		return res;
	}

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

	public User findByPrincipal() {
		User res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		res = this.findByUserAccount(userAccount);

		return res;
	}

	public User findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		User res;

		res = this.userRepository.findByUserAccountId(userAccount.getId());

		Assert.notNull(res);

		return res;
	}

	// Other business methods -----------------------

	public User reconstruct(final ActorForm actorForm, final BindingResult binding) {
		User res;

		res = actorForm.getUser();

		this.actorService.checkPasswords(actorForm.getUserAccount().getPassword(), actorForm.getConfirmPassword(), binding);

		this.validator.validate(res, binding);

		return res;
	}

	public List<Integer> findAllUserId() {
		List<Integer> result;

		result = this.userRepository.findAllUserId();

		return result;
	}

	public List<Integer> findAllUsersWithOrdersId() {
		List<Integer> result;

		result = this.userRepository.findAllUsersWithOrdersId();

		return result;
	}

}
