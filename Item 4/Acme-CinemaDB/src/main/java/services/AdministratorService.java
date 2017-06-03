
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;


	// Constructors -----------------------------------------

	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Administrator save(final Administrator admin) {
		Assert.notNull(admin);

		Administrator result;

		result = this.administratorRepository.save(admin);

		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Administrator findOne(final int adminId) {
		Assert.notNull(adminId);
		Assert.isTrue(adminId != 0);

		Administrator result;

		result = this.administratorRepository.findOne(adminId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Administrator result;

		result = this.administratorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.administratorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Administrator findAdministrator() {
		Administrator result;

		result = this.administratorRepository.findAdministrator();
		Assert.notNull(result);

		return result;
	}

}
