
package services;

import java.util.ArrayList;
import java.util.List;

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
import domain.Movie;
import domain.TvShow;
import forms.MovieForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ContentServiceTest extends AbstractTest {

	// System under test ----------------------------

	@Autowired
	private ContentService			contentService;

	// Services -------------------------------------

	@Autowired
	private MovieService			movieService;

	@Autowired
	private TvShowService			tvShowService;

	@Autowired
	private CinematicEntityService	cinematicEntityService;


	// Tests ----------------------------------------

	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * Registrar películas y editar y listar las que ha registrado..
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the movies
	 * , which includes listing, registering and modifying.
	 * In order to register a new movie, he must have registered a year.
	 * 
	 * Expected errors:
	 * - A producer tries to register a new movie but he/she does not write a year -> IllegalArgumentException
	 * - A user tries to register a new movie -> IllegalArgumentException
	 */

	@Test
	public void createMovieDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"producer3", 1996, null
			}, { // Content without year
				"producer3", null, NullPointerException.class
			}, { // A user tries to create an content 
				"user3", 1996, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createMovieTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * Registrar películas y editar y listar las que ha registrado..
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the movies
	 * , which includes listing, registering and modifying.
	 * In order to register a new movie, he must have registered a year.
	 * 
	 * Expected errors:
	 * - A producer tries to edit a new movie but he/she does not write this movie -> IllegalArgumentException
	 * - A user tries to edit a movie -> IllegalArgumentException
	 */

	@Test
	public void editMovieDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"producer3", 297, null
			}, { // A producer tries to edit a content of another producer
				"producer1", 297, IllegalArgumentException.class
			}, { // A user tries to create a content
				"user3", 297, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editionMovieTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * Registrar series y editar y listar las que ha registrado..
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the tv shows
	 * , which includes listing, registering and modifying.
	 * In order to register a new tv show, he must have registered a year.
	 * 
	 * Expected errors:
	 * - A producer tries to register a new tv show but he/she does not write a year -> IllegalArgumentException
	 * - A user tries to register a new tv show -> IllegalArgumentException
	 */

	@Test
	public void createTvShowDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"producer3", 1996, null
			}, { // Content without year
				"producer3", null, NullPointerException.class
			}, { // A user tries to create an content 
				"user3", 1996, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createTvShowTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * Registrar series y editar y listar las que ha registrado..
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the tv shows
	 * , which includes listing, registering and modifying.
	 * In order to register a new tv show, he must have registered a year.
	 * 
	 * Expected errors:
	 * - A producer tries to register a new tv show but he/she does not write this tv show -> IllegalArgumentException
	 * - A user tries to edit a tv show -> IllegalArgumentException
	 */

	@Test
	public void editTvShowDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"producer3", 300, null
			}, { // A producer tries to edit a content of another producer
				"producer1", 300, IllegalArgumentException.class
			}, { // A user tries to create a content
				"user3", 300, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editionTvShowTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * añadir actores y directores a las peliculas que ha registrado..
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the actors/directors of her/his movies
	 * 
	 * Expected errors:
	 * - A producer tries to add actors/directors but he/she does not register this movie-> IllegalArgumentException
	 * - A user tries to add an actor/director -> IllegalArgumentException
	 */

	@Test
	public void addCinematicEntityToMovieDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"producer3", 297, null
			}, { // A producer tries to edit a content of another producer
				"producer1", 297, IllegalArgumentException.class
			}, { // A user tries to create a content
				"user3", 297, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.addCinematicEntitytoMovieTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * añadir actores y directores a las peliculas que ha registrado..
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the actors/directors of her/his movies
	 * 
	 * Expected errors:
	 * - A producer tries to add actors/directors but he/she does not register this movie-> IllegalArgumentException
	 * - A user tries to add an actor/director -> IllegalArgumentException
	 */

	@Test
	public void addCinematicEntityToTvShowDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"producer3", 300, null
			}, { // A producer tries to edit a content of another producer
				"producer1", 300, IllegalArgumentException.class
			}, { // A user tries to create a content
				"user3", 300, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.addCinematicEntitytoTvShowTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ------------------------------------

	protected void createMovieTemplate(final String username, final Integer year, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final MovieForm content = new MovieForm();
			Movie reconstructed, saved;
			final DataBinder dataBinder;
			BindingResult binding;
			final List<Integer> genres = new ArrayList<>();

			// Simulating form

			genres.add(2);

			content.setGenres(genres);
			content.setTitle("Testing");
			content.setYear(year);
			content.setImages(new ArrayList<String>());
			content.setVideos(new ArrayList<String>());

			// Creating a binding

			dataBinder = new DataBinder(content, "content");
			binding = dataBinder.getBindingResult();

			reconstructed = this.movieService.reconstruct(content, binding);
			Assert.isTrue(!binding.hasErrors()); // If the moment was wrong the binding will have errors

			saved = this.movieService.save(reconstructed);
			this.movieService.flush();

			Assert.isTrue(saved.getId() != 0);

			Assert.isTrue(this.contentService.findAll().contains(saved)); // Check if the manager has been charged

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editionMovieTemplate(final String username, final Integer contentId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final MovieForm content = new MovieForm(this.movieService.findOneEdit(contentId));
			Movie reconstructed, saved;
			final DataBinder dataBinder;
			BindingResult binding;
			final List<Integer> genres = new ArrayList<>();

			// Simulating form

			genres.add(1);

			content.setGenres(genres);
			content.setTitle("Testing2");
			content.setYear(1980);

			// Creating a binding

			dataBinder = new DataBinder(content, "content");
			binding = dataBinder.getBindingResult();

			reconstructed = this.movieService.reconstruct(content, binding);
			Assert.isTrue(!binding.hasErrors()); // If the moment was wrong the binding will have errors

			saved = this.movieService.save(reconstructed);
			this.movieService.flush();

			Assert.isTrue(saved.getId() != 0);

			Assert.isTrue(this.contentService.findAll().contains(saved)); // Check if the manager has been charged

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void createTvShowTemplate(final String username, final Integer year, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final MovieForm content = new MovieForm();
			TvShow reconstructed, saved;
			final DataBinder dataBinder;
			BindingResult binding;
			final List<Integer> genres = new ArrayList<>();

			// Simulating form

			genres.add(2);

			content.setGenres(genres);
			content.setTitle("Testing");
			content.setYear(year);
			content.setImages(new ArrayList<String>());
			content.setVideos(new ArrayList<String>());

			// Creating a binding

			dataBinder = new DataBinder(content, "content");
			binding = dataBinder.getBindingResult();

			reconstructed = this.tvShowService.reconstruct(content, binding);
			Assert.isTrue(!binding.hasErrors()); // If the moment was wrong the binding will have errors

			saved = this.tvShowService.save(reconstructed);
			this.tvShowService.flush();

			Assert.isTrue(saved.getId() != 0);

			Assert.isTrue(this.contentService.findAll().contains(saved)); // Check if the manager has been charged

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editionTvShowTemplate(final String username, final Integer contentId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final MovieForm content = new MovieForm(this.tvShowService.findOneEdit(contentId));
			TvShow reconstructed, saved;
			final DataBinder dataBinder;
			BindingResult binding;
			final List<Integer> genres = new ArrayList<>();

			// Simulating form

			genres.add(1);

			content.setGenres(genres);
			content.setTitle("Testing2");
			content.setYear(1980);

			// Creating a binding

			dataBinder = new DataBinder(content, "content");
			binding = dataBinder.getBindingResult();

			reconstructed = this.tvShowService.reconstruct(content, binding);
			Assert.isTrue(!binding.hasErrors()); // If the moment was wrong the binding will have errors

			saved = this.tvShowService.save(reconstructed);
			this.tvShowService.flush();

			Assert.isTrue(saved.getId() != 0);

			Assert.isTrue(this.contentService.findAll().contains(saved)); // Check if the manager has been charged

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void addCinematicEntitytoMovieTemplate(final String username, final Integer contentId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final List<Integer> cinematicEntitiesId = new ArrayList<>();

			cinematicEntitiesId.add(286);

			this.movieService.addCinematicEntity(contentId, cinematicEntitiesId);
			this.movieService.flush();

			Assert.isTrue(this.movieService.findOne(contentId).getCinematicEntities().contains(this.cinematicEntityService.findOne(286)));

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void addCinematicEntitytoTvShowTemplate(final String username, final Integer contentId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final List<Integer> cinematicEntitiesId = new ArrayList<>();

			cinematicEntitiesId.add(286);

			this.tvShowService.addCinematicEntity(contentId, cinematicEntitiesId);
			this.tvShowService.flush();

			Assert.isTrue(this.tvShowService.findOne(contentId).getCinematicEntities().contains(this.cinematicEntityService.findOne(286)));

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
