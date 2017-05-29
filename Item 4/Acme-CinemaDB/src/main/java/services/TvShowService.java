
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

import repositories.TvShowRepository;
import domain.Chapter;
import domain.CinematicEntity;
import domain.Genre;
import domain.Season;
import domain.TvShow;
import forms.MovieForm;

@Service
@Transactional
public class TvShowService {

	// Managed repository -----------------------------------

	@Autowired
	private TvShowRepository		tvShowRepository;

	@Autowired
	private ProducerService			producerService;

	@Autowired
	private GenreService			genreService;

	@Autowired
	private ContentService			contentService;

	@Autowired
	private SeasonService			seasonService;

	@Autowired
	private ChapterService			chapterService;

	@Autowired
	private CinematicEntityService	cinematicEntityService;

	@Autowired
	private Validator				validator;


	// Constructors -----------------------------------------

	public TvShowService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public TvShow create() {
		TvShow res;

		res = new TvShow();

		return res;
	}

	public TvShow save(final TvShow tvShow) {
		TvShow res;

		res = this.tvShowRepository.save(tvShow);

		return res;
	}

	public TvShow reconstruct(final MovieForm tvShowForm, final BindingResult binding) {
		TvShow aux, tvShow;
		final Collection<Genre> selectedGenres = new ArrayList<>();

		tvShow = tvShowForm.getTvShow();

		tvShow.setProducer(this.producerService.findByPrincipal());

		if (!(tvShowForm.getGenres() == null))
			for (final Integer kind : tvShowForm.getGenres())
				selectedGenres.add(this.genreService.genreByKind(kind));

		tvShow.setGenres(selectedGenres);

		tvShow.setCinematicEntities(new HashSet<CinematicEntity>());

		if (tvShow.getId() != 0) {
			aux = this.findOne(tvShowForm.getId());
			tvShow.setVersion(aux.getVersion());
			tvShow.setAvgRating(aux.getAvgRating());
			tvShow.setCinematicEntities(aux.getCinematicEntities());
		}

		this.contentService.checkURLs(tvShow.getVideos(), binding);

		this.validator.validate(tvShow, binding);

		return tvShow;
	}

	public TvShow addCinematicEntity(final Integer tvShowId, final List<Integer> cinematicEntitiesId) {
		TvShow res;
		final TvShow tvShow;
		final Set<CinematicEntity> cinematicEntities = new HashSet<>();

		tvShow = this.findOneEdit(tvShowId);

		for (final Integer id : cinematicEntitiesId)
			cinematicEntities.add(this.cinematicEntityService.findOne(id));

		cinematicEntities.addAll(tvShow.getCinematicEntities());

		tvShow.setCinematicEntities(cinematicEntities);

		res = this.tvShowRepository.save(tvShow);

		return res;
	}

	//	public void delete(final TvShow tvShow) {
	//		List<Season> seasons;
	//		final List<Chapter> chapters = new ArrayList<>();
	//
	//		Assert.notNull(tvShow);
	//
	//		Assert.isTrue(tvShow.getId() != 0);
	//
	//		Assert.isTrue(this.tvShowRepository.exists(tvShow.getId()));
	//
	//		seasons = this.tvShowRepository.getSeasons(tvShow.getId());
	//
	//		for (final Season season : seasons) {
	//			for (final Chapter chapter : this.tvShowRepository.getChapter(season.getId()))
	//				this.chapterService.delete(chapter);
	//			this.seasonService.delete(season);
	//		}
	//
	//		this.tvShowRepository.delete(tvShow);
	//
	//	}
	public Collection<TvShow> findAll() {
		Collection<TvShow> result;

		result = this.tvShowRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public TvShow findOne(final int tvShowId) {
		Assert.notNull(tvShowId);
		Assert.isTrue(tvShowId != 0);

		TvShow result;

		result = this.tvShowRepository.findOne(tvShowId);
		Assert.notNull(result);

		return result;
	}

	// Other CRUD methods ----------------------------------

	public List<Season> getSeasons(final int tvShowId) {
		List<Season> resultSeasons;

		resultSeasons = this.tvShowRepository.getSeasons(tvShowId);

		return resultSeasons;
	}

	public List<Chapter> getChapters(final int seasonId) {
		List<Chapter> resultChapters;

		resultChapters = this.tvShowRepository.getChapter(seasonId);

		return resultChapters;
	}

	public TvShow findOneEdit(final int tvShowId) {
		Assert.notNull(tvShowId);
		Assert.isTrue(tvShowId != 0);

		TvShow result;

		result = this.tvShowRepository.findOne(tvShowId);
		Assert.notNull(result);
		Assert.isTrue(result.getProducer().equals(this.producerService.findByPrincipal()), "Couldn`t edit this tvShow");

		return result;
	}

	// Other CRUD methods ----------------------------------

	public Collection<TvShow> findAllProducer() {
		Collection<TvShow> result;

		result = this.tvShowRepository.findAllProducer(this.producerService.findByPrincipal().getId());
		Assert.notNull(result);

		return result;

	}

}
