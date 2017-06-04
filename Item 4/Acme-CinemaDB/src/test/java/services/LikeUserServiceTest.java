
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.LikeUser;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LikeUserServiceTest extends AbstractTest {

	@Autowired
	private LikeUserService	likeUserService;


	@Test
	public void saveLikeUserDriver() {
		final Object testingData[][] = {
			{
				// An administrator is not allowed to save a likeUser -> Exception
				"admin", 295, IllegalArgumentException.class
			}, {
				// An unauthenticated user is not allowed to save a likeUser -> Exception
				null, 295, IllegalArgumentException.class
			}, {
				// A producer user is not allowed to save a likeUser -> Exception
				"producer1", 295, IllegalArgumentException.class
			}, {
				// OK
				"user1", 295, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.saveLikeUserTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void removeLikeUserDriver() {
		final Object testingData[][] = {
			{
				// An administrator is not allowed to save a likeUser -> Exception
				"admin", 335, IllegalArgumentException.class
			}, {
				// An unauthenticated user is not allowed to save a likeUser -> Exception
				null, 335, IllegalArgumentException.class
			}, {
				// A producer user is not allowed to save a likeUser -> Exception
				"producer1", 335, IllegalArgumentException.class
			}, {
				// A user, but the likeUser not belong to him -> Exception
				"user1", 335, IllegalArgumentException.class
			}, {
				// OK
				"user3", 335, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.removeLikeUserTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void saveLikeUserTemplate(final String username, final int assessableEntityId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final LikeUser likeUser = this.likeUserService.create(assessableEntityId);
			likeUser.setComment("holis");

			final LikeUser saved = this.likeUserService.save(likeUser);

			final Collection<LikeUser> allLikeUserByPrincipal = this.likeUserService.findAllByPrincipal();

			Assert.isTrue(allLikeUserByPrincipal.contains(saved));

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	protected void removeLikeUserTemplate(final String username, final int likeUserId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final LikeUser likeUser = this.likeUserService.findOne(likeUserId);

			this.likeUserService.delete(likeUser);

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
