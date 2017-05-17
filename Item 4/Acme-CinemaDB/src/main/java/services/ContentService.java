
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ContentRepository;
import domain.Content;

@Service
@Transactional
public class ContentService {

	// Managed repository -----------------------------------

	@Autowired
	private ContentRepository	contentRepository;


	// Constructors -----------------------------------------

	public ContentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Collection<Content> findAll() {
		Collection<Content> result;

		result = this.contentRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Content findOne(final int contentId) {
		Assert.notNull(contentId);
		Assert.isTrue(contentId != 0);

		Content result;

		result = this.contentRepository.findOne(contentId);
		Assert.notNull(result);

		return result;
	}

	// Other CRUD methods ----------------------------------

	public List<Content> searchContent(final String s) {
		List<Content> resultContent;

		resultContent = this.contentRepository.searchContent(s);

		return resultContent;
	}

}
