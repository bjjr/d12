
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TvShowRepository;
import domain.Chapter;
import domain.Season;
import domain.TvShow;

@Service
@Transactional
public class TvShowService {

	// Managed repository -----------------------------------

	@Autowired
	private TvShowRepository	tvShowRepository;


	// Constructors -----------------------------------------

	public TvShowService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

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

}
