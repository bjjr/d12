
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ContentRepository;
import domain.Content;

@Service
@Transactional
public class ContentService {

	// Managed repository -----------------------------------

	@Autowired
	private ContentRepository	contentRepository;

	@Autowired
	private TvShowService		tvShowService;

	@Autowired
	private ProducerService		producerService;

	@Autowired
	private Validator			validator;


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

	public Content findOneEdit(final int contentId) {
		Assert.notNull(contentId);
		Assert.isTrue(contentId != 0);

		Content result;

		result = this.contentRepository.findOne(contentId);
		Assert.notNull(result);
		Assert.isTrue(result.getProducer().equals(this.producerService.findByPrincipal()), "Couldn`t edit this content");

		return result;
	}

	// Other CRUD methods ----------------------------------

	public Collection<Content> findAllProducer() {
		Collection<Content> result;

		result = this.contentRepository.findAllProducer(this.producerService.findByPrincipal().getId());
		Assert.notNull(result);

		return result;

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

	public void checkURLs(final Collection<String> paths, final BindingResult binding) {

		final Pattern youtube = Pattern.compile("^https?:\\/\\/www\\.youtube\\.com\\/watch\\?v=[^= &?/\\r\\n]{8,11}$");
		Matcher m;
		for (final String s : paths) {
			m = youtube.matcher(s);
			if (m.matches() == false) {
				binding.rejectValue("videos", "content.video.invalid");
				break;
			}
		}
	}
	/**
	 * Given a Youtube URL this method returns the videoID.
	 * 
	 * @param url
	 *            A valid Youtube URL
	 * @return The videoID to be stored in the DB.
	 */

	public String getYoutubeVideoId(final String url) {
		String res;
		String[] fragments;

		fragments = url.split("="); // Splits the url. There is only one "=" character, the right part after the split is the videoID.
		res = fragments[1]; // Get the second part of the spliced URL, which is the videoID.

		return res;
	}

	public List<String> listYoutubeId(final Collection<String> collection) {
		final List<String> res = new ArrayList<>();

		for (final String string : collection)
			res.add(this.getYoutubeVideoId(string));

		return res;
	}
}
