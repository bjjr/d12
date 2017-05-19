
package services;

import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CreditCardRepository;
import domain.Actor;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	@Autowired
	private CreditCardRepository	creditCardRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;


	public CreditCard create() {
		final CreditCard res = new CreditCard();

		res.setHolder("");
		res.setBrand("");
		res.setNumber("");
		res.setCvv(0);
		res.setYear(0);
		res.setMonth(0);

		return res;
	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.isTrue(this.actorService.checkAuthority("USER") || this.actorService.checkAuthority("PRODUCER"));
		Assert.notNull(creditCard);
		final Actor actor;
		CreditCard res;

		actor = this.actorService.findByPrincipal();

		// Save Brand in Uppercase
		creditCard.setBrand(creditCard.getBrand().toUpperCase());

		res = this.creditCardRepository.save(creditCard);

		if (creditCard.getId() == 0) {
			actor.setCreditCard(res);
			this.actorService.save(actor);
		}

		return res;
	}

	public void flush() {
		this.creditCardRepository.flush();
	}

	public CreditCard findOne(final int creditCardId) {
		final CreditCard res = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(res);
		return res;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> res;
		res = this.creditCardRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public CreditCard reconstruct(final CreditCard creditCard, final BindingResult bindingResult) {
		Assert.isTrue(this.actorService.checkAuthority("USER") || this.actorService.checkAuthority("PRODUCER"));
		CreditCard res;
		final Actor actor;

		actor = this.actorService.findByPrincipal();

		if (creditCard.getId() != 0)
			Assert.isTrue(actor.getCreditCard().getId() == creditCard.getId(), "You cannot edit info of other credit cards");

		if (actor.getCreditCard() == null)
			Assert.isTrue(creditCard.getId() == 0, "You cannot edit info of other credit cards");

		res = creditCard;

		if (!this.isCreditCardDateValid(res))
			bindingResult.rejectValue("month", "creditcard.error.dates");

		if (!this.isCreditCardBrandValid(res))
			bindingResult.rejectValue("brand", "creditcard.error.brand");

		this.validator.validate(res, bindingResult);
		return res;

	}
	// Other business methods -------------------------------

	/**
	 * Given a credit card this method checks if its brand
	 * is VISA, MASTERCARD, DISCOVER, DINNERS, or AMEX
	 * 
	 * @param creditCard
	 *            The credit card to be checked.
	 * @return The result of the check.
	 */
	public boolean isCreditCardBrandValid(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		final String[] brands = new String[] {
			"VISA", "MASTERCARD", "DISCOVER", "DINNERS", "AMEX"
		};

		for (final String s : brands)
			if (creditCard.getBrand().toUpperCase().equals(s))
				return true;

		return false;
	}

	/**
	 * Get the provided credit card's number masked to allow
	 * display of it in views.
	 * 
	 * @param creditCard
	 *            The credit card whose number must be masked
	 * @return A string with the masked credit card's number
	 */

	public String getMaskedNumber(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		return creditCard.getNumber().subSequence(0, 4) + StringUtils.repeat("*", 8) + creditCard.getNumber().substring(12);
	}

	/**
	 * Given a credit card this method checks if its expiration date
	 * is at least one day in the future.
	 * 
	 * @param creditCard
	 *            The credit card to be checked.
	 * @return The result of the check.
	 */
	public boolean isCreditCardDateValid(final CreditCard creditCard) {
		Assert.notNull(creditCard);

		final Calendar today = Calendar.getInstance();
		if (creditCard.getYear() == today.get(Calendar.YEAR))
			if (creditCard.getMonth() < today.get(Calendar.MONTH) + 1)
				return false;
			else
				return true;
		else if (creditCard.getYear() > today.get(Calendar.YEAR))
			return true;
		else
			return false;
	}

	public CreditCard findActorCreditCard() {
		final Actor actor;
		CreditCard creditCard;

		actor = this.actorService.findByPrincipal();
		Assert.notNull(actor, "You are not logged as an User");

		creditCard = this.creditCardRepository.findCreditCardByActorId(actor.getId());
		Assert.notNull(creditCard);

		return creditCard;
	}

	public CreditCard findCreditCardToEdit(final int creditCardId) {
		Actor actor;
		CreditCard creditCard;

		actor = this.actorService.findByPrincipal();
		Assert.notNull(actor, "You are not logged as a user");

		creditCard = this.findOne(creditCardId);
		Assert.notNull(creditCard, "The credit card does not exist");
		Assert.isTrue(this.findActorCreditCard().equals(creditCard), "This is not your credit card");

		return creditCard;
	}
}
