
package controllers.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChapterService;
import domain.Chapter;

@Controller
@RequestMapping("/chapter")
public class ChapterProducerController {

	// Services

	@Autowired
	private ChapterService	chapterService;


	// Constructors

	public ChapterProducerController() {
		super();
	}

	@RequestMapping(value = "/producer/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = true) final int seasonId) {
		ModelAndView res;
		Chapter chapter;

		chapter = this.chapterService.create(seasonId);

		res = this.createEditModelAndView(chapter);

		return res;
	}

	@RequestMapping(value = "/producer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int chapterId) {
		ModelAndView res;
		Chapter chapter;

		chapter = this.chapterService.findOneEdit(chapterId);

		res = this.createEditModelAndView(chapter);

		return res;
	}

	@RequestMapping(value = "/producer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Chapter chapter, final BindingResult binding) {
		ModelAndView res;
		Chapter reconstructed;

		reconstructed = this.chapterService.reconstruct(chapter, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(chapter);
		else
			try {
				this.chapterService.save(reconstructed);
				res = new ModelAndView("redirect:/season/display.do?seasonId=" + reconstructed.getSeason().getId());
			} catch (final Throwable e) {
				res = this.createEditModelAndView(chapter, "misc.commit.error");
			}

		return res;

	}

	// Ancillary methods ---------------------

	protected ModelAndView createEditModelAndView(final Chapter chapter) {
		ModelAndView res;
		res = this.createEditModelAndView(chapter, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final Chapter chapter, final String message) {
		ModelAndView res;

		res = new ModelAndView("chapter/producer/edit");
		res.addObject("chapter", chapter);
		res.addObject("message", message);
		res.addObject("action", "chapter/producer/edit.do");

		return res;
	}

}
