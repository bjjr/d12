
package controllers.critic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CriticService;
import controllers.AbstractController;
import domain.Critic;

@Controller
@RequestMapping("/critic/critic")
public class CriticCriticController extends AbstractController {

	@Autowired
	private CriticService	criticService;


	// Constructors ---------------------------

	public CriticCriticController() {
		super();
	}

	// Registration ---------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView res;
		final Critic critic;

		critic = this.criticService.findByPrincipal();
		Assert.notNull(critic);

		res = this.createEditModelAndView(critic);

		return res;
	}
	// Save ----------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Critic critic, final BindingResult binding) {
		ModelAndView res;
		Critic reconstructed;

		reconstructed = this.criticService.reconstruct(critic, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(critic);
		else
			try {
				this.criticService.save(reconstructed);
				res = this.createEditModelAndView(critic, "critic.updated.ok");
			} catch (final Throwable e) {
				res = this.createEditModelAndView(critic, "misc.commit.error");
			}

		return res;

	}
	// Ancillary methods ---------------------

	protected ModelAndView createEditModelAndView(final Critic critic) {
		final ModelAndView res = this.createEditModelAndView(critic, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final Critic critic, final String message) {
		final ModelAndView res = new ModelAndView("critic/edit");

		res.addObject("action", "critic/critic/edit.do");
		res.addObject("modelAttribute", "critic");
		res.addObject("critic", critic);
		res.addObject("message", message);
		res.addObject("isEdit", true);

		return res;
	}
}
