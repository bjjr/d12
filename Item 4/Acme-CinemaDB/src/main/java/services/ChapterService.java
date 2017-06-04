
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChapterRepository;
import domain.Chapter;
import domain.Producer;
import forms.MovieForm;

@Service
@Transactional
public class ChapterService {

	// Managed repository -----------------------------------

	@Autowired
	private ChapterRepository	chapterRepository;

	@Autowired
	private ProducerService		producerService;

	@Autowired
	private SeasonService		seasonService;

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------

	public ChapterService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Chapter create(final int seasonId) {
		Chapter res;

		res = new Chapter();

		res.setSeason(this.seasonService.findOneEdit(seasonId));

		return res;
	}
	public Chapter save(final Chapter chapter) {
		Chapter res;

		res = this.chapterRepository.save(chapter);

		return res;
	}

	public Chapter reconstruct(final MovieForm chapterForm, final BindingResult binding) {
		return null;
	}

	//	public void delete(final Chapter chapter) {
	//
	//		Assert.notNull(chapter);
	//
	//		Assert.isTrue(chapter.getId() != 0);
	//
	//		Assert.isTrue(this.chapterRepository.exists(chapter.getId()));
	//
	//		this.chapterRepository.delete(chapter);
	//
	//	}

	public Chapter reconstruct(final Chapter chapter, final BindingResult binding) {
		Chapter aux;

		if (chapter.getId() != 0) {
			aux = this.findOneEdit(chapter.getId());
			chapter.setVersion(aux.getVersion());
			chapter.setSeason(aux.getSeason());
		} else {
			Producer producer;

			producer = this.producerService.findByPrincipal();

			Assert.isTrue(chapter.getSeason().getTvShow().getProducer().getId() == producer.getId(), "Couldn`t create this chapter");
		}

		this.validator.validate(chapter, binding);

		return chapter;
	}

	public Collection<Chapter> findAll() {
		Collection<Chapter> result;

		result = this.chapterRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Chapter findOne(final int chapterId) {
		Assert.notNull(chapterId);
		Assert.isTrue(chapterId != 0);

		Chapter result;

		result = this.chapterRepository.findOne(chapterId);
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.chapterRepository.flush();
	}

	// Other CRUD methods ----------------------------------

	public Chapter findOneEdit(final int chapterId) {
		Assert.notNull(chapterId);
		Assert.isTrue(chapterId != 0);

		Producer producer;
		Chapter result;

		result = this.chapterRepository.findOne(chapterId);
		Assert.notNull(result);

		producer = this.producerService.findByPrincipal();

		Assert.isTrue(result.getSeason().getTvShow().getProducer().getId() == producer.getId(), "Couldn`t edit this season");

		return result;
	}

}
