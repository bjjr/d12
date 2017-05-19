
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
		final User currentUser = this.userService.findByPrincipal();

		LikeUser likeUser = this.findByUserAndAssessableEntity(currentUser.getId(), assessableEntityId);    // if already a like user exists get that like user 

		if (likeUser == null) {
			likeUser = new LikeUser();

			likeUser.setComment(null);

			likeUser.setUser(currentUser);
			likeUser.setAssessableEntity(this.assessableEntityService.findOne(assessableEntityId));
		}

		return likeUser;
	}

	public LikeUser save(final LikeUser likeUser) {
		final User currentUser = this.userService.findByPrincipal();

		Assert.isTrue(!this.alreadyExistsTheLikeUser(likeUser), "LikeUserService.save: The likeUser already exists");
		Assert.isTrue(likeUser.getUser().equals(currentUser), "LikeUserService.save: You can't save a like user that doesnt belong to you");

		final LikeUser savedLikeUser = this.likeUserRepository.save(likeUser);
		return savedLikeUser;
	}

	public void delete(final LikeUser likeUser) {
		final LikeUser likeUserRetrieved = this.findOne(likeUser.getId());

		final User currentUser = this.userService.findByPrincipal();

		Assert.isTrue(likeUserRetrieved.getUser().equals(currentUser), "LikeUserService.delete: You can't delete a like user that doesnt belong to you");

		this.likeUserRepository.delete(likeUserRetrieved);
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
		final Collection<LikeUser> likeUserByPrincipal = this.likeUserRepository.findAllByPrincipal(currentUser.getId());

		return likeUserByPrincipal;
	}

	public void like(final int assessableEntityId) {
		final LikeUser likeUser = this.create(assessableEntityId);

		this.save(likeUser);
	}

	public void unlike(final int assessableEntityId) {
		final User currentUser = this.userService.findByPrincipal();

		final LikeUser retrievedLikeUser = this.findByUserAndAssessableEntity(currentUser.getId(), assessableEntityId);

		this.delete(retrievedLikeUser);
	}

	public LikeUser findByUserAndAssessableEntity(final int userId, final int assessableEntityId) {
		return this.likeUserRepository.findByUserAndAssessableEntity(userId, assessableEntityId);
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

	private Boolean alreadyExistsTheLikeUser(final LikeUser likeUser) {
		final Collection<LikeUser> allLikeUserOfCurrentUser = this.findAllByPrincipal();
		Boolean res = false;

		for (final LikeUser lu : allLikeUserOfCurrentUser)
			if (lu.getAssessableEntity().getId() == likeUser.getAssessableEntity().getId() && lu.getId() != likeUser.getId()) {
				res = true;
				break;
			}

		return res;
	}
}
