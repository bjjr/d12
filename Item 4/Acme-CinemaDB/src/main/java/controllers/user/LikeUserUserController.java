
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LikeUserService;
import services.UserService;
import controllers.AbstractController;
import domain.LikeUser;
import domain.User;

@RequestMapping("/likeUser/user")
@Controller
public class LikeUserUserController extends AbstractController {

	@Autowired
	private LikeUserService	likeUserService;

	@Autowired
	private UserService		userService;


	@RequestMapping(value = "/listComments", method = RequestMethod.GET)
	public ModelAndView listComments(@RequestParam final int assessableEntityId) {
		final User currentUser = this.userService.findByPrincipal();

		final ModelAndView view = new ModelAndView("likeUser/listComments");

		final Collection<LikeUser> allCommentsByAssessableEntity = this.likeUserService.findCommentsByAssessableEntity(assessableEntityId);

		view.addObject("likeUser", allCommentsByAssessableEntity);
		view.addObject("currentUserId", currentUser.getId());
		view.addObject("requestURI", "/likeUser/user/listComments.do?assessableEntityId=" + assessableEntityId);

		return view;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int assessableEntityId) {

		final LikeUser likeUser = this.likeUserService.create(assessableEntityId);

		final ModelAndView view = this.createEditModelAndView(likeUser);

		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int assessableEntityId) {

		final LikeUser likeUser = this.likeUserService.findOne(assessableEntityId);
		Assert.notNull(likeUser);

		final ModelAndView view = this.createEditModelAndView(likeUser);

		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final LikeUser likeUser, final BindingResult bindingResult) {
		ModelAndView result;

		final LikeUser likeUserReconstructed = this.likeUserService.reconstruct(likeUser, bindingResult);

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(likeUserReconstructed);
		else
			try {
				this.likeUserService.save(likeUserReconstructed);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable e) {
				System.out.println(e.toString());
				result = this.createEditModelAndView(likeUserReconstructed, "misc.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final LikeUser likeUser, final BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.likeUserService.delete(likeUser);
			result = new ModelAndView("redirect:/");
		} catch (final Throwable e) {
			result = this.createEditModelAndView(likeUser, "misc.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final LikeUser likeUser) {
		ModelAndView res;

		res = this.createEditModelAndView(likeUser, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final LikeUser likeUser, final String message) {
		ModelAndView res;

		res = new ModelAndView("likeUser/edit");

		res.addObject("likeUser", likeUser);
		res.addObject("message", message);

		return res;
	}

}
