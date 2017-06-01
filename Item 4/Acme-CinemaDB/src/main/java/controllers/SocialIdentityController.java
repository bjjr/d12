
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SocialIdentityService;
import services.UserService;
import domain.SocialIdentity;
import domain.User;

@Controller
@RequestMapping("/socialIdentity")
public class SocialIdentityController extends AbstractController {

	// Services -----------------------------------------------

	@Autowired
	private SocialIdentityService	socialIdentityService;

	@Autowired
	private UserService				userService;


	// Constructors -------------------------------------------

	public SocialIdentityController() {
		super();
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<SocialIdentity> socialIdentities;
		User user;

		user = this.userService.findByPrincipal();
		socialIdentities = this.socialIdentityService.findUserSocialIdentities(user.getId());

		result = new ModelAndView("socialIdentity/list");
		result.addObject("socialIdentities", socialIdentities);
		result.addObject("requestURI", "socialIdentity/list.do");

		return result;
	}

	// Creating -----------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialIdentity socialIdentity;

		socialIdentity = this.socialIdentityService.create();
		result = this.createEditModelAndView(socialIdentity);

		return result;
	}

	// Edition -----------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialIdentityId) {
		ModelAndView result;
		SocialIdentity socialIdentity;

		socialIdentity = this.socialIdentityService.findOne(socialIdentityId);
		Assert.notNull(socialIdentity);
		result = this.createEditModelAndView(socialIdentity);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(SocialIdentity socialIdentity, final BindingResult binding) {
		ModelAndView result;

		socialIdentity = this.socialIdentityService.reconstruct(socialIdentity, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialIdentity);
		else
			try {
				this.socialIdentityService.save(socialIdentity);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialIdentity socialIdentity, final BindingResult binding) {
		ModelAndView result;

		try {
			this.socialIdentityService.delete(socialIdentity);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
		}

		return result;
	}

	// Ancillary methods -------------------------------------

	protected ModelAndView createEditModelAndView(final SocialIdentity socialIdentity) {
		ModelAndView result;

		result = this.createEditModelAndView(socialIdentity, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialIdentity socialIdentity, final String message) {
		ModelAndView result;
		Collection<SocialIdentity> socialIdentities;

		socialIdentities = this.socialIdentityService.findAll();

		result = new ModelAndView("socialIdentity/edit");
		result.addObject("socialIdentity", socialIdentity);
		result.addObject("socialIdentities", socialIdentities);
		result.addObject("message", message);

		return result;
	}
}
