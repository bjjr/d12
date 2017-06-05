
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import utilities.AbstractTest;
import domain.Producer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProducerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ProducerService	producerService;


	// Supporting services ----------------------------------------------------

	// Tests ------------------------------------------------------------------

	/*
	 * Use case: An actor who is authenticated as an administrator must be able to:
	 * Register producers
	 * Expected errors:
	 * - An actor logged as user tries to register a producer --> IllegalArgumentException
	 * - An actor logged as producer tries to register a producer --> IllegalArgumentException
	 */

	@Test
	public void createProducerDriver() {
		final Object testingData[][] = {
			{    //An actor logged as user cannot register producers
				"user1", IllegalArgumentException.class
			}, { //An actor logged as producer cannot register producers
				"producer1", IllegalArgumentException.class
			}, { // Successful test
				"admin", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createProducerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	// Templates --------------------------------------------------------------

	protected void createProducerTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			Producer created, saved;
			final Authority authority;

			this.authenticate(username);

			authority = new Authority();
			authority.setAuthority("PRODUCER");

			created = this.producerService.create();
			created.getUserAccount().setUsername("usernameTest");
			created.getUserAccount().setPassword("passwordTest");
			created.setName("NameTest");
			created.setSurname("SurnameTest");
			created.setEmail("emailTest@email.com");
			created.setPhone("+34123456789");
			created.setCountry("CountryTest");
			created.setCompany("CompanyTest");
			saved = this.producerService.save(created);
			this.producerService.flush();

			this.unauthenticate();

			Assert.isTrue(saved.getUserAccount().getAuthorities().contains(authority));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
