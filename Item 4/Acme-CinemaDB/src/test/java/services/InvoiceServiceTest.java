
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Campaign;
import domain.Invoice;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class InvoiceServiceTest extends AbstractTest {

	// System under test ----------------------------

	@Autowired
	private InvoiceService	invoiceService;

	//Services ----------------------------

	@Autowired
	private CampaignService	campaignService;


	// Tests ----------------------------------------

	/*
	 * Use case: An actor who is authenticated as an administrator must be able to:
	 * Create invoices and associate them to campaigns
	 * Expected errors:
	 * - An unauthenticated actor tries to create an invoice --> IllegalArgumentException
	 * - An user tries to create an invoice --> IllegalArgumentException
	 * - A critic tries to create an invoice --> IllegalArgumentException
	 * - A producer tries to create an invoice --> IllegalArgumentException
	 * - An administrator tries to create an invoice and associate it to a
	 * cancelled campaign --> IllegalArgumentException
	 * - An administrator tries to create an invoice and associate it to a
	 * campaign that already has an invoice --> IllegalArgumentException
	 * - An administrator tries to create an invoice and associate it to a
	 * a non approved campaign
	 */

	@Test
	public void createInvoiceDriver() {
		final Object testingData[][] = {
			{    //An unauthenticated actor tries to create an invoice
				null, 269, IllegalArgumentException.class
			}, { //An user tries to create an invoice
				"user1", 269, IllegalArgumentException.class
			}, { //A critic tries to create an invoice
				"critic1", 269, IllegalArgumentException.class
			}, { //A producer tries to create an invoice
				"producer1", 269, IllegalArgumentException.class
			}, { //An administrator tries to create an invoice and associate it to a cancelled campaign 
				"admin", 263, IllegalArgumentException.class
			}, { //An administrator tries to create an invoice and associate it to a campaign that already has an invoice 
				"admin", 268, IllegalArgumentException.class
			}, { //An administrator tries to create an invoice and associate it to a non approved campaign 
				"admin", 271, IllegalArgumentException.class
			}, { //Successful test
				"admin", 270, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createInvoiceTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/*
	 * Use case: An actor who is authenticated as a producer must be able to:
	 * Pay an invoice
	 * Expected errors:
	 * - An unauthenticated actor tries to pay an invoice --> IllegalArgumentException
	 * - An user tries to pay an invoice --> IllegalArgumentException
	 * - A critic tries to pay an invoice --> IllegalArgumentException
	 * - An administrator tries to pay an invoice --> IllegalArgumentException
	 * - A producer tries to pay a paid invoice --> IllegalArgumentException
	 */

	@Test
	public void payInvoiceDriver() {
		final Object testingData[][] = {
			{    //An unauthenticated actor tries to pay an invoice
				null, 282, IllegalArgumentException.class
			}, { //An user tries to pay an invoice
				"user1", 282, IllegalArgumentException.class
			}, { //A critic tries to pay an invoice
				"critic1", 282, IllegalArgumentException.class
			}, { //An administrator tries to pay an invoice
				"admin", 282, IllegalArgumentException.class
			}, { //A producer tries to pay a paid invoice
				"producer3", 281, IllegalArgumentException.class
			}, { //Successful test
				"producer3", 282, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.payInvoiceTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates --------------------------------------------------------------

	protected void createInvoiceTemplate(final String username, final int campaignId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			Invoice invoice;
			final Campaign campaign;

			this.authenticate(username);

			invoice = this.invoiceService.create();
			campaign = this.campaignService.findOne(campaignId);
			invoice.setBillingDate(new Date(System.currentTimeMillis()));
			invoice.setPaid(false);
			invoice.setTotal(45.78);
			campaign.setTimesDisplayed(campaign.getMax());
			invoice.setCampaign(campaign);

			this.invoiceService.save(invoice);
			this.invoiceService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void payInvoiceTemplate(final String username, final int invoiceId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			final Invoice invoice;

			this.authenticate(username);

			invoice = this.invoiceService.findOne(invoiceId);

			this.invoiceService.setPaid(invoice);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
