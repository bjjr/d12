
package controllers.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProducerService;
import controllers.AbstractController;
import domain.Producer;

@Controller
@RequestMapping("/producer/producer")
public class ProducerProducerController extends AbstractController {

	// Services -----------------------------------------------

	@Autowired
	private ProducerService	producerService;


	// Constructors -------------------------------------------

	private ProducerProducerController() {
		super();
	}

	// Edition -----------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Producer producer;

		producer = this.producerService.findByPrincipal();
		result = this.createEditModelAndView(producer);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Producer producer, final BindingResult binding) {
		ModelAndView result;
		Producer producerReconstructed;

		producerReconstructed = this.producerService.reconstruct(producer, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(producerReconstructed);
		else
			try {
				this.producerService.save(producerReconstructed);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final IllegalArgumentException oops) {
				result = this.createEditModelAndView(producerReconstructed, "misc.commit.error");
			}

		return result;
	}

	// Ancillary methods -------------------------------------

	protected ModelAndView createEditModelAndView(final Producer producer) {
		ModelAndView result;

		result = this.createEditModelAndView(producer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Producer producer, final String message) {
		ModelAndView result;

		result = new ModelAndView("producer/edit");
		result.addObject("producer", producer);
		result.addObject("message", message);

		return result;
	}

}
