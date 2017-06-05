
package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import services.ActorService;
import forms.UserAccountForm;

@Service
@Transactional
public class UserAccountService {

	// Managed repository --------------------
	@Autowired
	private UserAccountRepository	userAccountRepository;

	// Supporting services -------------------

	@Autowired
	private ActorService			actorService;

	// Validator	--------------------------

	@Autowired
	private Validator				validator;


	// Constructors --------------------------

	public UserAccountService() {
		super();
	}

	// Simple CRUD methods -------------------

	public UserAccount create(final String authority) {
		Assert.notNull(authority);
		UserAccount res;
		Authority a;
		final Collection<Authority> authorities = new ArrayList<Authority>();

		a = new Authority();
		a.setAuthority(authority);
		authorities.add(a);

		res = new UserAccount();
		res.setAuthorities(authorities);

		return res;
	}

	public UserAccount save(final UserAccount ua) {
		Assert.notNull(ua);

		UserAccount res;
		res = this.userAccountRepository.save(ua);

		return res;
	}

	public UserAccount findByUsername(final String username) {
		Assert.notNull(username);
		UserAccount res;

		res = this.userAccountRepository.findByUsername(username);
		Assert.notNull(res);

		return res;
	}

	// Other business methods ----------------

	public UserAccount reconstruct(final UserAccountForm userAccountForm, final BindingResult binding) {
		Assert.isTrue(this.actorService.checkAuthority("USER") || this.actorService.checkAuthority("PRODUCER") || this.actorService.checkAuthority("CRITIC"));

		UserAccount principalua, res;
		String currentPasswd, hashedPasswd;

		principalua = this.actorService.findByPrincipal().getUserAccount();
		res = userAccountForm.getUserAccount();

		res.setId(principalua.getId());
		res.setVersion(principalua.getVersion());
		res.setUsername(principalua.getUsername());
		res.setAuthorities(new HashSet<>(principalua.getAuthorities()));

		currentPasswd = principalua.getPassword();
		hashedPasswd = this.actorService.hashCodePassword(userAccountForm.getCurrentPassword());

		if (!userAccountForm.getPassword().equals(userAccountForm.getPasswdConf()))
			binding.rejectValue("password", "userAccount.password.invalid");
		else if (!hashedPasswd.equals(currentPasswd))
			binding.rejectValue("currentPassword", "userAccount.password.invalid.current");

		this.validator.validate(res, binding);

		return res;
	}
}
