
package services;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.validation.Payload;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.internal.constraintvalidators.URLValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CampaignRepository;
import domain.Campaign;
import domain.Producer;

@Service
@Transactional
public class CampaignService {

	// Managed repository -----------------------------------

	@Autowired
	private CampaignRepository	campaignRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FeeService			feeService;

	@Autowired
	private ProducerService		producerService;

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------

	public CampaignService() {
		super();
	}


	// Validator --------------------------------------------

	@Autowired
	private Validator	validator;


	// Simple CRUD methods ----------------------------------

	public Campaign create() {
		Assert.isTrue(this.actorService.checkAuthority("PRODUCER"));

		Campaign result;
		Producer producer;

		producer = this.producerService.findByPrincipal();

		Assert.isTrue(producer.getCreditCard() != null);
		Assert.isTrue(this.creditCardService.isCreditCardDateValid(producer.getCreditCard()));

		result = new Campaign();

		return result;
	}

	public Campaign save(final Campaign campaign) {
		Assert.isTrue(this.actorService.checkAuthority("PRODUCER"));

		final Campaign result;

		result = this.campaignRepository.save(campaign);

		return result;
	}

	public Campaign saveAnyone(final Campaign campaign) {
		final Campaign result;

		result = this.campaignRepository.save(campaign);

		return result;
	}

	public Campaign findOne(final int campaignId) {
		Assert.isTrue(campaignId != 0);
		Assert.notNull(campaignId);

		Campaign result;

		result = this.campaignRepository.findOne(campaignId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Campaign> findAll() {
		Collection<Campaign> result;

		result = this.campaignRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void flush() {
		this.campaignRepository.flush();
	}

	// Other business methods ----------------------------------

	public Collection<Campaign> findUnpaidCampaigns() {
		Collection<Campaign> result;
		Collection<Campaign> campaignsInvoices;

		result = this.campaignRepository.findAllCampaignsFinished();
		campaignsInvoices = this.campaignRepository.findCampaignsInvoices();
		result.removeAll(campaignsInvoices);

		return result;
	}

	public Collection<Campaign> findAllCampaignsUnfinished() {
		Collection<Campaign> result;

		result = this.campaignRepository.findAllCampaignsUnfinished();
		Assert.notNull(result);

		return result;
	}

	public Collection<Campaign> findCampaignsUnpaidInvoices() {
		Collection<Campaign> result;

		result = this.campaignRepository.findCampaignsUnpaidInvoices();
		Assert.notNull(result);

		return result;
	}

	public void acceptCampaign(final Campaign campaign) {
		Assert.isTrue(this.actorService.checkAuthority("ADMIN"));
		Assert.isTrue(campaign.getApproved() == null);

		campaign.setApproved(true);
	}

	public void cancelCampaign(final Campaign campaign) {
		Assert.isTrue(this.actorService.checkAuthority("ADMIN"));
		Assert.isTrue(campaign.getApproved() == null);

		campaign.setApproved(false);
	}

	public Collection<Campaign> findCampaignsProducer(final int producerId) {
		Assert.isTrue(producerId != 0);
		Assert.notNull(producerId);

		Collection<Campaign> result;

		result = this.campaignRepository.findCampaignsProducer(producerId);

		return result;
	}

	public Campaign reconstruct(final Campaign campaign, final BindingResult bindingResult) {
		Assert.isTrue(this.actorService.checkAuthority("PRODUCER"));
		Assert.isTrue(campaign.getId() == 0);

		Campaign result;
		final Double fee;
		Producer producer;

		producer = this.producerService.findByPrincipal();

		Assert.isTrue(producer.getCreditCard() != null);

		result = campaign;
		fee = this.feeService.findFeeValue();
		result.setFee(fee);
		result.setProducer(producer);
		result.setTimesDisplayed(0);

		this.validateURLs(result.getImages(), bindingResult);
		this.validator.validate(result, bindingResult);

		return result;
	}

	private void validateURLs(final Collection<String> attachments, final BindingResult binding) {
		URLValidator validator;

		validator = new URLValidator();

		validator.initialize(new URL() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}

			@Override
			public String regexp() {
				return ".*";
			}

			@Override
			public String protocol() {
				return "";
			}

			@Override
			public int port() {
				return -1;
			}

			@Override
			public Class<? extends Payload>[] payload() {
				return null;
			}

			@Override
			public String message() {
				return "org.hibernate.validator.constraints.URL.message";
			}

			@Override
			public String host() {
				return "";
			}

			@Override
			public Class<?>[] groups() {
				return null;
			}

			@Override
			public Flag[] flags() {
				return null;
			}
		});

		for (final String s : attachments)
			if (!validator.isValid(s, null)) {
				binding.rejectValue("attachments", "org.hibernate.validator.constraints.URL.message");
				break;
			}
	}

	public void incrementDisplayed(final Campaign c) {
		final Campaign res = c;
		res.setTimesDisplayed(c.getTimesDisplayed() + 1);
		this.saveAnyone(res);
	}

	public String displayBanner() {
		String res = "";
		ArrayList<String> banners;
		final ArrayList<Campaign> listC = (ArrayList<Campaign>) this.findAllCampaignsUnfinished();
		if (listC.size() > 0) {
			final Campaign c = listC.get(new Random().nextInt(listC.size()));
			banners = new ArrayList<>(c.getImages());
			res = banners.get(new Random().nextInt(banners.size()));
			this.incrementDisplayed(c);
		}
		return res;
	}

}
