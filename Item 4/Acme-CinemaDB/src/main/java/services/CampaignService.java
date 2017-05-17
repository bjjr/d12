
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CampaignRepository;
import domain.Campaign;

@Service
@Transactional
public class CampaignService {

	// Managed repository -----------------------------------

	@Autowired
	private CampaignRepository	campaignRepository;


	// Constructors -----------------------------------------

	public CampaignService() {
		super();
	}

	// Other business methods ----------------------------------

	public Collection<Campaign> findUnpaidCampaigns() {
		Collection<Campaign> result;

		result = this.campaignRepository.findAllCampaignsFinished();
		result.removeAll(this.campaignRepository.findCampaignsInvoices());

		return result;
	}

}
