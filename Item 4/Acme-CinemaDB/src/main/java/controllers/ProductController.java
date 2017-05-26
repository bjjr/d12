
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProductService;
import domain.Product;

@RequestMapping("/product")
@Controller
public class ProductController extends AbstractController {

	@Autowired
	private ProductService	productService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int contentId) {

		final ModelAndView view = new ModelAndView("product/list");

		final Collection<Product> allProductsByContentId = this.productService.findAllProductsByContentId(contentId);

		view.addObject("products", allProductsByContentId);
		view.addObject("requestURI", "/product/list.do?contentId=" + contentId);

		return view;
	}

}
