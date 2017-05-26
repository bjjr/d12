
package controllers.producer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.GenreService;
import services.TvShowService;
import domain.TvShow;
import forms.MovieForm;

@Controller
@RequestMapping("/tvShow")
public class TvShowProducerController {

	// Services

	@Autowired
	private TvShowService	tvShowService;

	@Autowired
	private GenreService	genreService;


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

		result = new ModelAndView("movie/producer/list");
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
	// Ancillary methods ---------------------

	protected ModelAndView createEditModelAndView(final MovieForm tvShowForm) {
		ModelAndView res;
		res = this.createEditModelAndView(tvShowForm, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final MovieForm tvShowForm, final String message) {
		ModelAndView res;
		final Boolean isTv = true;

		res = new ModelAndView("movie/producer/edit");
		res.addObject("movieForm", tvShowForm);
		res.addObject("message", message);
		res.addObject("action", "tvShow/producer/edit.do");
		res.addObject("listURL", "tvShow/producer/list.do");
		res.addObject("createURL", "tvShow/producer/create.do");

		return res;
	}
}
