
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InvoiceRepository;
import domain.Administrator;
import domain.Campaign;
import domain.Invoice;

@Service
@Transactional
public class InvoiceService {

	// Managed repository -----------------------------------

	@Autowired
	private InvoiceRepository		invoiceRepository;

	//Supporting services -----------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------

	public InvoiceService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Invoice create() {
		Assert.isTrue(this.actorService.checkAuthority("ADMIN"));

		final Invoice result;
		final Administrator administrator;
		Date billingDate;

		result = new Invoice();
		administrator = this.administratorService.findByPrincipal();
		billingDate = new Date(System.currentTimeMillis() - 1000);

		result.setAdministrator(administrator);
		result.setPaid(false);
		result.setBillingDate(billingDate);

		return result;
	}

	public Invoice save(final Invoice invoice) {
		Assert.isTrue(this.actorService.checkAuthority("ADMIN"));
		Assert.notNull(invoice);

		Invoice result;
		Date billingDate;
		Campaign campaign;
		Date current;

		campaign = invoice.getCampaign();
		current = new Date(System.currentTimeMillis());

		Assert.isTrue(campaign.getEnd().before(current) || campaign.getTimesDisplayed() == campaign.getMax());

		billingDate = new Date(System.currentTimeMillis() - 1000);
		invoice.setTotal(campaign.getTimesDisplayed() * campaign.getFee());
		invoice.setBillingDate(billingDate);
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
		Assert.isTrue(this.actorService.checkAuthority("ADMIN"));
		Assert.isTrue(!invoice.getPaid());
		Assert.notNull(invoice);

		invoice.setPaid(true);

	}

}
