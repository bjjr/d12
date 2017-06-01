
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CriticRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Critic;
import forms.CriticForm;

@Service
@Transactional
public class CriticService {

	// Managed repository -----------------------------------

	@Autowired
	private CriticRepository	criticRepository;

	// Supporting services ----------------------------------

	@Autowired
	private ActorService		actorService;

	// Validator --------------------------------------------

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------

	public CriticService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Critic create() {
		Assert.isTrue(this.actorService.checkAuthority("ADMIN"), "Only an admin user can create a critic");

		Critic res;
		Authority authority;
		UserAccount userAccount;

		userAccount = new UserAccount();
		userAccount.setUsername("");
		userAccount.setPassword("");

		res = new Critic();
		res.setUserAccount(userAccount);

		authority = new Authority();
		authority.setAuthority("CRITIC");
		res.getUserAccount().addAuthority(authority);

		return res;
	}

	public Critic save(Critic critic) {
		Assert.notNull(critic);
		final Critic res;

		if (critic.getId() == 0) {
			Assert.isTrue(critic.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("CRITIC"));
			critic.getUserAccount().setPassword(this.hashCodePassword(critic.getUserAccount().getPassword()));
		} else
			critic = (Critic) this.actorService.findByPrincipal();

		res = this.criticRepository.save(critic);
		return res;
	}

	public Critic reconstruct(final CriticForm criticForm, final BindingResult binding) {
		final Critic res = criticForm.getCritic();
		this.checkPasswords(criticForm.getUserAccount().getPassword(), criticForm.getConfirmPassword(), binding);

		this.validator.validate(res, binding);

		return res;
	}

	public Critic reconstruct(final Critic critic, final BindingResult binding) {
		Assert.isTrue(this.actorService.checkAuthority("CRITIC"));
		final Critic res = critic;
		final Critic principal = this.findByPrincipal();

		res.setId(principal.getId());
		res.setVersion(principal.getVersion());
		res.setCreditCard(principal.getCreditCard());
		res.setUserAccount(principal.getUserAccount());

		this.validator.validate(res, binding);

		return res;
	}
	public Critic findByPrincipal() {
		Critic res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);

		return res;
	}
	public Critic findByUserAccount(final UserAccount userAccount) {
		final Critic res = this.criticRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	public Collection<Critic> findAll() {
		Collection<Critic> result;

		result = this.criticRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Critic findOne(final int criticId) {
		Assert.notNull(criticId);
		Assert.isTrue(criticId != 0);

		Critic result;

		result = this.criticRepository.findOne(criticId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.criticRepository.flush();
	}

	public String hashCodePassword(final String password) {
		String result;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		result = encoder.encodePassword(password, null);

		return result;
	}

	private void checkPasswords(final String passwd1, final String passwd2, final BindingResult binding) {
		if (!passwd1.equals(passwd2) || (passwd1 == null || passwd2 == null))
			binding.rejectValue("userAccount.password", "critic.password.invalid");
	}

	// Other business methods ----------------------------------

	public List<Integer> findAllCriticId() {
		List<Integer> result;

		result = this.criticRepository.findAllCriticId();

		return result;
	}

	public List<Integer> findAllCriticWithReviewsId() {
		List<Integer> result;

		result = this.criticRepository.findAllCriticWithReviewsId();

		return result;
	}

}
