
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MovieRepository;
import domain.CinematicEntity;
import domain.Genre;
import domain.Movie;
import forms.MovieForm;

@Service
@Transactional
public class MovieService {

	// Managed repository -----------------------------------

	@Autowired
	private MovieRepository			movieRepository;

	@Autowired
	private ContentService			contentService;

	@Autowired
	private ProducerService			producerService;

	@Autowired
	private GenreService			genreService;

	@Autowired
	private CinematicEntityService	cinematicEntityService;

	@Autowired
	private Validator				validator;


	// Constructors -----------------------------------------

	public MovieService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Movie create() {
		Movie res;

		res = new Movie();

		return res;
	}

	public Movie save(final Movie movie) {
		Movie res;

		movie.setTitle("Star Wars");

		res = this.movieRepository.save(movie);

		return res;
	}

	public Movie addCinematicEntity(final Integer movieId, final List<Integer> cinematicEntitiesId) {
		Movie res;
		final Movie movie;
		final Set<CinematicEntity> cinematicEntities = new HashSet<>();

		movie = this.findOneEdit(movieId);

		for (final Integer id : cinematicEntitiesId)
			cinematicEntities.add(this.cinematicEntityService.findOne(id));

		cinematicEntities.addAll(movie.getCinematicEntities());

		movie.setCinematicEntities(cinematicEntities);

		res = this.movieRepository.save(movie);

		return res;
	}
	public Movie reconstruct(final MovieForm movieForm, final BindingResult binding) {
		Movie aux, movie;
		final Collection<Genre> selectedGenres = new ArrayList<>();

		movie = movieForm.getMovie();

		movie.setProducer(this.producerService.findByPrincipal());

		if (!(movieForm.getGenres() == null))
			for (final Integer kind : movieForm.getGenres())
				selectedGenres.add(this.genreService.genreByKind(kind));

		movie.setGenres(selectedGenres);

		movie.setCinematicEntities(new HashSet<CinematicEntity>());

		if (movie.getId() != 0) {
			aux = this.findOne(movieForm.getId());
			movie.setVersion(aux.getVersion());
			movie.setAvgRating(aux.getAvgRating());
			movie.setCinematicEntities(aux.getCinematicEntities());
		}

		this.contentService.checkURLs(movie.getVideos(), binding);

		this.validator.validate(movie, binding);

		return movie;
	}

	//	public void delete(final Movie movie) {
	//
	//		Assert.notNull(movie);
	//
	//		Assert.isTrue(movie.getId() != 0);
	//
	//		Assert.isTrue(this.movieRepository.exists(movie.getId()));
	//		;
	//
	//		this.movieRepository.delete(movie);
	//
	//	}

	public Collection<Movie> findAll() {
		Collection<Movie> result;

		result = this.movieRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Movie findOne(final int movieId) {
		Assert.notNull(movieId);
		Assert.isTrue(movieId != 0);

		Movie result;

		result = this.movieRepository.findOne(movieId);
		Assert.notNull(result);

		return result;
	}

	public Movie findOneEdit(final int movieId) {
		Assert.notNull(movieId);
		Assert.isTrue(movieId != 0);

		Movie result;

		result = this.movieRepository.findOne(movieId);
		Assert.notNull(result);
		Assert.isTrue(result.getProducer().equals(this.producerService.findByPrincipal()), "Couldn`t edit this movie");

		return result;
	}

	// Other CRUD methods ----------------------------------

	public Collection<Movie> findAllProducer() {
		Collection<Movie> result;

		result = this.movieRepository.findAllProducer(this.producerService.findByPrincipal().getId());
		Assert.notNull(result);

		return result;

	}

	// Other business methods ----------------------------------

	public Long findMinMoviesProducer() {
		Long result;
		List<Integer> allProducers, allProducersMovies;
		List<Long> movieMin;

		allProducers = this.producerService.findAllProducerId();
		allProducersMovies = this.producerService.findAllProducerWithMoviesId();

		if (!allProducersMovies.containsAll(allProducers))
			return 0L;

		result = 0L;
		movieMin = this.movieRepository.findMinMoviesProducer();

		if (!movieMin.isEmpty())
			result = movieMin.get(0);

		return result;
	}

	public Long findMaxMoviesProducer() {
		Long result;
		List<Long> movieMax;

		result = 0L;
		movieMax = this.movieRepository.findMaxMoviesProducer();

		if (!movieMax.isEmpty())
			result = movieMax.get(0);

		return result;
	}

	public Double findAvgMoviesProducer() {
		Double result;

		result = this.movieRepository.findAvgMoviesProducer();

		return result;
	}

	public List<Integer[]> findNumberOfMoviesPerType() {
		List<Integer[]> result;

		result = this.movieRepository.findNumberOfMoviesPerType();

		return result;
	}

}
