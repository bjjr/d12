
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AssessableEntityRepository;
import domain.AssessableEntity;

@Service
@Transactional
public class AssessableEntityService {

	// Managed repository -----------------------------------

	@Autowired
	private AssessableEntityRepository	assessableEntityRepository;


	// Constructors -----------------------------------------

	public AssessableEntityService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Collection<AssessableEntity> findAll() {
		Collection<AssessableEntity> result;

		result = this.assessableEntityRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public AssessableEntity findOne(final int assessableEId) {
		Assert.notNull(assessableEId);
		Assert.isTrue(assessableEId != 0);

		AssessableEntity result;

		result = this.assessableEntityRepository.findOne(assessableEId);
		Assert.notNull(result);

		return result;
	}

}
