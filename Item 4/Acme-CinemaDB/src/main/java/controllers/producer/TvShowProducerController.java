
package controllers.producer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import services.CinematicEntityService;
import services.TvShowService;
import domain.CinematicEntity;
import domain.TvShow;
import forms.MovieForm;

@Controller
@RequestMapping("/tvShow")
public class TvShowProducerController {

	// Services

	@Autowired
	private TvShowService			tvShowService;

	@Autowired
	private CinematicEntityService	cinematicEntityService;


	// Constructors

	public TvShowProducerController() {
		super();
	}

	@RequestMapping(value = "/producer/list", method = RequestMethod.GET)
	public ModelAndView listProducer() {
		ModelAndView result;
		Collection<TvShow> tvShows;
		final Boolean isTv = true;

		tvShows = this.tvShowService.findAllProducer();

		result = new ModelAndView("tvshow/producer/list");
		result.addObject("requestURI", "tvShow/producer/list.do");
		result.addObject("movies", tvShows);
		result.addObject("isTv", isTv);
		result.addObject("createURL", "tvShow/producer/create.do");

		return result;
	}

	@RequestMapping(value = "/producer/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		TvShow tvShow;
		MovieForm tvShowForm;

		tvShow = this.tvShowService.create();

		tvShowForm = new MovieForm(tvShow);

		res = this.createEditModelAndView(tvShowForm);

		return res;
	}

	@RequestMapping(value = "/producer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int movieId) {
		ModelAndView res;
		TvShow tvShow;
		MovieForm tvShowForm;

		tvShow = this.tvShowService.findOneEdit(movieId);

		tvShowForm = new MovieForm(tvShow);

		res = this.createEditModelAndView(tvShowForm);

		return res;
	}

	@RequestMapping(value = "/producer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final MovieForm tvShowForm, final BindingResult binding) {
		ModelAndView res;
		TvShow reconstructed;

		reconstructed = this.tvShowService.reconstruct(tvShowForm, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(tvShowForm);
		else
			try {
				this.tvShowService.save(reconstructed);
				res = new ModelAndView("redirect:/tvShow/producer/list.do");
			} catch (final Throwable e) {
				res = this.createEditModelAndView(tvShowForm, "misc.commit.error");
			}

		return res;

	}

	//	@RequestMapping(value = "/producer/edit", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(final MovieForm tvShowForm, final BindingResult binding) {
	//		ModelAndView result;
	//		TvShow tvShow;
	//
	//		tvShow = this.tvShowService.findOneEdit(tvShowForm.getId());
	//
	//		try {
	//			this.tvShowService.delete(tvShow);
	//			result = new ModelAndView("redirect:list.do");
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(tvShowForm, "misc.commit.error");
	//		}
	//
	//		return result;
	//	}

	@RequestMapping(value = "/producer/addCinematicEntities", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam final int tvShowId) {
		ModelAndView res;
		MovieForm movieForm;
		final Map<Integer, String> idList = new HashMap<>();
		final Collection<CinematicEntity> cinematicEntities = this.cinematicEntityService.findAll();

		cinematicEntities.removeAll(this.tvShowService.findOneEdit(tvShowId).getCinematicEntities());

		for (final CinematicEntity ce : cinematicEntities)
			idList.put(ce.getId(), ce.getName());

		movieForm = new MovieForm();

		movieForm.setId(tvShowId);

		res = new ModelAndView("content/producer/addCinematicEntities");
		res.addObject("movieForm", movieForm);
		res.addObject("action", "tvShow/producer/addCinematicEntities.do");
		res.addObject("listURL", "content/display.do?contentId=" + movieForm.getId());
		res.addObject("idList", idList);

		return res;
	}
	@RequestMapping(value = "/producer/addCinematicEntities", method = RequestMethod.POST, params = "save")
	public ModelAndView addSave(final MovieForm movieForm, final BindingResult binding) {
		ModelAndView res;

		try {
			this.tvShowService.addCinematicEntity(movieForm.getId(), movieForm.getGenres());
			res = new ModelAndView("redirect:/content/display.do?contentId=" + movieForm.getId());
		} catch (final Throwable e) {
			res = new ModelAndView();
			res.setView(new RedirectView("addCinematicEntities.do?tvShowId=" + movieForm.getId()));
			res.addObject("message", "misc.commit.error");
		}

		return res;
	}

	// Ancillary methods ---------------------

	protected ModelAndView createEditModelAndView(final MovieForm tvShowForm) {
		ModelAndView res;
		res = this.createEditModelAndView(tvShowForm, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final MovieForm tvShowForm, final String message) {
		ModelAndView res;

		res = new ModelAndView("tvshow/producer/edit");
		res.addObject("movieForm", tvShowForm);
		res.addObject("message", message);
		res.addObject("action", "tvShow/producer/edit.do");
		res.addObject("listURL", "tvShow/producer/list.do");
		res.addObject("createURL", "tvShow/producer/create.do");

		return res;
	}
}
