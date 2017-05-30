
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;
import domain.MessageEntity;
import domain.Producer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageEntityServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MessageEntityService	messageEntityService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ProducerService			producerService;

	@Autowired
	private AdministratorService	administratorService;


	// Tests ------------------------------------------------------------------

	/*
	 * Use case: An actor who is authenticated as an administrator must be able to:
	 * Send messages to producers
	 * Expected errors:
	 * - The administrator tries to send a message to an user --> IllegalArgumentException
	 * - The administrator tries to send a message to himself --> IllegalArgumentException
	 */

	@Test
	public void sendMessageToAProducerDriver() {
		final Object testingData[][] = {
			{    //The administrator cannot send a message to a user
				"admin", 252, IllegalArgumentException.class
			}, { //The administrator cannot send a message to himself
				"admin", 249, IllegalArgumentException.class
			}, { //Successful test
				"admin", 266, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.sendMessageToAProducerTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/*
	 * Use case: An actor who is authenticated as a producer must be able to:
	 * Send messages to the administrator
	 * Expected errors:
	 * - An user tries to send a message to the administrator --> IllegalArgumentException
	 * - A critic tries to send a message to the administrator --> IllegalArgumentException
	 */

	@Test
	public void sendMessageToTheAdministratorDriver() {
		final Object testingData[][] = {
			{    //An user cannot send a message to the administrator
				"user3", IllegalArgumentException.class
			}, { //A critic cannot send a message to the administrator
				"critic1", IllegalArgumentException.class
			}, { //Successful test
				"producer1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.sendMessageToTheAdministratorTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/*
	 * Use case: An actor who is authenticated as a producer or administrator must be able to:
	 * Browse the list of messages that he or she has sent
	 */

	@Test
	public void findChirpsSentTest() {
		Collection<MessageEntity> sentMessages;
		Boolean isReceived;

		this.authenticate("producer2");

		sentMessages = this.messageEntityService.findSentMessages();
		isReceived = false;

		this.unauthenticate();

		for (final MessageEntity m : sentMessages)
			if (m.getCopy())
				isReceived = true;
		Assert.isTrue(!isReceived);
	}

	/*
	 * Use case: An actor who is authenticated as a producer or administrator must be able to:
	 * Browse the list of messages that he or she got
	 */

	@Test
	public void findChirpsReceivedTest() {
		Collection<MessageEntity> receivedMessages;
		Boolean isSent;

		this.authenticate("admin");

		receivedMessages = this.messageEntityService.findReceivedMessages();
		isSent = false;

		this.unauthenticate();

		for (final MessageEntity m : receivedMessages)
			if (!m.getCopy())
				isSent = true;
		Assert.isTrue(!isSent);
	}

	// Templates --------------------------------------------------------------

	protected void sendMessageToAProducerTemplate(final String username, final int producerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			final MessageEntity created, sent, copy;
			final Producer producer;
			final Administrator administrator;

			producer = this.producerService.findOne(producerId);
			administrator = this.administratorService.findAdministrator();

			this.authenticate(username);

			created = this.messageEntityService.create(producerId);
			created.setTitle("Title test");
			created.setBody("Body test");
			sent = this.messageEntityService.send(created);
			copy = this.messageEntityService.saveCopy(sent);
			this.messageEntityService.flush();

			this.unauthenticate();

			Assert.isTrue(!sent.getCopy());
			Assert.isTrue(copy.getCopy());
			Assert.isTrue(sent.getSender().equals(administrator));
			Assert.isTrue(sent.getRecipient().equals(producer));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void sendMessageToTheAdministratorTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			final MessageEntity created, sent, copy;
			final Producer producer;
			final Administrator administrator;

			this.authenticate(username);

			producer = this.producerService.findByPrincipal();
			administrator = this.administratorService.findAdministrator();

			created = this.messageEntityService.sendToAdmin();
			created.setTitle("Title test");
			created.setBody("Body test");
			sent = this.messageEntityService.send(created);
			copy = this.messageEntityService.saveCopy(sent);
			this.messageEntityService.flush();

			this.unauthenticate();

			Assert.isTrue(!sent.getCopy());
			Assert.isTrue(copy.getCopy());
			Assert.isTrue(sent.getSender().equals(producer));
			Assert.isTrue(sent.getRecipient().equals(administrator));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
