
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LikeUserRepository;
import domain.LikeUser;
import domain.User;

@Service
@Transactional
public class LikeUserService {

	// Managed repository ---------------------------

	@Autowired
	private UserService				userService;

	@Autowired
	private AssessableEntityService	assessableEntityService;

	@Autowired
	private LikeUserRepository		likeUserRepository;

	@Autowired
	Validator						validator;


	// Simple CRUD methods --------------------------

	public LikeUser create(final int assessableEntityId) {
		final LikeUser likeUser = new LikeUser();

		likeUser.setComment("");

		likeUser.setUser(this.userService.findByPrincipal());
		likeUser.setAssessableEntity(this.assessableEntityService.findOne(assessableEntityId));

		return likeUser;
	}

	public LikeUser save(final LikeUser likeUser) {
		//todo: faltan restricciones

		final LikeUser savedLikeUser = this.likeUserRepository.save(likeUser);

		return savedLikeUser;
	}

	public Collection<LikeUser> findAll() {
		return this.likeUserRepository.findAll();
	}

	public LikeUser findOne(final int id) {
		return this.likeUserRepository.findOne(id);
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

	public LikeUser reconstruct(final LikeUser likeUser, final BindingResult bindingResult) {
		LikeUser reconstructed;

		if (likeUser.getId() == 0) {
			reconstructed = likeUser;
			reconstructed.setUser(this.userService.findByPrincipal());
		} else {
			reconstructed = this.findOne(likeUser.getId());
			reconstructed.setComment(likeUser.getComment());

			this.validator.validate(reconstructed, bindingResult);
		}

		if (reconstructed.getComment().isEmpty())
			reconstructed.setComment(null);

		return reconstructed;
	}
}
