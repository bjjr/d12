
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SeasonRepository;
import domain.Season;

@Service
@Transactional
public class SeasonService {

	// Managed repository -----------------------------------

	@Autowired
	private SeasonRepository	seasonRepository;


	// Constructors -----------------------------------------

	public SeasonService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

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

}
