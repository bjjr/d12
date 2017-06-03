
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Campaign;
import domain.Producer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CampaignServiceTest extends AbstractTest {

	// System under test ----------------------------

	@Autowired
	private CampaignService	campaignService;

	//Services ----------------------------

	@Autowired
	private ProducerService	producerService;

	@Autowired
	private FeeService		feeService;


	// Tests ----------------------------------------

	/*
	 * Use case: An actor who is authenticated as a producer must be able to:
	 * Create campaigns when he/she had a valid credit card
	 * Expected errors:
	 * - An unauthenticated actor tries to create a campaign --> IllegalArgumentException
	 * - An user tries to create a campaign --> IllegalArgumentException
	 * - A critic tries to create a campaign --> IllegalArgumentException
	 * - An administrator tries to create a campaign --> IllegalArgumentException
	 * - A producer without a credit card tries to create a campaign --> IllegalArgumentException
	 */

	@Test
	public void createCampaignDriver() {
		final Object testingData[][] = {
			{    //An unauthenticated actor tries to create a campaign
				null, IllegalArgumentException.class
			}, { //An user tries to create a campaign
				"user1", IllegalArgumentException.class
			}, { //A critic tries to create a campaign
				"critic1", IllegalArgumentException.class
			}, { //An administrator tries to create a campaign
				"admin", IllegalArgumentException.class
			}, { //A producer without a credit card tries to create a campaign
				"producer1", IllegalArgumentException.class
			}, { //Successful test
				"producer3", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createCampaignTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/*
	 * Use case: An actor who is authenticated as an administrator must be able to:
	 * Accept a campaign
	 * Expected errors:
	 * - An unauthenticated actor tries to accept a campaign --> IllegalArgumentException
	 * - An user tries to accept a campaign --> IllegalArgumentException
	 * - A critic tries to accept a campaign --> IllegalArgumentException
	 * - A producer tries to accept a campaign --> IllegalArgumentException
	 * - An administrator tries to accept an accepted campaign --> IllegalArgumentException
	 * - An administrator tries to accept a cancelled campaign --> IllegalArgumentException
	 */

	@Test
	public void acceptCampaignDriver() {
		final Object testingData[][] = {
			{    //An unauthenticated actor tries to accept a campaign
				null, 271, IllegalArgumentException.class
			}, { //An user tries to accept a campaign
				"user1", 271, IllegalArgumentException.class
			}, { //A critic tries to accept a campaign
				"critic1", 271, IllegalArgumentException.class
			}, { //A producer tries to accept a campaign
				"producer1", 271, IllegalArgumentException.class
			}, { //An administrator tries to accept an accepted campaign
				"admin", 264, IllegalArgumentException.class
			}, { //An administrator tries to accept a cancelled campaign
				"admin", 263, IllegalArgumentException.class
			}, { //Successful test
				"admin", 271, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.acceptCampaignTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/*
	 * Use case: An actor who is authenticated as an administrator must be able to:
	 * Cancel a campaign
	 * Expected errors:
	 * - An unauthenticated actor tries to cancel a campaign --> IllegalArgumentException
	 * - An user tries to cancel a campaign --> IllegalArgumentException
	 * - A critic tries to cancel a campaign --> IllegalArgumentException
	 * - A producer tries to cancel a campaign --> IllegalArgumentException
	 * - An administrator tries to cancel an accepted campaign --> IllegalArgumentException
	 * - An administrator tries to cancel a cancelled campaign --> IllegalArgumentException
	 */

	@Test
	public void cancelCampaignDriver() {
		final Object testingData[][] = {
			{    //An unauthenticated actor tries to cancel a campaign
				null, 271, IllegalArgumentException.class
			}, { //An user tries to cancel a campaign
				"user1", 271, IllegalArgumentException.class
			}, { //A critic tries to cancel a campaign
				"critic1", 271, IllegalArgumentException.class
			}, { //A producer tries to cancel a campaign
				"producer1", 271, IllegalArgumentException.class
			}, { //An administrator tries to cancel an accepted campaign
				"admin", 264, IllegalArgumentException.class
			}, { //An administrator tries to cancel a cancelled campaign
				"admin", 263, IllegalArgumentException.class
			}, { //Successful test
				"admin", 271, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.cancelCampaignTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates --------------------------------------------------------------

	protected void createCampaignTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			final Campaign campaign;
			final Date start;
			final Date end;
			Collection<String> images;
			final Producer producer;

			this.authenticate(username);

			start = new Date(System.currentTimeMillis());
			end = new Date(1501365600000L);
			images = new ArrayList<String>();
			images.add("https://pics.filmaffinity.com/el_ministerio_del_tiempo_tv_series-544602756-large.jpg");
			producer = this.producerService.findByPrincipal();

			campaign = this.campaignService.create();
			campaign.setStart(start);
			campaign.setEnd(end);
			campaign.setImages(images);
			campaign.setMax(150);
			campaign.setProducer(producer);
			campaign.setFee(this.feeService.findFeeValue());
			campaign.setTimesDisplayed(0);

			this.campaignService.save(campaign);
			this.campaignService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void acceptCampaignTemplate(final String username, final int campaignId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			final Campaign campaign;

			this.authenticate(username);

			campaign = this.campaignService.findOne(campaignId);

			this.campaignService.acceptCampaign(campaign);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void cancelCampaignTemplate(final String username, final int campaignId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			final Campaign campaign;

			this.authenticate(username);

			campaign = this.campaignService.findOne(campaignId);

			this.campaignService.cancelCampaign(campaign);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
