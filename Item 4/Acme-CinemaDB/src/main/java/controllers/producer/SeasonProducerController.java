
package controllers.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SeasonService;
import domain.Season;

@Controller
@RequestMapping("/season")
public class SeasonProducerController {

	// Services

	@Autowired
	private SeasonService	seasonService;


	// Constructors

	public SeasonProducerController() {
		super();
	}

	@RequestMapping(value = "/producer/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = true) final int tvShowId) {
		ModelAndView res;
		Season season;

		season = this.seasonService.create(tvShowId);

		res = this.createEditModelAndView(season);

		return res;
	}

	@RequestMapping(value = "/producer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int seasonId) {
		ModelAndView res;
		Season season;

		season = this.seasonService.findOneEdit(seasonId);

		res = this.createEditModelAndView(season);

		return res;
	}

	@RequestMapping(value = "/producer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Season season, final BindingResult binding) {
		ModelAndView res;
		Season reconstructed;

		reconstructed = this.seasonService.reconstruct(season, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(season);
		else
			try {
				this.seasonService.save(reconstructed);
				res = new ModelAndView("redirect:/content/display.do?contentId=" + reconstructed.getTvShow().getId());
			} catch (final Throwable e) {
				res = this.createEditModelAndView(season, "misc.commit.error");
			}

		return res;

	}

	// Ancillary methods ---------------------

	protected ModelAndView createEditModelAndView(final Season season) {
		ModelAndView res;
		res = this.createEditModelAndView(season, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final Season season, final String message) {
		ModelAndView res;

		res = new ModelAndView("season/producer/edit");
		res.addObject("season", season);
		res.addObject("message", message);
		res.addObject("action", "season/producer/edit.do");

		return res;
	}

}
