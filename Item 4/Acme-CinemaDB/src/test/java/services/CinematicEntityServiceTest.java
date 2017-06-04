
package services;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import utilities.AbstractTest;
import domain.CinematicEntity;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CinematicEntityServiceTest extends AbstractTest {

	// System under test ----------------------------

	@Autowired
	private CinematicEntityService	cinematicEntityService;

	// Services -------------------------------------

	@Autowired
	private ProducerService			producerService;


	// Tests ----------------------------------------

	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * Registrar actores/directores y editar actores/directores.
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the cinematicEntitys
	 * , which includes listing, registering and modifying.
	 * In order to register a new cinematicEntity, he must have registered a birth date.
	 * 
	 * Expected errors:
	 * - A producer tries to register a new cinematicEntity but he/she does not write a birth date -> IllegalArgumentException
	 * - A user tries to register a new cinematicEntity -> IllegalArgumentException
	 */

	@Test
	public void createDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"producer3", "01/01/1980 00:00", null
			}, { // CinematicEntity with an invalid birth date
				"producer3", "01/01/2025 00:00", IllegalArgumentException.class
			}, { // A user tries to create an cinematicEntity
				"user3", "01/01/1996 00:00", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * Registrar actores/directores y editar actores/directores.
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the cinematicEntitys
	 * , which includes listing, registering and modifying.
	 * In order to register a new cinematicEntity, he must have registered a birth date.
	 * 
	 * Expected errors:
	 * - A producer tries to register a new cinematicEntity but he/she does not write a birth date -> IllegalArgumentException
	 * - A user tries to register a new cinematicEntity -> IllegalArgumentException
	 */

	@Test
	public void editDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"producer3", 289, "01/01/1980 00:00", null
			}, { // CinematicEntity with an invalid birth date
				"producer3", 289, "01/01/2025 00:00", IllegalArgumentException.class
			}, { // A user tries to create an cinematicEntity
				"user3", 289, "01/01/1996 00:00", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editionTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	// Templates ------------------------------------

	protected void createTemplate(final String username, final String moment, final Class<?> expected) {
		Class<?> caught;
		DateTimeFormatter dtf;
		DateTime dt;

		dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		dt = dtf.parseDateTime(moment);

		caught = null;

		try {
			this.authenticate(username);

			CinematicEntity cinematicEntity, reconstructed, saved;
			DataBinder dataBinder;
			BindingResult binding;

			// Simulating form

			cinematicEntity = this.cinematicEntityService.create(); // This method looks for manager's credit card
			cinematicEntity.setName("Testing");
			cinematicEntity.setBirthdate(dt.toDate());
			cinematicEntity.setBio("Testing");
			cinematicEntity.setSurname("Testing");
			cinematicEntity.setProducer(this.producerService.findByPrincipal());
			cinematicEntity.setDirector(false);

			// Creating a binding

			dataBinder = new DataBinder(cinematicEntity, "cinematicEntity");
			binding = dataBinder.getBindingResult();

			reconstructed = this.cinematicEntityService.reconstruct(cinematicEntity, binding);
			Assert.isTrue(!binding.hasErrors()); // If the moment was wrong the binding will have errors

			saved = this.cinematicEntityService.save(reconstructed);
			this.cinematicEntityService.flush();

			Assert.isTrue(saved.getId() != 0);

			Assert.isTrue(this.cinematicEntityService.findAll().contains(saved)); // Check if the manager has been charged

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editionTemplate(final String username, final int cinematicEntityId, final String moment, final Class<?> expected) {
		Class<?> caught;
		DateTimeFormatter dtf;
		DateTime dt;

		dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		dt = dtf.parseDateTime(moment);

		caught = null;

		try {
			this.authenticate(username);

			final CinematicEntity cinematicEntity;
			CinematicEntity reconstructed, saved;
			DataBinder dataBinder;
			BindingResult binding;

			cinematicEntity = this.cinematicEntityService.findOne(cinematicEntityId);

			// Simulating form

			cinematicEntity.setName("Testing2");
			cinematicEntity.setBirthdate(dt.toDate());
			cinematicEntity.setBio("Testing2");
			cinematicEntity.setSurname("Testing2");
			cinematicEntity.setProducer(this.producerService.findByPrincipal());
			cinematicEntity.setDirector(false);

			// Creating a binding

			dataBinder = new DataBinder(cinematicEntity, "cinematicEntity");
			binding = dataBinder.getBindingResult();

			reconstructed = this.cinematicEntityService.reconstruct(cinematicEntity, binding);
			Assert.isTrue(!binding.hasErrors()); // If the moment was wrong the binding will have errors

			saved = this.cinematicEntityService.save(reconstructed);
			this.cinematicEntityService.flush();

			Assert.isTrue(cinematicEntity.getId() == saved.getId());

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
