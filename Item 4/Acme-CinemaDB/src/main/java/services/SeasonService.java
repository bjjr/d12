
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SeasonRepository;
import domain.Producer;
import domain.Season;

@Service
@Transactional
public class SeasonService {

	// Managed repository -----------------------------------

	@Autowired
	private SeasonRepository	seasonRepository;

	@Autowired
	private ProducerService		producerService;

	@Autowired
	private TvShowService		tvShowService;

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------

	public SeasonService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Season create(final int tvShowId) {
		Season res;

		res = new Season();

		res.setTvShow(this.tvShowService.findOneEdit(tvShowId));

		return res;
	}

	public Season save(final Season season) {
		Season res;

		res = this.seasonRepository.save(season);

		return res;
	}

	public Season reconstruct(final Season season, final BindingResult binding) {
		Season aux;

		if (season.getId() != 0) {
			aux = this.findOneEdit(season.getId());
			season.setVersion(aux.getVersion());
			season.setTvShow(aux.getTvShow());
		} else {
			Producer producer;

			producer = this.producerService.findByPrincipal();

			Assert.isTrue(season.getTvShow().getProducer().getId() == producer.getId(), "Couldn`t create this season");
		}

		this.validator.validate(season, binding);

		return season;
	}
	//	public void delete(final Season season) {
	//
	//		Assert.notNull(season);
	//
	//		Assert.isTrue(season.getId() != 0);
	//
	//		Assert.isTrue(this.seasonRepository.exists(season.getId()));
	//
	//		this.seasonRepository.delete(season);
	//
	//	}

	public Collection<Season> findAll() {
		Collection<Season> result;

		result = this.seasonRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Season findOne(final int seasonId) {
		Assert.notNull(seasonId);
		Assert.isTrue(seasonId != 0);

		Season result;

		result = this.seasonRepository.findOne(seasonId);
		Assert.notNull(result);

		return result;
	}

	public Season findOneEdit(final int seasonId) {
		Assert.notNull(seasonId);
		Assert.isTrue(seasonId != 0);

		Producer producer;
		Season result;

		result = this.seasonRepository.findOne(seasonId);
		Assert.notNull(result);

		producer = this.producerService.findByPrincipal();

		Assert.isTrue(result.getTvShow().getProducer().getId() == producer.getId(), "Couldn`t edit this season");

		return result;
	}
}
