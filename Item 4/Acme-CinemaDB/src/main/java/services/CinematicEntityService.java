
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CinematicEntityRepository;
import security.Authority;
import domain.CinematicEntity;
import domain.Content;

@Service
@Transactional
public class CinematicEntityService {

	// Managed repository -----------------------------------

	@Autowired
	private CinematicEntityRepository	cinematicEntityRepository;

	@Autowired
	private ProducerService				producerService;

	@Autowired
	private ActorService				actorService;

	// Validator --------------------------------------------

	@Autowired
	private Validator					validator;


	// Constructors -----------------------------------------

	public CinematicEntityService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public CinematicEntity create() {
		CinematicEntity res;

		res = new CinematicEntity();

		//res.setProducer(this.producerService.findByPrincipal());

		return res;
	}

	public CinematicEntity save(final CinematicEntity cinematicEntity) {
		CinematicEntity res;

		Assert.isTrue(this.actorService.checkAuthority(Authority.PRODUCER));

		res = this.cinematicEntityRepository.save(cinematicEntity);

		return res;
	}

	public CinematicEntity reconstruct(final CinematicEntity cinematicEntity, final BindingResult binding) {

		this.validator.validate(cinematicEntity, binding);

		return cinematicEntity;
	}

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

	public CinematicEntity findOneEdit(final int cinematicEntityId) {
		Assert.notNull(cinematicEntityId);
		Assert.isTrue(cinematicEntityId != 0);

		CinematicEntity result;

		result = this.cinematicEntityRepository.findOne(cinematicEntityId);
		Assert.notNull(result);
		Assert.isTrue(result.getProducer().equals(this.producerService.findByPrincipal()), "Couldn`t edit this cinematicEntity");

		return result;
	}

	public void flush() {
		this.cinematicEntityRepository.flush();
	}

	// Other CRUD methods ----------------------------------

	public List<CinematicEntity> searchCinematicEntity(final String s) {
		List<CinematicEntity> resultCinematicEntities;

		resultCinematicEntities = this.cinematicEntityRepository.searchCinematicEntity(s);

		return resultCinematicEntities;
	}

	public List<Content> getContents(final int cinematicEntityId) {
		List<Content> resultContents;

		resultContents = this.cinematicEntityRepository.getContents(cinematicEntityId);

		return resultContents;
	}

	public Collection<CinematicEntity> findAllProducer() {
		Collection<CinematicEntity> result;

		result = this.cinematicEntityRepository.findAllProducer(this.producerService.findByPrincipal().getId());
		Assert.notNull(result);

		return result;

	}

}
