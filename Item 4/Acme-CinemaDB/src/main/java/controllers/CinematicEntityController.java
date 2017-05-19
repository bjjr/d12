
package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CinematicEntityService;
import domain.CinematicEntity;
import domain.Content;

@Controller
@RequestMapping("/cinematicEntity")
public class CinematicEntityController {

	// Services

	@Autowired
	private CinematicEntityService	cinematicEntityService;


	// Constructors

	public CinematicEntityController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) final int cinematicEntityId) {
		ModelAndView result;
		CinematicEntity cinematicEntity;
		List<Content> contents;

		cinematicEntity = this.cinematicEntityService.findOne(cinematicEntityId);
		contents = this.cinematicEntityService.getContents(cinematicEntityId);

		result = new ModelAndView("cinematicEntity/display");
		result.addObject("requestURI", "cinematicEntity/display.do");
		result.addObject("cinematicEntity", cinematicEntity);
		result.addObject("contents", contents);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<CinematicEntity> cinematicEntities;

		cinematicEntities = this.cinematicEntityService.findAll();

		result = new ModelAndView("cinematicEntity/list");
		result.addObject("requestURI", "cinematicEntity/list.do");
		result.addObject("cinematicEntities", cinematicEntities);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@RequestParam final String name) {
		ModelAndView result;
		Collection<CinematicEntity> cinematicEntities;

		if (name == "")
			result = new ModelAndView("redirect:list.do");
		else {
			cinematicEntities = this.cinematicEntityService.searchCinematicEntity(name);

			result = new ModelAndView("cinematicEntity/list");
			result.addObject("requestURI", "cinematicEntity/list.do");
			result.addObject("cinematicEntities", cinematicEntities);
		}

		return result;
	}

}
