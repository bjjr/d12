
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.LikeUserService;
import domain.LikeUser;

@RequestMapping("/likeUser")
@Controller
public class LikeUserController extends AbstractController {

	@Autowired
	private LikeUserService	likeUserService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView view = new ModelAndView("likeUser/list");

		final Collection<LikeUser> allLikeUser = this.likeUserService.findAll();

		view.addObject("likeUser", allLikeUser);
		view.addObject("requestURI", "/likeUser/list.do");

		return view;
	}
}
