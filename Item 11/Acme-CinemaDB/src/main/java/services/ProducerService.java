
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Producer save(final Producer producer) {
		Assert.notNull(producer);

		Producer result;

		if (producer.getId() == 0) {
			Assert.isTrue(producer.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("PRODUCER"));
			producer.getUserAccount().setPassword(this.hashCodePassword(producer.getUserAccount().getPassword()));
		}

		result = this.producerRepository.save(producer);

		return result;
	}

	public void flush() {
		this.producerRepository.flush();
	}

	// Other business methods -------------------------------

	// Reconstruct to create

	public Producer reconstruct(final ProducerForm producerForm, final BindingResult binding) {
		Producer result;
		Authority auth;

		auth = new Authority();
		auth.setAuthority("PRODUCER");

		result = producerForm.getProducer();

		result.getUserAccount().addAuthority(auth);

		this.validator.validate(result, binding);

		return result;
	}

	// Reconstruct to edit

	public Producer reconstruct(final Producer producer, final BindingResult binding) {
		final Producer result;
		Producer aux;

		result = producer;
		aux = this.findByPrincipal();

		result.setName(aux.getName());
		result.setSurname(aux.getSurname());
		result.setCountry(aux.getCountry());
		result.setCompany(aux.getCompany());
		result.setUserAccount(aux.getUserAccount());
		result.setCreditCard(aux.getCreditCard());

		this.validator.validate(result, binding);

		return result;
	}

	public String hashCodePassword(final String password) {
		String result;

		result = this.actorService.hashCodePassword(password);

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

	public List<Integer> findAllProducerId() {
		List<Integer> result;

		result = this.producerRepository.findAllProducerId();

		return result;
	}

	public List<Integer> findAllProducerWithMoviesId() {
		List<Integer> result;

		result = this.producerRepository.findAllProducerWithMoviesId();

		return result;
	}
}
