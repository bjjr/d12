
package controllers.producer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InvoiceService;
import services.ProducerService;
import controllers.AbstractController;
import domain.Invoice;
import domain.Producer;

@Controller
@RequestMapping("/invoice/producer")
public class InvoiceProducerController extends AbstractController {

	@Autowired
	private InvoiceService	invoiceService;

	@Autowired
	private ProducerService	producerService;


	// Constructors -------------------------------------------

	public InvoiceProducerController() {
		super();
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Invoice> invoices;
		Producer producer;

		producer = this.producerService.findByPrincipal();
		invoices = this.invoiceService.findInvoicesProducer(producer.getId());

		result = new ModelAndView("invoice/list");
		result.addObject("invoices", invoices);
		result.addObject("requestURI", "invoice/producer/list.do");

		return result;
	}

	//Set paid -----------------------------------------

	@RequestMapping(value = "/setPaid", method = RequestMethod.GET)
	public ModelAndView setPaid(@RequestParam final int invoiceId) {
		ModelAndView result;
		Invoice invoice;

		invoice = this.invoiceService.findOne(invoiceId);

		try {
			this.invoiceService.setPaid(invoice);
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageStatus", "misc.commit.ok");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageStatus", "misc.commit.error");
		}

		return result;
	}

}
