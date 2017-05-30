
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
import services.MovieService;
import domain.CinematicEntity;
import domain.Movie;
import forms.MovieForm;

@Controller
@RequestMapping("/movie")
public class MovieProducerController {

	// Services

	@Autowired
	private MovieService			movieService;

	@Autowired
	private CinematicEntityService	cinematicEntityService;


	// Constructors

	public MovieProducerController() {
		super();
	}

	@RequestMapping(value = "/producer/list", method = RequestMethod.GET)
	public ModelAndView listProducer() {
		ModelAndView result;
		Collection<Movie> movies;

		movies = this.movieService.findAllProducer();

		result = new ModelAndView("movie/producer/list");
		result.addObject("requestURI", "movie/producer/list.do");
		result.addObject("movies", movies);
		result.addObject("createURL", "movie/producer/create.do");

		return result;
	}

	@RequestMapping(value = "/producer/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Movie movie;
		MovieForm movieForm;

		movie = this.movieService.create();

		movieForm = new MovieForm(movie);

		res = this.createEditModelAndView(movieForm);

		return res;
	}

	@RequestMapping(value = "/producer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int movieId) {
		ModelAndView res;
		Movie movie;
		MovieForm movieForm;

		movie = this.movieService.findOneEdit(movieId);

		movieForm = new MovieForm(movie);

		res = this.createEditModelAndView(movieForm);

		return res;
	}

	@RequestMapping(value = "/producer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final MovieForm movieForm, final BindingResult binding) {
		ModelAndView res;
		Movie reconstructed;

		reconstructed = this.movieService.reconstruct(movieForm, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(movieForm);
		else
			try {
				this.movieService.save(reconstructed);
				res = new ModelAndView("redirect:/movie/producer/list.do");
			} catch (final Throwable e) {
				res = this.createEditModelAndView(movieForm, "misc.commit.error");
			}

		return res;

	}

	//	@RequestMapping(value = "/producer/edit", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(final MovieForm movieForm, final BindingResult binding) {
	//		ModelAndView result;
	//		Movie movie;
	//
	//		movie = this.movieService.findOneEdit(movieForm.getId());
	//		;
	//
	//		try {
	//			this.movieService.delete(movie);
	//			result = new ModelAndView("redirect:list.do");
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(movieForm, "misc.commit.error");
	//		}
	//
	//		return result;
	//	}

	@RequestMapping(value = "/producer/addCinematicEntities", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam final int movieId) {
		ModelAndView res;
		MovieForm movieForm;
		final Map<Integer, String> idList = new HashMap<>();
		final Collection<CinematicEntity> cinematicEntities = this.cinematicEntityService.findAll();

		cinematicEntities.removeAll(this.movieService.findOneEdit(movieId).getCinematicEntities());

		for (final CinematicEntity ce : cinematicEntities)
			idList.put(ce.getId(), ce.getName());

		movieForm = new MovieForm();

		movieForm.setId(movieId);

		res = new ModelAndView("content/producer/addCinematicEntities");
		res.addObject("movieForm", movieForm);
		res.addObject("action", "movie/producer/addCinematicEntities.do");
		res.addObject("listURL", "content/display.do?contentId=" + movieForm.getId());
		res.addObject("idList", idList);

		return res;
	}
	@RequestMapping(value = "/producer/addCinematicEntities", method = RequestMethod.POST, params = "save")
	public ModelAndView addSave(final MovieForm movieForm, final BindingResult binding) {
		ModelAndView res;

		try {
			this.movieService.addCinematicEntity(movieForm.getId(), movieForm.getGenres());
			res = new ModelAndView("redirect:/content/display.do?contentId=" + movieForm.getId());
		} catch (final Throwable e) {
			res = new ModelAndView();
			res.setView(new RedirectView("addCinematicEntities.do?movieId=" + movieForm.getId()));
			res.addObject("message", "misc.commit.error");
		}

		return res;
	}
	// Ancillary methods ---------------------

	protected ModelAndView createEditModelAndView(final MovieForm movieForm) {
		ModelAndView res;
		res = this.createEditModelAndView(movieForm, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final MovieForm movieForm, final String message) {
		ModelAndView res;

		res = new ModelAndView("movie/producer/edit");
		res.addObject("movieForm", movieForm);
		res.addObject("message", message);
		res.addObject("action", "movie/producer/edit.do");
		res.addObject("listURL", "movie/producer/list.do");
		res.addObject("createURL", "movie/producer/create.do");

		return res;
	}
}
