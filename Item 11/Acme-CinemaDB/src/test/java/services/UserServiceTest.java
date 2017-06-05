
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import utilities.AbstractTest;
import domain.User;
import forms.ActorForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceTest extends AbstractTest {

	// System under test ----------------------------

	@Autowired
	private UserService	userService;


	// Supporting Services --------------------------

	// Tests ----------------------------------------

	/*
	 * Use case: A person tries to create an user account.
	 * Functional Requirements:
	 * - Un actor no autenticado debe ser capaz de registrarse como un nuevo usuario.
	 */

	@Test
	public void userRegistrationDriver() {
		final Object testingData[][] = {
			// User inputs valid data
			{
				null, "+34123456789", "user@email.com", null
			},
			// User inputs an invalid phone number
			{
				null, "test", "user@email.com", IllegalArgumentException.class
			},
			// User inputs an invalid email
			{
				null, "+34123456789", "test", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.userRegistrationTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void userRegistrationTemplate(final String username, final String phone, final String email, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			User user, reconstructed, saved;
			ActorForm actorForm;
			DataBinder dataBinder;
			BindingResult binding;

			user = this.userService.create();

			user.setName("name");
			user.setSurname("surname");
			user.setCountry("country");
			user.getUserAccount().setUsername("username");
			user.getUserAccount().setPassword("password");
			user.setCreditCard(null);
			user.setEmail(email);
			user.setPhone(phone);

			actorForm = new ActorForm(user);

			actorForm.setConfirmPassword("password");

			dataBinder = new DataBinder(actorForm, "actorForm");
			binding = dataBinder.getBindingResult();

			reconstructed = this.userService.reconstruct(actorForm, binding);

			Assert.isTrue(!binding.hasErrors());

			saved = this.userService.save(reconstructed);
			Assert.isTrue(saved.getId() != 0);

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
