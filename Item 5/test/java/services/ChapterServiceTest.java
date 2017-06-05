
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
import domain.Chapter;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChapterServiceTest extends AbstractTest {

	// System under test ----------------------------

	@Autowired
	private ChapterService	chapterService;


	// Tests ----------------------------------------

	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * Registrar temporadas y editar y listar las que ha registrado..
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the chapters
	 * , which includes listing, registering and modifying.
	 * In order to register a new chapter, he must have registered a season.
	 * 
	 * Expected errors:
	 * - A producer tries to register a new chapter but he/she does not write this season -> IllegalArgumentException
	 * - A user tries to register a new chapter -> IllegalArgumentException
	 */

	@Test
	public void createChapterDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"producer3", 305, null
			}, { // Content without year
				"producer1", 305, IllegalArgumentException.class
			}, { // A user tries to create an content 
				"user3", 305, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createChapterTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/*
	 * Use case: Un productor autenticado debe ser capaz de realizar:
	 * Registrar temporadas y editar y listar las que ha registrado..
	 * 
	 * Functional requierements: An actor who is authenticated as a producer must be able to manage the chapters
	 * , which includes listing, registering and modifying.
	 * In order to register a new chapter, he must have registered a season.
	 * 
	 * Expected errors:
	 * - A producer tries to edit a new chapter but he/she does not write this chapter -> IllegalArgumentException
	 * - A user tries to edit a chapter -> IllegalArgumentException
	 */

	@Test
	public void editChapterDriver() {
		final Object testingData[][] = {
			{ // Successful test
				"producer3", 315, null
			}, { // A producer tries to edit a content of another producer
				"producer1", 315, IllegalArgumentException.class
			}, { // A user tries to create a content
				"user3", 315, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editionChapterTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ------------------------------------

	protected void createChapterTemplate(final String username, final Integer seasonId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final Chapter chapter = this.chapterService.create(seasonId);
			Chapter reconstructed, saved;
			final DataBinder dataBinder;
			BindingResult binding;

			// Simulating form

			chapter.setName("Testing");

			// Creating a binding

			dataBinder = new DataBinder(chapter, "content");
			binding = dataBinder.getBindingResult();

			reconstructed = this.chapterService.reconstruct(chapter, binding);
			Assert.isTrue(!binding.hasErrors()); // If the moment was wrong the binding will have errors

			saved = this.chapterService.save(reconstructed);
			this.chapterService.flush();

			Assert.isTrue(saved.getId() != 0);

			Assert.isTrue(this.chapterService.findAll().contains(saved)); // Check if the manager has been charged

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editionChapterTemplate(final String username, final Integer chapterId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final Chapter chapter = this.chapterService.findOneEdit(chapterId);
			Chapter reconstructed, saved;
			final DataBinder dataBinder;
			BindingResult binding;

			// Simulating form

			chapter.setName("Testing2");

			// Creating a binding

			dataBinder = new DataBinder(chapter, "content");
			binding = dataBinder.getBindingResult();

			reconstructed = this.chapterService.reconstruct(chapter, binding);
			Assert.isTrue(!binding.hasErrors()); // If the moment was wrong the binding will have errors

			saved = this.chapterService.save(reconstructed);
			this.chapterService.flush();

			Assert.isTrue(saved.getId() != 0);

			Assert.isTrue(this.chapterService.findAll().contains(saved)); // Check if the manager has been charged

		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
