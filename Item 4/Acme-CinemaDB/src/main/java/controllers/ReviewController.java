
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReviewService;
import domain.Review;

@Controller
@RequestMapping("/review")
public class ReviewController extends AbstractController {

	@Autowired
	private ReviewService	reviewService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int contentId) {
		final ModelAndView res;
		Collection<Review> reviews;

		reviews = this.reviewService.findReviewsByContentId(contentId);
		Assert.notNull(reviews);

		res = new ModelAndView("review/list");
		res.addObject("requestURI", "review/list.do");
		res.addObject("reviews", reviews);

		return res;
	}
}
