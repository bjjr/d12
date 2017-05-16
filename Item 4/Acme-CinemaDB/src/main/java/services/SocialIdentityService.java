
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	// Managed repository ---------------------------

	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;


	// Other business methods -----------------------

	public Collection<SocialIdentity> findUserSocialIdentities(final int userId) {
		Assert.isTrue(userId != 0);

		Collection<SocialIdentity> res;

		res = this.socialIdentityRepository.findUserSocialIdentities(userId);

		return res;
	}
}
