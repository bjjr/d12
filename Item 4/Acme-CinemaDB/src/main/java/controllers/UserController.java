
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.LikeUserService;
import services.SocialIdentityService;
import services.UserService;
import domain.LikeUser;
import domain.SocialIdentity;
import domain.User;
import forms.ActorForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Constructor ----------------------------------

	public UserController() {
		super();
	}


	// Supporting services --------------------------

	@Autowired
	private UserService				userService;

	@Autowired
	private LikeUserService			likeUserService;

	@Autowired
	private SocialIdentityService	socialIdentityService;

	@Autowired
	private ActorService			actorService;


	// Registration ---------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		ActorForm actorForm;

		actorForm = new ActorForm();
		res = this.createEditModelAndView(actorForm);

		return res;
	}

	// Save -----------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ActorForm actorForm, final BindingResult binding) {
		ModelAndView res;
		User reconstructed;

		reconstructed = this.userService.reconstruct(actorForm, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(actorForm);
		else
			try {
				this.userService.save(reconstructed);
				res = new ModelAndView("redirect:/");
				res.addObject("conf", "misc.registration.ok");
			} catch (final Throwable th) {
				res = this.createEditModelAndView(actorForm, "misc.commit.error");
			}

		return res;
	}

	// Display --------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int userId) {
		ModelAndView res;
		User user;
		Collection<LikeUser> contentLikes, cEntitiesLikes;
		Collection<SocialIdentity> socialIds;

		user = this.userService.findOne(userId);
		contentLikes = this.likeUserService.findContentLikes(userId);
		cEntitiesLikes = this.likeUserService.findCinematicEntityLikes(userId);
		socialIds = this.socialIdentityService.findUserSocialIdentities(userId);

		res = new ModelAndView("user/display");

		res.addObject("user", user);
		res.addObject("contentLikes", contentLikes);
		res.addObject("cEntitiesLikes", cEntitiesLikes);
		res.addObject("socialIds", socialIds);

		return res;
	}

	// List -----------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<User> users;

		users = this.userService.findAll();

		if (this.actorService.checkAuthority(Authority.USER)) {
			User principal;

			principal = this.userService.findByPrincipal();

			users.remove(principal);
		}

		res = new ModelAndView("user/list");
		res.addObject("users", users);

		return res;
	}

	// Ancillary methods ----------------------------

	protected ModelAndView createEditModelAndView(final ActorForm actorForm) {
		final ModelAndView res = this.createEditModelAndView(actorForm, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final ActorForm actorForm, final String message) {
		ModelAndView res;

		res = new ModelAndView("user/edit");
		res.addObject("action", "user/edit.do");
		res.addObject("actorForm", actorForm);
		res.addObject("message", message);

		return res;
	}
}
