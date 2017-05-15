
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReviewRepository;
import domain.Review;

@Service
@Transactional
public class ReviewService {

	// Managed repository -----------------------------------

	@Autowired
	private ReviewRepository	reviewRepository;


	public Collection<Review> findReviewsByContentId(final int contentId) {
		Assert.notNull(contentId);
		return this.reviewRepository.findReviewsByContentId(contentId);
	}

}
