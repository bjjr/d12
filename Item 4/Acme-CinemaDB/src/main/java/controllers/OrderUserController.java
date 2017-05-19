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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.OrderUserService;
import services.UserService;
import domain.OrderUser;
import domain.User;

@Controller
@RequestMapping("/order")
public class OrderUserController extends AbstractController {

	// Supporting services --------------------------

	@Autowired
	private OrderUserService	orderUserService;

	@Autowired
	private UserService			userService;


	// Constructors ---------------------------------

	public OrderUserController() {
		super();
	}

	// List -----------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<OrderUser> orders;
		User principal;

		principal = this.userService.findByPrincipal();
		orders = this.orderUserService.findOrdersByUser(principal.getId());

		res = new ModelAndView("orderUser/list");
		res.addObject("orders", orders);

		return res;
	}

}
