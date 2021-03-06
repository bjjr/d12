
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.LikeUserService;
import services.UserService;
import domain.LikeUser;

@RequestMapping("/likeUser")
@Controller
public class LikeUserController extends AbstractController {

	@Autowired
	private LikeUserService	likeUserService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private UserService		userService;


	@RequestMapping(value = "/listComments", method = RequestMethod.GET)
	public ModelAndView listComments(@RequestParam final int assessableEntityId) {

		Integer currentUserId = null;

		if (this.actorService.checkAuthority(Authority.USER))
			currentUserId = this.userService.findByPrincipal().getId();

		final ModelAndView view = new ModelAndView("likeUser/listComments");

		final Collection<LikeUser> allCommentsByAssessableEntity = this.likeUserService.findCommentsByAssessableEntity(assessableEntityId);

		view.addObject("likeUser", allCommentsByAssessableEntity);
		view.addObject("currentUserId", currentUserId);
		view.addObject("requestURI", "/likeUser/listComments.do?assessableEntityId=" + assessableEntityId);

		return view;
	}

}
