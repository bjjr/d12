
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.InvoiceRepository;
import domain.Campaign;
import domain.Invoice;

@Service
@Transactional
public class InvoiceService {

	// Managed repository -----------------------------------

	@Autowired
	private InvoiceRepository	invoiceRepository;

	//Supporting services -----------------------------------

	@Autowired
	private ActorService		actorService;

	// Validator --------------------------------------------

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------

	public InvoiceService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Invoice create() {
		Assert.isTrue(this.actorService.checkAuthority("ADMIN"));

		final Invoice result;

		result = new Invoice();

		return result;
	}

	public Invoice save(final Invoice invoice) {
		Assert.isTrue(this.actorService.checkAuthority("ADMIN"));
		Assert.notNull(invoice);

		Invoice result;
		Date current;
		Campaign campaign;

		campaign = invoice.getCampaign();
		current = new Date(System.currentTimeMillis());

		Assert.isTrue(this.invoiceRepository.findInvoiceCampaign(campaign.getId()) == null);
		Assert.isTrue(campaign.getApproved() != null);
		Assert.isTrue(campaign.getApproved());
		Assert.isTrue(campaign.getEnd().before(current) || campaign.getTimesDisplayed() == campaign.getMax());

		result = this.invoiceRepository.save(invoice);

		return result;
	}

	public void flush() {
		this.invoiceRepository.flush();
	}

	public Invoice findOne(final int invoiceId) {
		Assert.isTrue(invoiceId != 0);
		Assert.notNull(invoiceId);

		Invoice result;

		result = this.invoiceRepository.findOne(invoiceId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Invoice> findAll() {
		Collection<Invoice> result;

		result = this.invoiceRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// Other business methods ----------------------------------

	public void setPaid(final Invoice invoice) {
		Assert.isTrue(this.actorService.checkAuthority("PRODUCER"));
		Assert.isTrue(!invoice.getPaid());
		Assert.notNull(invoice);

		invoice.setPaid(true);

	}

	public Invoice findInvoiceCampaign(final int campaignId) {
		Assert.isTrue(campaignId != 0);
		Assert.notNull(campaignId);

		Invoice result;

		result = this.invoiceRepository.findInvoiceCampaign(campaignId);

		return result;
	}

	public Invoice reconstruct(final Invoice invoice, final BindingResult bindingResult) {
		Assert.isTrue(this.actorService.checkAuthority("ADMIN"));
		Assert.isTrue(invoice.getId() == 0);

		Invoice result;
		Date billingDate;
		Campaign campaign;

		result = invoice;

		if (result.getCampaign() == null)
			bindingResult.rejectValue("campaign", "invoice.error.campaign");
		else {
			billingDate = new Date(System.currentTimeMillis() - 1000);
			campaign = invoice.getCampaign();

			result.setBillingDate(billingDate);
			result.setPaid(false);
			invoice.setTotal(campaign.getTimesDisplayed() * campaign.getFee());
			this.validator.validate(result, bindingResult);
		}

		return result;
	}

	public Collection<Invoice> findInvoicesProducer(final int producerId) {
		Assert.isTrue(producerId != 0);
		Assert.notNull(producerId);

		Collection<Invoice> result;

		result = this.invoiceRepository.findInvoicesProducer(producerId);

		return result;
	}

	public Double findMinTotalMoneyInvoices() {
		Double result;

		result = this.invoiceRepository.findMinTotalMoneyInvoices();

		return result;
	}

	public Double findAvgTotalMoneyInvoices() {
		Double result;

		result = this.invoiceRepository.findAvgTotalMoneyInvoices();

		return result;
	}

	public Double findMaxTotalMoneyInvoices() {
		Double result;

		result = this.invoiceRepository.findMaxTotalMoneyInvoices();

		return result;
	}
}
