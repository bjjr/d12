
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import utilities.AbstractTest;
import domain.SocialIdentity;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SocialIdentityServiceTest extends AbstractTest {

	// System under test ----------------------------

	@Autowired
	private SocialIdentityService	socialIdentityService;


	// Tests ----------------------------------------

	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * Registrar identidades sociales y editar, eliminar y listar las que ha registrado..
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the socialIdentitys
	 * , which includes listing, registering and modifying.
	 * In order to register a new socialIdentity, he must have registered a season.
	 * 
	 * Expected errors:
	 * - A producer tries to register a new socialIdentity but he/she does not write this season -> IllegalArgumentException
	 * - A user tries to register a new socialIdentity -> IllegalArgumentException
	 */

	@Test
	public void createSocialIdentityDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"user3", "https://twitter.com/", null
			}, { // Content without year
				"user1", "", IllegalArgumentException.class
			}, { // A user tries to create an content 
				"producer3", "https://twitter.com/", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createSocialIdentityTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * Registrar identidades sociales y editar, eliminar y listar las que ha registrado..
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the socialIdentities
	 * , which includes listing, registering and modifying.
	 * In order to edit socialIdentity, he must have this social identity.
	 * 
	 * Expected errors:
	 * - A user tries to edit a socialIdentity but he/she does not write this socialIdentity -> IllegalArgumentException
	 * - A producer tries to edit a socialIdentity -> IllegalArgumentException
	 */

	@Test
	public void editSocialIdentityDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"user3", 358, null
			}, { // A producer tries to edit a social Identity of another producer
				"user1", 358, IllegalArgumentException.class
			}, { // A user tries to edit a social Identity
				"producer3", 358, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editionSocialIdentityTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void deleteSocialIdentityDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"user3", 358, null
			}, { // A producer tries to delete a social Identity of another producer
				"user1", 358, IllegalArgumentException.class
			}, { // A user tries to edit a social Identity
				"producer3", 358, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteSocialIdentityTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ------------------------------------

	protected void createSocialIdentityTemplate(final String username, final String path, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final SocialIdentity socialIdentity = this.socialIdentityService.create();
			SocialIdentity reconstructed, saved;
			final DataBinder dataBinder;
			BindingResult binding;

			// Simulating form

			socialIdentity.setUsername("Testing");
			socialIdentity.setPath(path);

			// Creating a binding

			dataBinder = new DataBinder(socialIdentity, "socialIdentity");
			binding = dataBinder.getBindingResult();

			reconstructed = this.socialIdentityService.reconstruct(socialIdentity, binding);
			Assert.isTrue(!binding.hasErrors()); // If the moment was wrong the binding will have errors

			saved = this.socialIdentityService.save(reconstructed);
			this.socialIdentityService.flush();

			Assert.isTrue(saved.getId() != 0);

			Assert.isTrue(this.socialIdentityService.findAll().contains(saved)); // Check if the manager has been charged

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editionSocialIdentityTemplate(final String username, final Integer socialIdentityId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final SocialIdentity socialIdentity = this.socialIdentityService.findOneEdit(socialIdentityId);
			SocialIdentity reconstructed, saved;
			final DataBinder dataBinder;
			BindingResult binding;

			// Simulating form

			socialIdentity.setUsername("Testing2");
			socialIdentity.setPath("https://twitter.com/");

			// Creating a binding

			dataBinder = new DataBinder(socialIdentity, "content");
			binding = dataBinder.getBindingResult();

			reconstructed = this.socialIdentityService.reconstruct(socialIdentity, binding);
			Assert.isTrue(!binding.hasErrors()); // If the moment was wrong the binding will have errors

			saved = this.socialIdentityService.save(reconstructed);
			this.socialIdentityService.flush();

			Assert.isTrue(saved.getId() != 0);

			Assert.isTrue(this.socialIdentityService.findAll().contains(saved)); // Check if the manager has been charged

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void deleteSocialIdentityTemplate(final String username, final Integer socialIdentityId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final SocialIdentity socialIdentity = this.socialIdentityService.findOneEdit(socialIdentityId);

			// Creating a binding

			this.socialIdentityService.delete(socialIdentity);
			this.socialIdentityService.flush();

			Assert.isTrue(!this.socialIdentityService.findAll().contains(socialIdentity)); // Check if the manager has been charged

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
