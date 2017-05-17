
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SeasonService;
import services.TvShowService;
import domain.Chapter;
import domain.Season;

@Controller
@RequestMapping("/season")
public class SeasonController {

	// Services

	@Autowired
	private SeasonService	serviceService;

	@Autowired
	private TvShowService	TvShowService;


	// Constructors

	public SeasonController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) final int seasonId) {
		ModelAndView result;
		Season season;
		List<Chapter> chapters = null;

		season = this.serviceService.findOne(seasonId);
		chapters = this.TvShowService.getChapters(seasonId);

		result = new ModelAndView("season/display");
		result.addObject("requestURI", "season/display.do");
		result.addObject("season", season);
		result.addObject("chapters", chapters);

		return result;
	}
}
