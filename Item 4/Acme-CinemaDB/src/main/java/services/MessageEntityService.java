
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageEntityRepository;
import security.Authority;
import domain.Actor;
import domain.MessageEntity;

@Service
@Transactional
public class MessageEntityService {

	// Managed repository -----------------------------------

	@Autowired
	private MessageEntityRepository	messageEntityRepository;

	// Supporting services ----------------------------------

	@Autowired
	private ActorService			actorService;

	// Validator --------------------------------------------

	@Autowired
	private Validator				validator;


	// Constructors -----------------------------------------

	public MessageEntityService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public MessageEntity create(final int recipientId) {
		Assert.isTrue(this.actorService.checkAuthority("PRODUCER") || this.actorService.checkAuthority("ADMIN"));

		MessageEntity result;
		Actor sender, recipient;
		Date moment;
		Authority authProducer, authAdmin;

		authProducer = new Authority();
		authAdmin = new Authority();
		authProducer.setAuthority(Authority.PRODUCER);
		authAdmin.setAuthority(Authority.ADMIN);

		sender = this.actorService.findByPrincipal();
		recipient = this.actorService.findOne(recipientId);
		Assert.isTrue(recipient.getUserAccount().getAuthorities().contains(authProducer) || recipient.getUserAccount().getAuthorities().contains(authAdmin), "You only can send messages to producers or administrator");
		moment = new Date(System.currentTimeMillis() - 1000);
		Assert.isTrue(!recipient.equals(sender), "Cannot send a message to you");
		result = new MessageEntity();
		result.setSender(sender);
		result.setRecipient(recipient);
		result.setCopy(false);
		result.setMoment(moment);

		return result;
	}

	public MessageEntity findOne(final int messageEntityId) {
		Assert.isTrue(this.actorService.checkAuthority("PRODUCER") || this.actorService.checkAuthority("ADMIN"));

		MessageEntity result;

		result = this.messageEntityRepository.findOne(messageEntityId);
		Assert.notNull(result);

		return result;
	}

	public Collection<MessageEntity> findAll() {
		Assert.isTrue(this.actorService.checkAuthority("PRODUCER") || this.actorService.checkAuthority("ADMIN"));

		Collection<MessageEntity> result;

		result = this.messageEntityRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// This method finds, using a query, the sent messages by the actor logged

	public Collection<MessageEntity> findSentMessages() {
		Assert.isTrue(this.actorService.checkAuthority("PRODUCER") || this.actorService.checkAuthority("ADMIN"));

		Collection<MessageEntity> result;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		result = this.messageEntityRepository.findSentMessages(actor.getId());

		return result;
	}

	// This method finds, using a query, the received messages by the actor logged

	public Collection<MessageEntity> findReceivedMessages() {
		Assert.isTrue(this.actorService.checkAuthority("PRODUCER") || this.actorService.checkAuthority("ADMIN"));

		Collection<MessageEntity> result;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		result = this.messageEntityRepository.findReceivedMessages(actor.getId());

		return result;
	}

	public MessageEntity send(final MessageEntity messageEntity) {
		Assert.isTrue(this.actorService.checkAuthority("PRODUCER") || this.actorService.checkAuthority("ADMIN"));

		Assert.notNull(messageEntity);

		MessageEntity result;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1000);
		messageEntity.setCopy(false);
		messageEntity.setMoment(moment);
		result = this.messageEntityRepository.save(messageEntity);

		return result;
	}

	// This method save a copy of a message for the recipient of this message

	public MessageEntity saveCopy(final MessageEntity originalMessage) {
		Assert.isTrue(this.actorService.checkAuthority("PRODUCER") || this.actorService.checkAuthority("ADMIN"));

		MessageEntity aux, result;

		aux = new MessageEntity();

		aux.setCopy(true);
		aux.setSender(originalMessage.getSender());
		aux.setRecipient(originalMessage.getRecipient());
		aux.setTitle(originalMessage.getTitle());
		aux.setBody(originalMessage.getBody());
		aux.setMoment(originalMessage.getMoment());
		result = this.messageEntityRepository.save(aux);

		return result;
	}

	public void flush() {
		this.messageEntityRepository.flush();
	}

	// Other business methods -------------------------------

	public MessageEntity reconstruct(final MessageEntity messageEntity, final BindingResult bindingResult, final int recipientId) {
		Assert.isTrue(this.actorService.checkAuthority("PRODUCER") || this.actorService.checkAuthority("ADMIN"));
		MessageEntity result;
		Actor sender;
		Actor recipient;

		result = messageEntity;
		sender = this.actorService.findByPrincipal();
		recipient = this.actorService.findOne(recipientId);
		result.setCopy(false);
		result.setSender(sender);
		result.setRecipient(recipient);
		result.setMoment(new Date(System.currentTimeMillis() - 1000));

		this.validator.validate(result, bindingResult);

		return result;
	}

}
