
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import utilities.AbstractTest;
import domain.Critic;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CriticServiceTest extends AbstractTest {

	@Autowired
	private CriticService	criticService;


	// Tests ------------------------------------------------------------------

	/*
	 * Use case: An actor who is authenticated as an administrator must be able to:
	 * Register critics
	 * Expected errors:
	 * - An actor logged as user tries to register a critic --> IllegalArgumentException
	 * - An actor logged as critic tries to register a critic --> IllegalArgumentException
	 */

	@Test
	public void createCriticDriver() {
		final Object testingData[][] = {
			{    // An actor logged as user cannot register critics
				"user1", IllegalArgumentException.class
			}, {
				// An actor logged as producer cannot register critics
				"producer1", IllegalArgumentException.class
			}, { // An actor logged as critic cannot register critics
				"critic1", IllegalArgumentException.class
			}, { // OK
				"admin", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createCriticTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	@Test
	public void editCriticDriver() {
		final Object testingData[][] = {
			{    // An actor logged as critic edits his profile with an invalid email -> Exception
				"critic1", 252, "user.com", ConstraintViolationException.class
			}, {
				// OK
				"critic1", 252, "critic@email.com", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editCriticTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	// Templates --------------------------------------------------------------

	protected void createCriticTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			Critic critic, saved;
			final Authority authority;

			this.authenticate(username);

			authority = new Authority();
			authority.setAuthority("CRITIC");

			critic = this.criticService.create();
			critic.getUserAccount().setUsername("usernameTest");
			critic.getUserAccount().setPassword("passwordTest");
			critic.setName("NameTest");
			critic.setSurname("SurnameTest");
			critic.setEmail("emailTest@email.com");
			critic.setPhone("+34123456789");
			critic.setCountry("CountryTest");
			critic.setMedia("MediaTest");
			saved = this.criticService.save(critic);
			this.criticService.flush();

			this.unauthenticate();

			Assert.isTrue(saved.getUserAccount().getAuthorities().contains(authority));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editCriticTemplate(final String username, final int criticId, final String email, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			Critic critic, saved;
			final Authority authority;

			this.authenticate(username);

			authority = new Authority();
			authority.setAuthority("CRITIC");

			critic = this.criticService.findOne(criticId);
			critic.setEmail(email);
			saved = this.criticService.save(critic);
			this.criticService.flush();

			this.unauthenticate();

			Assert.isTrue(saved.getUserAccount().getAuthorities().contains(authority));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
