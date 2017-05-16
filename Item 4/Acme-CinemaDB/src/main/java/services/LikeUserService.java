
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LikeUserRepository;
import domain.LikeUser;
import domain.User;

@Service
@Transactional
public class LikeUserService {

	// Managed repository ---------------------------

	@Autowired
	private UserService			userService;

	@Autowired
	private LikeUserRepository	likeUserRepository;


	// Simple CRUD methods --------------------------

	public Collection<LikeUser> findAll() {
		return this.likeUserRepository.findAll();
	}

	// Other business methods -----------------------

	public Collection<LikeUser> findAllByPrincipal() {
		final User currentUser = this.userService.findByPrincipal();
		final Collection<LikeUser> likeUserByPrincipal = this.likeUserRepository.findAllByUserAccountId(currentUser.getId());

		return likeUserByPrincipal;
	}

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

	public Collection<LikeUser> findCommentsByAssessableEntity(final int assessableEntityId) {
		final Collection<LikeUser> allCommentsInTheEntity = this.likeUserRepository.findCommentsByAssessableEntity(assessableEntityId);

		return allCommentsInTheEntity;
	}
}
