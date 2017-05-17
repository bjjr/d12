
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CriticService;
import services.ReviewService;
import domain.Actor;
import domain.Critic;
import domain.Review;

@Controller
@RequestMapping("/review")
public class ReviewController extends AbstractController {

	@Autowired
	private ReviewService	reviewService;

	@Autowired
	private CriticService	criticService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int contentId) {
		final ModelAndView res;
		final Collection<Review> reviews;
		Critic critic;

		try {
			critic = this.criticService.findByPrincipal();
		} catch (final Exception ignored) {
			critic = null;
		}

		reviews = this.reviewService.findReviewsByContentId(contentId);
		Assert.notNull(reviews);

		res = new ModelAndView("review/list");
		res.addObject("requestURI", "review/list.do");
		res.addObject("reviews", reviews);
		res.addObject("contentId", contentId);
		
		if (critic != null)
			res.addObject("criticId", critic.getId());
		return res;
	}
}
