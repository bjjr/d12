
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CriticRepository;
import domain.Critic;

@Service
@Transactional
public class CriticService {

	// Managed repository -----------------------------------

	@Autowired
	private CriticRepository	criticRepository;


	// Constructors -----------------------------------------

	public CriticService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Collection<Critic> findAll() {
		Collection<Critic> result;

		result = this.criticRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Critic findOne(final int criticId) {
		Assert.notNull(criticId);
		Assert.isTrue(criticId != 0);

		Critic result;

		result = this.criticRepository.findOne(criticId);
		Assert.notNull(result);

		return result;
	}

}
