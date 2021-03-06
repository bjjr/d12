
package services;

import java.util.Collection;
import java.util.List;

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
		final Review res = this.findOne(review.getId());
		Assert.notNull(res);

		this.reviewRepository.delete(res);
	}

	public Review reconstruct(final Review review, final int contentId, final boolean isDraft, final BindingResult bindingResult) {
		final Review result = review;

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

	// Other business methods -----------------------------------

	public Long findMinReviewCritic() {
		Long result;
		List<Integer> allCritics, allCriticsReviews;
		List<Long> reviewMin;

		allCritics = this.criticService.findAllCriticId();
		allCriticsReviews = this.criticService.findAllCriticWithReviewsId();

		if (!allCriticsReviews.containsAll(allCritics))
			return 0L;

		result = 0L;
		reviewMin = this.reviewRepository.findMinReviewCritic();

		if (!reviewMin.isEmpty())
			result = reviewMin.get(0);

		return result;

	}

	public Double findAvgReviewCritic() {
		Double result;

		result = this.reviewRepository.findAvgReviewCritic();

		return result;
	}

	public Long findMaxReviewCritic() {
		Long result;
		List<Long> reviewMax;

		result = 0L;
		reviewMax = this.reviewRepository.findMaxReviewCritic();

		if (!reviewMax.isEmpty())
			result = reviewMax.get(0);

		return result;

	}

	public Double findAvgRatingMovies() {
		Double result;

		result = this.reviewRepository.findAvgRatingMovies();

		return result;
	}

	public Double findAvgRatingTvShows() {
		Double result;

		result = this.reviewRepository.findAvgRatingTvShows();

		return result;
	}

}
