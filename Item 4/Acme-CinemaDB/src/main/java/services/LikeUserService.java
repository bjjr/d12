
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LikeUserRepository;
import domain.LikeUser;

@Service
@Transactional
public class LikeUserService {

	// Managed repository ---------------------------

	@Autowired
	private LikeUserRepository	likeUserRepository;


	// Other business methods -----------------------

	public Collection<LikeUser> findContentLikes(final int userId) {
		Assert.isTrue(userId != 0);

		Collection<LikeUser> res;

		res = this.likeUserRepository.findContentLikes(userId);

		return res;
	}

	public Collection<LikeUser> findCinematicEntityLikes(final int userId) {
		Assert.isTrue(userId != 0);

		Collection<LikeUser> res;

		res = this.likeUserRepository.findCinematicEntityLikes(userId);

		return res;
	}
}
