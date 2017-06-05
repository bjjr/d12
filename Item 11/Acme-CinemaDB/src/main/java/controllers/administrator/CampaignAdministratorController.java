
package controllers.administrator;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CampaignService;
import controllers.AbstractController;
import domain.Campaign;

@Controller
@RequestMapping("/campaign/administrator")
public class CampaignAdministratorController extends AbstractController {

	// Services -----------------------------------------------

	@Autowired
	private CampaignService	campaignService;


	// Constructors -------------------------------------------

	public CampaignAdministratorController() {
		super();
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Campaign> campaigns;
		Date current;

		campaigns = this.campaignService.findAll();
		current = new Date(System.currentTimeMillis());

		result = new ModelAndView("campaign/list");
		result.addObject("campaigns", campaigns);
		result.addObject("requestURI", "campaign/administrator/list.do");
		result.addObject("current", current);

		return result;
	}

	//Approve -----------------------------------------

	@RequestMapping(value = "/approve", method = RequestMethod.GET)
	public ModelAndView approve(@RequestParam final int campaignId) {
		ModelAndView result;
		Campaign campaign;

		campaign = this.campaignService.findOne(campaignId);

		try {
			this.campaignService.acceptCampaign(campaign);
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageStatus", "misc.commit.ok");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageStatus", "misc.commit.error");
		}

		return result;
	}

	//Cancel -----------------------------------------

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int campaignId) {
		ModelAndView result;
		Campaign campaign;

		campaign = this.campaignService.findOne(campaignId);

		try {
			this.campaignService.cancelCampaign(campaign);
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageStatus", "misc.commit.ok");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageStatus", "misc.commit.error");
		}

		return result;
	}

}
