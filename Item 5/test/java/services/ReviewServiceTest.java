
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Content;
import domain.Critic;
import domain.Review;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReviewServiceTest extends AbstractTest {

	@Autowired
	private ReviewService	reviewService;

	@Autowired
	private CriticService	criticService;

	@Autowired
	private ContentService	contentService;


	@Test
	public void saveReviewDriver() {
		final Object testingData[][] = {
			{
				// OK
				"critic1", 300, "Great movie", "I liked it!", 4, true, null
			}, {
				// An administrator is not allowed to save a review -> Exception
				"admin", 300, "Great movie", "I liked it!", 4, true, IllegalArgumentException.class
			}, {
				// An unauthenticated user is not allowed to save a review -> Exception
				null, 300, "Great movie", "I liked it!", 4, true, IllegalArgumentException.class
			}, {
				// Rating must be higher than 0 and less than 5 -> Exception
				"critic1", 300, "Great movie", "I liked it!", 6, true, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.saveReviewTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (int) testingData[i][4], (boolean) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	@Test
	public void editReviewDriver() {
		final Object testingData[][] = {
			{
				// A non-draft review is not allowed to be edited -> Exception
				"critic1", 352, IllegalArgumentException.class
			}, {
				// OK
				"critic1", 350, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editReviewTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void saveReviewTemplate(final String username, final int contentId, final String title, final String body, final int rating, final boolean draft, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.unauthenticate();
			this.authenticate(username);

			final Review review = this.reviewService.create(contentId);
			final Critic critic = this.criticService.findByPrincipal();
			final Content content = this.contentService.findOne(contentId);

			review.setTitle(title);
			review.setBody(body);
			review.setRating(rating);
			review.setDraft(draft);
			review.setCritic(critic);
			review.setContent(content);

			this.reviewService.save(review);
			this.reviewService.flush();

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editReviewTemplate(final String username, final int reviewId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.unauthenticate();
			this.authenticate(username);

			final Review review = this.reviewService.findOne(reviewId);

			this.reviewService.save(review);
			this.reviewService.flush();

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
