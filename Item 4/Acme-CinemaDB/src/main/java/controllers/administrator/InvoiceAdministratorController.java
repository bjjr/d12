
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CampaignService;
import services.InvoiceService;
import controllers.AbstractController;
import domain.Campaign;
import domain.Invoice;

@Controller
@RequestMapping("/invoice/administrator")
public class InvoiceAdministratorController extends AbstractController {

	// Services -----------------------------------------------

	@Autowired
	private InvoiceService	invoiceService;

	@Autowired
	private CampaignService	campaignService;


	// Constructors -------------------------------------------

	public InvoiceAdministratorController() {
		super();
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Invoice> invoices;

		invoices = this.invoiceService.findAll();

		result = new ModelAndView("invoice/list");
		result.addObject("invoices", invoices);
		result.addObject("requestURI", "invoice/administrator/list.do");

		return result;
	}

	// Create ---------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Invoice invoice;
		Collection<Campaign> unpaidCampaigns;

		invoice = this.invoiceService.create();
		result = new ModelAndView("invoice/create");
		unpaidCampaigns = this.campaignService.findUnpaidCampaigns();
		result.addObject("invoice", invoice);
		result.addObject("campaigns", unpaidCampaigns);

		return result;
	}

	// Save -----------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Invoice invoice, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("invoice/create");
			result.addObject("invoice", invoice);
		} else
			try {
				this.invoiceService.save(invoice);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable th) {
				result = new ModelAndView("invoice/create");
				result.addObject("invoice", invoice);
				result.addObject("message", "misc.commit.error");
			}

		return result;
	}

	//Set paid -----------------------------------------

	@RequestMapping(value = "/setPaid", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int invoiceId) {
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
