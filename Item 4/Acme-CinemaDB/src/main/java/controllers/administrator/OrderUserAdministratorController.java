/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.OrderUserService;
import controllers.AbstractController;
import domain.OrderUser;

@Controller
@RequestMapping("/orderUser/administrator")
public class OrderUserAdministratorController extends AbstractController {

	// Supporting services --------------------------

	@Autowired
	private OrderUserService	orderUserService;


	// Constructors ---------------------------------

	public OrderUserAdministratorController() {
		super();
	}

	// Mark sent ------------------------------------

	@RequestMapping(value = "send", method = RequestMethod.GET)
	public ModelAndView markSent(@RequestParam final int orderUserId) {
		ModelAndView res;
		OrderUser orderUser;

		orderUser = this.orderUserService.findOne(orderUserId);
		this.orderUserService.setOrderStatus(orderUser, 1);

		res = new ModelAndView("redirect:/orderUser/list.do");

		return res;
	}

	// Mark cancelled -------------------------------

	@RequestMapping(value = "cancel", method = RequestMethod.GET)
	public ModelAndView markCancel(@RequestParam final int orderUserId) {
		ModelAndView res;
		OrderUser orderUser;

		orderUser = this.orderUserService.findOne(orderUserId);
		this.orderUserService.setOrderStatus(orderUser, 2);

		res = new ModelAndView("redirect:/orderUser/list.do");

		return res;
	}

}
