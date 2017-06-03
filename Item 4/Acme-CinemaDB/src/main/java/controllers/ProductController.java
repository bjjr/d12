
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ContentService;
import services.ProducerService;
import services.ProductService;
import domain.Content;
import domain.Producer;
import domain.Product;

@RequestMapping("/product")
@Controller
public class ProductController extends AbstractController {

	@Autowired
	private ProductService	productService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ProducerService	producerService;

	@Autowired
	private ContentService	contentService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int contentId) {
		Boolean isThisContentBelongToCurrentProducer = null;

		if (this.actorService.checkAuthority(Authority.PRODUCER)) {
			final Producer currentProducer = this.producerService.findByPrincipal();
			final Collection<Content> allContentByProducer = this.contentService.findContentByProducerId(currentProducer.getId());
			final Content content = this.contentService.findOne(contentId);
			isThisContentBelongToCurrentProducer = allContentByProducer.contains(content);
		}

		final ModelAndView view = new ModelAndView("product/list");

		final Collection<Product> allProductsByContentId = this.productService.findAllProductsByContentId(contentId);

		view.addObject("products", allProductsByContentId);
		view.addObject("isThisContentBelongToCurrentProducer", isThisContentBelongToCurrentProducer);
		view.addObject("contentId", contentId);
		view.addObject("requestURI", "/product/list.do?contentId=" + contentId);

		return view;
	}
}
