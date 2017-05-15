
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ContentService;
import domain.Content;

@Controller
@RequestMapping("/content")
public class ContentController {

	// Services

	@Autowired
	private ContentService	contentService;


	// Constructors

	public ContentController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Content> contents;

		contents = this.contentService.findAll();

		result = new ModelAndView("content/list");
		result.addObject("requestURI", "content/list.do");
		result.addObject("contents", contents);

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

}
