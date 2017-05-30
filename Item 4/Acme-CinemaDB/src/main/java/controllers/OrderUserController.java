/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

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
import services.CreditCardService;
import services.OrderUserService;
import services.ShippingAddressService;
import domain.CreditCard;
import domain.OrderUser;
import domain.ShippingAddress;

@Controller
@RequestMapping("/orderUser")
public class OrderUserController extends AbstractController {

	// Supporting services --------------------------

	@Autowired
	private OrderUserService		orderUserService;

	@Autowired
	private ShippingAddressService	shippingAddressService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ActorService			actorService;


	// Constructors ---------------------------------

	public OrderUserController() {
		super();
	}

	// List -----------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String message, @RequestParam(required = false) final Integer displayCategory) {
		final ModelAndView res;
		Collection<OrderUser> orders;

		orders = null;

		if (this.actorService.checkAuthority(Authority.USER))
			orders = this.orderUserService.findOrdersByUser(displayCategory);
		else if (this.actorService.checkAuthority(Authority.ADMIN))
			orders = this.orderUserService.findOrders(displayCategory);

		res = new ModelAndView("orderUser/list");
		res.addObject("orders", orders);
		res.addObject("message", message);

		return res;
	}
	// Display -------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(defaultValue = "-1") final int orderUserId, @RequestParam(required = false) final String message) {
		ModelAndView res;
		OrderUser orderUser;

		res = new ModelAndView("orderUser/display");

		if (orderUserId == -1)
			orderUser = this.orderUserService.findUnfinishedOrder();
		else
			orderUser = this.orderUserService.findOne(orderUserId);

		res.addObject("orderUser", orderUser);
		res.addObject("message", message);

		return res;
	}

	// Add product ---------------------------------

	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public ModelAndView addProduct(@RequestParam final int productId) {
		ModelAndView res;

		try {
			this.orderUserService.addProduct(productId);
			res = new ModelAndView("redirect:display.do");
		} catch (final IllegalArgumentException e) {
			res = new ModelAndView("redirect:display.do");
			res.addObject("message", "order.product.repeat");
		}

		return res;
	}

	// Remove product -------------------------------

	@RequestMapping(value = "/removeProduct", method = RequestMethod.GET)
	public ModelAndView removeProduct(@RequestParam final int orderQuantityId) {
		ModelAndView res;

		this.orderUserService.removeProduct(orderQuantityId);

		res = new ModelAndView("redirect:display.do");

		return res;
	}

	// Finish order ---------------------------------

	@RequestMapping(value = "/finish", method = RequestMethod.GET)
	public ModelAndView finishOrder() {
		ModelAndView res;
		OrderUser orderUser;
		CreditCard creditCard;

		creditCard = this.creditCardService.findActorCreditCard();

		if (creditCard == null)
			res = new ModelAndView("redirect:/creditCard/display.do?showWarning=true");
		else if (!this.creditCardService.isCreditCardDateValid(creditCard))
			res = new ModelAndView("redirect:/creditCard/display.do?showWarning=true");
		else {
			orderUser = this.orderUserService.findUnfinishedOrder();
			res = this.createEditModelAndView(orderUser);
		}

		return res;
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final OrderUser orderUser, final BindingResult binding) {
		ModelAndView res;
		OrderUser reconstructed;

		reconstructed = this.orderUserService.reconstruct(orderUser, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(orderUser);
		else
			try {
				OrderUser order;
				order = this.orderUserService.finishOrder(reconstructed);
				res = new ModelAndView("redirect:display.do?orderUserId=" + order.getId());
				res.addObject("message", "order.finish.ok");
			} catch (final Throwable th) {
				res = new ModelAndView("redirect:display.do?orderUserId=" + reconstructed.getId());

				if (th.getClass().equals(IllegalArgumentException.class))
					res.addObject("message", "order.finish.stock.error");
				else
					res.addObject("message", "misc.commit.error");
			}

		return res;
	}

	// Ancillary methods ----------------------------

	protected ModelAndView createEditModelAndView(final OrderUser orderUser) {
		ModelAndView res;

		res = this.createEditModelAndView(orderUser, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final OrderUser orderUser, final String message) {
		ModelAndView res;
		Collection<ShippingAddress> shippingAddresses;

		shippingAddresses = this.shippingAddressService.findShippingAddressesByUser();
		res = new ModelAndView("orderUser/edit");
		res.addObject("orderUser", orderUser);
		res.addObject("addresses", shippingAddresses);
		res.addObject("message", message);

		return res;
	}

}
