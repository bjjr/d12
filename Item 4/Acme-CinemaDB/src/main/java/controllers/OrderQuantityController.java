
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.OrderQuantityService;
import services.OrderUserService;
import domain.OrderQuantity;
import domain.OrderUser;

@Controller
@RequestMapping("/orderQuantity")
public class OrderQuantityController extends AbstractController {

	// Supporting services --------------------------

	@Autowired
	private OrderQuantityService	orderQuantityService;

	@Autowired
	private OrderUserService		orderUserService;


	// Edit -----------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int orderQuantityId) {
		ModelAndView res;
		OrderQuantity orderQuantity;

		orderQuantity = this.orderQuantityService.findOne(orderQuantityId);

		res = this.createEditModelAndView(orderQuantity);

		return res;
	}

	// Save -----------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final OrderQuantity orderQuantity, final BindingResult binding) {
		ModelAndView res;
		OrderQuantity reconstructed;

		reconstructed = this.orderQuantityService.reconstruct(orderQuantity, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(orderQuantity);
		else
			try {
				OrderUser unfinishedOrder;

				this.orderQuantityService.save(reconstructed);
				unfinishedOrder = this.orderUserService.findUnfinishedOrder();
				this.orderUserService.updateTotal(unfinishedOrder);
				res = new ModelAndView("redirect:/orderUser/display.do?orderUserId=" + unfinishedOrder.getId());
			} catch (final Throwable th) {
				res = this.createEditModelAndView(orderQuantity, "misc.commit.error");
			}

		return res;
	}

	// Ancillary methods ----------------------------

	protected ModelAndView createEditModelAndView(final OrderQuantity orderQuantity) {
		ModelAndView res;

		res = this.createEditModelAndView(orderQuantity, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final OrderQuantity orderQuantity, final String message) {
		ModelAndView res;

		res = new ModelAndView("orderQuantity/edit");
		res.addObject("orderQuantity", orderQuantity);
		res.addObject("message", message);

		return res;
	}

}
