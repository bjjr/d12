
package controllers.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProductService;
import controllers.AbstractController;
import domain.Product;

@Controller
@RequestMapping("/product/producer")
public class ProductProducerController extends AbstractController {

	@Autowired
	private ProductService	productService;


	// Constructors -------------------------------------------

	public ProductProducerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int contentId) {

		final Product product = this.productService.create(contentId);

		final ModelAndView view = this.createEditModelAndView(product);

		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int productId) {

		final Product product = this.productService.findOne(productId);
		Assert.notNull(product);

		final ModelAndView view = this.createEditModelAndView(product);

		return view;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Product product, final BindingResult bindingResult) {
		ModelAndView result;

		final Product productReconstructed = this.productService.reconstruct(product, bindingResult);

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(productReconstructed);
		else
			try {
				this.productService.save(productReconstructed);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable e) {
				result = this.createEditModelAndView(productReconstructed, "misc.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Product product) {
		ModelAndView res;

		res = this.createEditModelAndView(product, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Product product, final String message) {
		ModelAndView res;

		res = new ModelAndView("product/edit");

		res.addObject("product", product);
		res.addObject("message", message);

		return res;
	}

}
