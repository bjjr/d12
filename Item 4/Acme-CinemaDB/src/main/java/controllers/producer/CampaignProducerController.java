
package controllers.producer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CampaignService;
import services.ProducerService;
import controllers.AbstractController;
import domain.Campaign;
import domain.Producer;

@Controller
@RequestMapping("/campaign/producer")
public class CampaignProducerController extends AbstractController {

	// Services -----------------------------------------------

	@Autowired
	private CampaignService	campaignService;

	@Autowired
	private ProducerService	producerService;


	// Constructors -------------------------------------------

	public CampaignProducerController() {
		super();
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Campaign> campaigns;
		Producer producer;

		producer = this.producerService.findByPrincipal();
		campaigns = this.campaignService.findCampaignsProducer(producer.getId());

		result = new ModelAndView("campaign/list");
		result.addObject("campaigns", campaigns);
		result.addObject("requestURI", "campaign/producer/list.do");

		return result;
	}

	// Create ---------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Campaign campaign;

		campaign = this.campaignService.create();
		result = new ModelAndView("campaign/create");
		result.addObject("campaign", campaign);

		return result;
	}

	// Save -----------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Campaign campaign, final BindingResult binding) {
		ModelAndView result;
		Campaign reconstructed;

		reconstructed = this.campaignService.reconstruct(campaign, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("campaign/create");
			result.addObject("campaign", reconstructed);
		} else
			try {
				this.campaignService.save(reconstructed);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable th) {
				result = new ModelAndView("campaign/create");
				result.addObject("campaign", reconstructed);
				result.addObject("message", "misc.commit.error");
			}

		return result;
	}

}
