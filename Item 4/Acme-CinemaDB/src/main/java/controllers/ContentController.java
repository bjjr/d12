
package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ContentService;
import services.LikeUserService;
import services.TvShowService;
import domain.Content;
import domain.LikeUser;
import domain.Movie;
import domain.Season;

@Controller
@RequestMapping("/content")
public class ContentController {

	// Services

	@Autowired
	private ContentService	contentService;

	@Autowired
	private TvShowService	TvShowService;

	@Autowired
	private LikeUserService	likeUserService;

	@Autowired
	private ActorService	actorService;


	// Constructors

	public ContentController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Content> contents;
		Collection<LikeUser> allLikeUserByPrincipal = null;

		if (this.actorService.checkAuthority(Authority.USER))
			allLikeUserByPrincipal = this.likeUserService.findAllByPrincipal();

		contents = this.contentService.findAll();

		result = new ModelAndView("content/list");
		result.addObject("requestURI", "content/list.do");
		result.addObject("contents", contents);
		result.addObject("likeUserCurrentUser", allLikeUserByPrincipal);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@RequestParam final String title) {
		ModelAndView result;
		Collection<Content> contents;

		if (title == "")
			result = new ModelAndView("redirect:list.do");
		else {
			contents = this.contentService.searchContent(title);

			result = new ModelAndView("content/list");
			result.addObject("requestURI", "content/list.do");
			result.addObject("contents", contents);
		}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) final int contentId) {
		ModelAndView result;
		Content content;
		List<Season> seasons = null;
		Boolean isMovie = true;

		content = this.contentService.findOne(contentId);

		if (!(content instanceof Movie)) {
			isMovie = false;
			seasons = this.TvShowService.getSeasons(content.getId());
		}

		result = new ModelAndView("content/display");
		result.addObject("requestURI", "content/display.do");
		result.addObject("content", content);
		result.addObject("isMovie", isMovie);
		result.addObject("seasons", seasons);

		return result;
	}
}
