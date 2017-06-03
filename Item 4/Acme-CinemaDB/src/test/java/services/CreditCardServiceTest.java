
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	@Autowired
	private CreditCardService	creditCardService;


	@Test
	public void saveCreditCardDriver() {
		final Object testingData[][] = {
			{
				// An administrator is not allowed to save a credit card -> Exception
				"admin", "Javier", "MASTERCARD", "869964971792152", 2017, 8, 123, IllegalArgumentException.class
			}, {
				// An unauthenticated user is not allowed to save a credit card -> Exception
				null, "Javier", "MASTERCARD", "869964971792152", 2017, 8, 123, IllegalArgumentException.class
			}, {
				// Brand name is not valid -> Exception
				"user1", "Javier", "MYOWNBRAND", "869964971792152", 2017, 8, 123, IllegalArgumentException.class
			}, {
				// OK
				"user1", "Javier", "MASTERCARD", "869964971792152", 2017, 8, 123, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.saveCreditCardTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);
	}

	@Test
	public void editCreditCardDriver() {
		final Object testingData[][] = {
			{
				// An user edit his credit card with an invalid CVV -> Exception
				"user3", 240, 98, ConstraintViolationException.class
			}, {
				// OK
				"user3", 240, 120, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editCreditCardTemplate((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	// Ancillary methods ------------------------------------------------------

	protected void saveCreditCardTemplate(final String username, final String holder, final String brand, final String number, final int year, final int month, final int cvv, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			CreditCard creditCard;
			final CreditCard saved;

			this.authenticate(username);

			creditCard = this.creditCardService.create();
			creditCard.setHolder(holder);
			creditCard.setBrand(brand);
			creditCard.setNumber(number);
			creditCard.setYear(year);
			creditCard.setMonth(month);
			creditCard.setCvv(cvv);

			Assert.isTrue(this.creditCardService.isCreditCardBrandValid(creditCard));
			Assert.isTrue(this.creditCardService.isCreditCardDateValid(creditCard));

			saved = this.creditCardService.save(creditCard);
			this.creditCardService.flush();

			this.unauthenticate();

			Assert.isTrue(saved.getId() > 0);
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editCreditCardTemplate(final String username, final int creditCardId, final int cvv, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			CreditCard creditCard;
			final CreditCard saved;

			this.authenticate(username);

			creditCard = this.creditCardService.findOne(creditCardId);
			creditCard.setCvv(cvv);

			saved = this.creditCardService.save(creditCard);
			this.creditCardService.flush();

			this.unauthenticate();

			Assert.isTrue(saved.getId() > 0);
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
