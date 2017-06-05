
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProducerService;
import controllers.AbstractController;
import domain.Producer;
import forms.ProducerForm;

@Controller
@RequestMapping("/producer/administrator")
public class ProducerAdministratorController extends AbstractController {

	// Services -----------------------------------------------

	@Autowired
	private ProducerService	producerService;


	// Constructors -------------------------------------------

	private ProducerAdministratorController() {
		super();
	}

	// Creating -----------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Producer producer;
		ProducerForm producerForm;

		producer = this.producerService.create();
		producerForm = new ProducerForm(producer);
		result = this.createEditModelAndView(producerForm);

		return result;
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Producer> producers;

		producers = this.producerService.findAll();

		result = new ModelAndView("producer/list");
		result.addObject("producers", producers);
		result.addObject("requestURI", "producer/list.do");

		return result;
	}

	// Saving ------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ProducerForm producerForm, final BindingResult binding) {
		ModelAndView result;
		Producer producer;

		producer = this.producerService.reconstruct(producerForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(producerForm);
		else if (!producer.getUserAccount().getPassword().equals(producerForm.getConfirmPassword()))
			result = this.createEditModelAndView(producerForm, "producer.commit.password");
		else
			try {
				this.producerService.save(producer);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final IllegalArgumentException oops) {
				result = this.createEditModelAndView(producerForm, "misc.commit.error");
			}
		return result;
	}

	// Ancillary methods -------------------------------------

	protected ModelAndView createEditModelAndView(final ProducerForm producerForm) {
		ModelAndView result;

		result = this.createEditModelAndView(producerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProducerForm producerForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("producer/create");
		result.addObject("producerForm", producerForm);
		result.addObject("message", message);

		return result;
	}

}
