
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		Invoice reconstructed;
		final Collection<Campaign> unpaidCampaigns;

		reconstructed = this.invoiceService.reconstruct(invoice, binding);
		unpaidCampaigns = this.campaignService.findUnpaidCampaigns();

		if (binding.hasErrors()) {
			result = new ModelAndView("invoice/create");
			result.addObject("invoice", reconstructed);
			result.addObject("campaigns", unpaidCampaigns);
		} else
			try {
				this.invoiceService.save(reconstructed);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable th) {
				result = new ModelAndView("invoice/create");
				result.addObject("invoice", reconstructed);
				result.addObject("message", "misc.commit.error");
				result.addObject("campaigns", unpaidCampaigns);
			}

		return result;
	}

}
