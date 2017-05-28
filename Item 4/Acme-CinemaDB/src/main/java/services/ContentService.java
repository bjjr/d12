
package services;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

import javax.validation.Payload;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.internal.constraintvalidators.URLValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

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

	public Collection<Content> findContentByProducerId(final int producerId) {
		return this.contentRepository.findContentByProducerId(producerId);
	}

	public List<Content> searchContent(final String s) {
		List<Content> resultContent;

		resultContent = this.contentRepository.searchContent(s);

		return resultContent;
	}

	/**
	 * Checks if the specified URLs are valid Youtube URLs
	 * 
	 * @param paths
	 *            The URLs to be checked
	 * @param binding
	 *            The BindingResult used in the form to inject error messages if necessary
	 */

	private void checkURLs(final Collection<String> paths, final BindingResult binding) {
		URLValidator urlValidator;

		urlValidator = new URLValidator();

		urlValidator.initialize(new URL() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}

			@Override
			public String regexp() {
				return "^https?:\\/\\/www\\.youtube\\.com\\/watch\\?v=[^= &?/\\r\\n]{8,11}$"; // Matches Youtube URLs
			}

			@Override
			public String protocol() {
				return null;
			}

			@Override
			public int port() {
				return -1;
			}

			@Override
			public Class<? extends Payload>[] payload() {
				return null;
			}

			@Override
			public String message() {
				return "content.video.invalid";
			}

			@Override
			public String host() {
				return null;
			}

			@Override
			public Class<?>[] groups() {
				return null;
			}

			@Override
			public Flag[] flags() {
				return null;
			}
		});

		for (final String s : paths)
			if (!urlValidator.isValid(s, null)) {
				binding.rejectValue("videos", "content.video.invalid");
				break;
			}
	}

	/**
	 * Given a Youtube URL this method returns the videoID.
	 * 
	 * @param url
	 *            A valid Youtube URL
	 * @return The videoID to be stored in the DB.
	 */

	private String getYoutubeVideoId(final String url) {
		String res;
		String[] fragments;

		fragments = url.split("="); // Splits the url. There is only one "=" character, the right part after the split is the videoID.
		res = fragments[1]; // Get the second part of the spliced URL, which is the videoID.

		return res;
	}
}
