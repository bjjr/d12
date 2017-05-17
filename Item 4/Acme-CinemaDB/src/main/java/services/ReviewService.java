
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReviewRepository;
import domain.Content;
import domain.Critic;
import domain.Review;

@Service
@Transactional
public class ReviewService {

	// Managed repository -----------------------------------

	@Autowired
	private ReviewRepository	reviewRepository;

	@Autowired
	private CriticService		criticService;

	@Autowired
	private ContentService		contentService;

	@Autowired
	private Validator			validator;


	public Review create(final int contentId) {
		final Review res = new Review();
		final Critic critic = this.criticService.findByPrincipal();
		final Content content = this.contentService.findOne(contentId);
		Assert.notNull(critic);
		Assert.notNull(content);

		res.setBody("");
		res.setTitle("");
		res.setDraft(true);
		res.setRating(0);

		return res;
	}

	public Review save(final Review review) {
		final Critic critic = this.criticService.findByPrincipal();
		Assert.notNull(critic);
		Assert.notNull(review);
		Assert.isTrue(review.getCritic().getId() == critic.getId());

		return this.reviewRepository.save(review);
	}

	public void delete(final Review review) {
		final Critic critic = this.criticService.findByPrincipal();
		Assert.notNull(critic);
		Assert.isTrue(review.getCritic().getId() == critic.getId());

		this.reviewRepository.delete(review.getId());
	}

	public Review reconstruct(final Review review, final int contentId, final boolean isDraft, final BindingResult bindingResult) {
		final Review result = review;
		;

		if (review.getId() != 0) {
			final Review aux = this.findOne(review.getId());
			result.setContent(aux.getContent());
			result.setCritic(aux.getCritic());
		} else {
			final Critic critic = this.criticService.findByPrincipal();
			final Content content = this.contentService.findOne(contentId);
			Assert.notNull(critic);
			Assert.notNull(content);
			result.setContent(content);
			result.setCritic(critic);
		}

		result.setDraft(isDraft);

		this.validator.validate(result, bindingResult);
		return result;
	}

	public void flush() {
		this.reviewRepository.flush();
	}

	public Collection<Review> findReviewsByContentId(final int contentId) {
		Assert.notNull(contentId);
		return this.reviewRepository.findReviewsByContentId(contentId);
	}

	public Collection<Review> findReviewsByCriticId(final int criticId) {
		Assert.notNull(criticId);
		return this.reviewRepository.findReviewsByCriticId(criticId);
	}

	public Review findOne(final int reviewId) {
		Assert.isTrue(reviewId != 0);
		final Review res = this.reviewRepository.findOne(reviewId);
		final Critic critic = this.criticService.findByPrincipal();
		Assert.notNull(critic);
		Assert.notNull(res);
		Assert.isTrue(res.getCritic().getId() == critic.getId());

		return res;
	}

}
