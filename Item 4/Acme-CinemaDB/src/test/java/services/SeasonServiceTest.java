
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
import domain.Season;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SeasonServiceTest extends AbstractTest {

	// System under test ----------------------------

	@Autowired
	private SeasonService	seasonService;


	// Tests ----------------------------------------

	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * Registrar temporadas y editar y listar las que ha registrado..
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the seasons
	 * , which includes listing, registering and modifying.
	 * In order to register a new season, he must have registered a tvShow.
	 * 
	 * Expected errors:
	 * - A producer tries to register a new season but he/she does not write this tvShow -> IllegalArgumentException
	 * - A user tries to register a new season -> IllegalArgumentException
	 */

	@Test
	public void createSeasonDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"producer3", 300, null
			}, { // Content without year
				"producer1", 300, IllegalArgumentException.class
			}, { // A user tries to create an content 
				"user3", 300, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createSeasonTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * Registrar temporadas y editar y listar las que ha registrado..
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the seasons
	 * , which includes listing, registering and modifying.
	 * In order to register a new season, he must have registered a tvShow.
	 * 
	 * Expected errors:
	 * - A producer tries to edit a new season but he/she does not write this season -> IllegalArgumentException
	 * - A user tries to edit a season -> IllegalArgumentException
	 */

	@Test
	public void editSeasonDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"producer3", 305, null
			}, { // A producer tries to edit a content of another producer
				"producer1", 305, IllegalArgumentException.class
			}, { // A user tries to create a content
				"user3", 305, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editionSeasonTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ------------------------------------

	protected void createSeasonTemplate(final String username, final Integer tvShowId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final Season season = this.seasonService.create(tvShowId);
			Season reconstructed, saved;
			final DataBinder dataBinder;
			BindingResult binding;

			// Simulating form

			season.setName("Testing");

			// Creating a binding

			dataBinder = new DataBinder(season, "content");
			binding = dataBinder.getBindingResult();

			reconstructed = this.seasonService.reconstruct(season, binding);
			Assert.isTrue(!binding.hasErrors()); // If the moment was wrong the binding will have errors

			saved = this.seasonService.save(reconstructed);
			this.seasonService.flush();

			Assert.isTrue(saved.getId() != 0);

			Assert.isTrue(this.seasonService.findAll().contains(saved)); // Check if the manager has been charged

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editionSeasonTemplate(final String username, final Integer seasonId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final Season season = this.seasonService.findOneEdit(seasonId);
			Season reconstructed, saved;
			final DataBinder dataBinder;
			BindingResult binding;

			// Simulating form

			season.setName("Testing2");

			// Creating a binding

			dataBinder = new DataBinder(season, "content");
			binding = dataBinder.getBindingResult();

			reconstructed = this.seasonService.reconstruct(season, binding);
			Assert.isTrue(!binding.hasErrors()); // If the moment was wrong the binding will have errors

			saved = this.seasonService.save(reconstructed);
			this.seasonService.flush();

			Assert.isTrue(saved.getId() != 0);

			Assert.isTrue(this.seasonService.findAll().contains(saved)); // Check if the manager has been charged

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
