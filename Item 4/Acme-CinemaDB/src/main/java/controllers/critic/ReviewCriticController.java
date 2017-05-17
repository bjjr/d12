
package controllers.critic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CriticService;
import services.ReviewService;
import controllers.AbstractController;
import domain.Review;

@Controller
@RequestMapping("/review/critic")
public class ReviewCriticController extends AbstractController {

	private int				contentId;

	@Autowired
	private CriticService	criticService;

	@Autowired
	private ReviewService	reviewService;


	// Constructors ---------------------------

	public ReviewCriticController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int contentId) {
		final ModelAndView res;
		final Review review;

		review = this.reviewService.create(contentId);
		this.contentId = contentId;

		res = this.createEditModelAndView(review);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int reviewId) {
		final ModelAndView res;
		final Review review = this.reviewService.findOne(reviewId);
		this.contentId = review.getContent().getId();
		Assert.notNull(review);
		Assert.isTrue(review.isDraft());

		res = this.createEditModelAndView(review);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveDraft(final Review review, final BindingResult binding) {
		ModelAndView res;
		Review reconstructed;

		reconstructed = this.reviewService.reconstruct(review, this.contentId, true, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(review);
		else
			try {
				this.reviewService.save(reconstructed);
				res = new ModelAndView("redirect:/review/list.do?contentId=" + this.contentId);
			} catch (final Throwable e) {
				res = this.createEditModelAndView(review, "misc.commit.error");
			}

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "publish")
	public ModelAndView savePublish(final Review review, final BindingResult binding) {
		ModelAndView res;
		Review reconstructed;

		reconstructed = this.reviewService.reconstruct(review, this.contentId, false, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(review);
		else
			try {
				this.reviewService.save(reconstructed);
				res = new ModelAndView("redirect:/review/list.do?contentId=" + this.contentId);
			} catch (final Throwable e) {
				res = this.createEditModelAndView(review, "misc.commit.error");
			}

		return res;

	}

	// Ancillary methods ---------------------

	protected ModelAndView createEditModelAndView(final Review review) {
		final ModelAndView res = this.createEditModelAndView(review, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final Review review, final String message) {
		final ModelAndView res = new ModelAndView("review/edit");

		res.addObject("action", "review/critic/edit.do");
		res.addObject("modelAttribute", "review");
		res.addObject("contentId", this.contentId);
		res.addObject("review", review);
		res.addObject("message", message);

		return res;
	}
}
