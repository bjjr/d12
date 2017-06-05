
package controllers.producer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.InvoiceService;
import services.ProducerService;
import controllers.AbstractController;
import domain.CreditCard;
import domain.Invoice;
import domain.Producer;

@Controller
@RequestMapping("/invoice/producer")
public class InvoiceProducerController extends AbstractController {

	@Autowired
	private InvoiceService		invoiceService;

	@Autowired
	private ProducerService		producerService;

	@Autowired
	private CreditCardService	creditCardService;


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
		Producer producer;
		CreditCard creditCard;

		invoice = this.invoiceService.findOne(invoiceId);
		producer = this.producerService.findByPrincipal();
		creditCard = producer.getCreditCard();
		result = new ModelAndView();

		try {
			if (creditCard == null)
				result = new ModelAndView("redirect:/creditCard/create.do");
			if (creditCard != null && !this.creditCardService.isCreditCardDateValid(creditCard))
				result = new ModelAndView("redirect:/creditCard/edit.do?creditCardId=" + creditCard.getId());
			if (creditCard != null && this.creditCardService.isCreditCardDateValid(creditCard)) {
				this.invoiceService.setPaid(invoice);
				result = new ModelAndView("redirect:list.do");
				result.addObject("messageStatus", "misc.commit.ok");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageStatus", "misc.commit.error");
		}

		return result;
	}

}
