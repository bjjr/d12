
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SocialIdentityRepository;
import domain.SocialIdentity;
import domain.User;

@Service
@Transactional
public class SocialIdentityService {

	// Managed repository ---------------------------

	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;

	@Autowired
	private UserService					userService;

	@Autowired
	private Validator					validator;


	// Other business methods -----------------------

	public Collection<SocialIdentity> findUserSocialIdentities(final int userId) {
		Assert.isTrue(userId != 0);

		Collection<SocialIdentity> res;

		res = this.socialIdentityRepository.findUserSocialIdentities(userId);

		return res;
	}

	// Simple CRUD methods ----------------------------------

	public SocialIdentity create() {

		SocialIdentity result;

		User user;

		result = new SocialIdentity();

		user = this.userService.findByPrincipal();

		result.setUser(user);

		return result;

	}

	public SocialIdentity findOne(final int socialIdentityId) {

		SocialIdentity result;

		result = this.socialIdentityRepository.findOne(socialIdentityId);

		Assert.notNull(result);

		return result;

	}

	public Collection<SocialIdentity> findAll() {

		Collection<SocialIdentity> result;

		result = this.socialIdentityRepository.findAll();

		Assert.notNull(result);

		return result;

	}

	public SocialIdentity save(final SocialIdentity socialIdentity) {

		Assert.notNull(socialIdentity);

		SocialIdentity result;

		User user;

		if (socialIdentity.getId() == 0) {

			user = this.userService.findByPrincipal();

			this.userService.save(user);

		}

		result = this.socialIdentityRepository.save(socialIdentity);

		return result;

	}

	public void flush() {

		this.socialIdentityRepository.flush();

	}

	public void delete(final SocialIdentity socialIdentity) {

		Assert.notNull(socialIdentity);

		Assert.isTrue(socialIdentity.getId() != 0);

		Assert.isTrue(this.socialIdentityRepository.exists(socialIdentity.getId()));

		User user;

		user = this.userService.findByPrincipal();

		this.userService.save(user);

		this.socialIdentityRepository.delete(socialIdentity);

	}

	// Other business methods -------------------------------

	public SocialIdentity reconstruct(final SocialIdentity socialIdentity, final BindingResult binding) {

		SocialIdentity result;

		User user;

		if (socialIdentity.getId() == 0) {

			user = this.userService.findByPrincipal();

			result = socialIdentity;

			result.setUser(user);

		} else {

			SocialIdentity aux;

			aux = this.findOne(socialIdentity.getId());

			result = socialIdentity;

			result.setUser(aux.getUser());

		}

		this.validator.validate(result, binding);

		return result;

	}

}
