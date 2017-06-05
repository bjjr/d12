
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CriticService;
import controllers.AbstractController;
import domain.Critic;
import forms.CriticForm;

@Controller
@RequestMapping("/critic/administrator")
public class CriticAdministratorController extends AbstractController {

	@Autowired
	private CriticService	criticService;


	// Constructors ---------------------------

	public CriticAdministratorController() {
		super();
	}

	// Registration ---------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final CriticForm criticForm = new CriticForm();

		res = this.createEditModelAndView(criticForm);

		return res;
	}

	// Save ----------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final CriticForm criticForm, final BindingResult binding) {
		ModelAndView res;
		Critic reconstructed;

		reconstructed = this.criticService.reconstruct(criticForm, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(criticForm);
		else
			try {
				this.criticService.save(reconstructed);
				res = new ModelAndView("redirect:/");
				res.addObject("conf", "critic.created.ok");
			} catch (final Throwable e) {
				res = this.createEditModelAndView(criticForm, "misc.commit.error");
			}

		return res;

	}
	// Ancillary methods ---------------------

	protected ModelAndView createEditModelAndView(final CriticForm criticForm) {
		final ModelAndView res = this.createEditModelAndView(criticForm, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final CriticForm criticForm, final String message) {
		final ModelAndView res = new ModelAndView("critic/register");

		res.addObject("action", "critic/administrator/register.do");
		res.addObject("modelAttribute", "criticForm");
		res.addObject("criticForm", criticForm);
		res.addObject("message", message);
		res.addObject("isEdit", false);

		return res;
	}
}
