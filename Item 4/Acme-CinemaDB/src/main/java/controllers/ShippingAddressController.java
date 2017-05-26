
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ShippingAddressService;
import domain.ShippingAddress;

@Controller
@RequestMapping("/shippingAddress")
public class ShippingAddressController extends AbstractController {

	// Services -------------------------------------

	@Autowired
	private ShippingAddressService	shippingAddressService;


	// List -----------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<ShippingAddress> addresses;

		addresses = this.shippingAddressService.findShippingAddressesByUser();

		res = new ModelAndView("shippingAddress/list");
		res.addObject("shippingAddresses", addresses);

		return res;
	}

	// Display --------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int shippingAddressId) {
		ModelAndView res;
		ShippingAddress shippingAddress;

		shippingAddress = this.shippingAddressService.findOne(shippingAddressId);

		res = new ModelAndView("shippingAddress/display");
		res.addObject("sa", shippingAddress);

		return res;
	}

	// Create ---------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		ShippingAddress shippingAddress;

		shippingAddress = this.shippingAddressService.create();

		res = this.createEditModelAndView(shippingAddress);

		return res;
	}

	// Save -----------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ShippingAddress shippingAddress, final BindingResult binding) {
		ModelAndView res;
		ShippingAddress reconstructed;

		reconstructed = this.shippingAddressService.reconstruct(shippingAddress, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(shippingAddress);
		else
			try {
				this.shippingAddressService.save(reconstructed);
				res = new ModelAndView("redirect:list.do");
				res.addObject("message", "sa.saved");
			} catch (final Throwable th) {
				res = this.createEditModelAndView(shippingAddress, "misc.commit.error");
			}

		return res;
	}

	// Delete ---------------------------------------

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int shippingAddressId) {
		ModelAndView res;
		ShippingAddress shippingAddress;

		shippingAddress = this.shippingAddressService.findOne(shippingAddressId);
		this.shippingAddressService.delete(shippingAddress);

		res = new ModelAndView("redirect:list.do");

		return res;
	}

	// Ancillary methods ----------------------------

	protected ModelAndView createEditModelAndView(final ShippingAddress shippingAddress) {
		ModelAndView res;

		res = this.createEditModelAndView(shippingAddress, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final ShippingAddress shippingAddress, final String message) {
		ModelAndView res;

		res = new ModelAndView("shippingAddress/edit");

		res.addObject("shippingAddress", shippingAddress);
		res.addObject("message", message);

		return res;
	}

}
