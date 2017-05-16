
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProducerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Producer;
import forms.ProducerForm;

@Service
@Transactional
public class ProducerService {

	// Managed repository -----------------------------------

	@Autowired
	private ProducerRepository	producerRepository;

	// Supporting services ----------------------------------

	@Autowired
	private ActorService		actorService;

	// Validator --------------------------------------------

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------

	public ProducerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Producer create() {
		Assert.isTrue(this.actorService.checkAuthority("ADMIN"));

		Producer result;
		Authority authority;
		UserAccount userAccount;

		userAccount = new UserAccount();
		userAccount.setUsername("");
		userAccount.setPassword("");

		result = new Producer();

		result.setUserAccount(userAccount);

		authority = new Authority();
		authority.setAuthority("PRODUCER");
		result.getUserAccount().addAuthority(authority);

		return result;
	}

	public Producer findOne(final int producerId) {
		Producer result;

		result = this.producerRepository.findOne(producerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Producer> findAll() {
		Collection<Producer> result;

		result = this.producerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Producer save(Producer producer) {
		Assert.notNull(producer);

		Producer result;

		if (producer.getId() == 0) {
			Assert.isTrue(producer.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("PRODUCER"));
			producer.getUserAccount().setPassword(this.hashCodePassword(producer.getUserAccount().getPassword()));
		} else
			producer = (Producer) this.actorService.findByPrincipal();

		result = this.producerRepository.save(producer);

		return result;
	}

	public void flush() {
		this.producerRepository.flush();
	}

	// Other business methods -------------------------------

	public Producer reconstruct(final Producer producer, final BindingResult binding) {
		Producer result;

		if (producer.getId() == 0)
			result = producer;
		else {
			final Producer aux = this.findByPrincipal();
			result = producer;

			result.setUserAccount(aux.getUserAccount());
			result.setCreditCard(aux.getCreditCard());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public Producer reconstruct(final ProducerForm producerForm, final BindingResult binding) {
		Producer result;

		if (producerForm.getProducer().getId() == 0)
			result = producerForm.getProducer();
		else {
			final Producer aux = this.findByPrincipal();
			result = producerForm.getProducer();

			result.setUserAccount(aux.getUserAccount());
		}

		this.validator.validate(result, binding);

		return result;
	}

	public String hashCodePassword(final String password) {
		String result;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		result = encoder.encodePassword(password, null);

		return result;
	}

	public Producer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Producer result;

		result = this.producerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Producer findByPrincipal() {
		Producer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}
}
