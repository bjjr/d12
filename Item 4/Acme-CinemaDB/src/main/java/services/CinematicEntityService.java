
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CinematicEntityRepository;
import domain.CinematicEntity;

@Service
@Transactional
public class CinematicEntityService {

	// Managed repository -----------------------------------

	@Autowired
	private CinematicEntityRepository	cinematicEntityRepository;


	// Constructors -----------------------------------------

	public CinematicEntityService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Collection<CinematicEntity> findAll() {
		Collection<CinematicEntity> result;

		result = this.cinematicEntityRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public CinematicEntity findOne(final int cinematicEntityId) {
		Assert.notNull(cinematicEntityId);
		Assert.isTrue(cinematicEntityId != 0);

		CinematicEntity result;

		result = this.cinematicEntityRepository.findOne(cinematicEntityId);
		Assert.notNull(result);

		return result;
	}

	// Other CRUD methods ----------------------------------

	public List<CinematicEntity> searchCinematicEntity(final String s) {
		List<CinematicEntity> resultCinematicEntities;

		resultCinematicEntities = this.cinematicEntityRepository.searchCinematicEntity(s);

		return resultCinematicEntities;
	}

}
