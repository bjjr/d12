
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.GenreRepository;
import domain.Genre;

@Service
@Transactional
public class GenreService {

	// Managed repository -----------------------------------

	@Autowired
	private GenreRepository	genreRepository;


	// Constructors -----------------------------------------

	public GenreService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Collection<Genre> findAll() {
		Collection<Genre> result;

		result = this.genreRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Genre findOne(final int genreId) {
		Assert.notNull(genreId);
		Assert.isTrue(genreId != 0);

		Genre result;

		result = this.genreRepository.findOne(genreId);
		Assert.notNull(result);

		return result;
	}

	// Other CRUD methods ----------------------------------

	public Genre genreByKind(final Integer kind) {
		Assert.notNull(kind);

		Genre result;

		result = this.genreRepository.genreByKind(kind);
		Assert.notNull(result);

		return result;
	}

}
