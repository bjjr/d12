
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import utilities.AbstractTest;
import domain.OrderUser;
import domain.ShippingAddress;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ShippingAddressServiceTest extends AbstractTest {

	// System under test ----------------------------

	@Autowired
	private ShippingAddressService	shippingAddressService;

	// Supporting services --------------------------

	@Autowired
	private OrderUserService		orderUserService;


	// Tests ----------------------------------------

	/*
	 * Use case: Register a new shipping address
	 * Functional requirements:
	 * - Añadir y eliminar direcciones de envío
	 */

	@Test
	public void shippingAddressRegistrationDriver() {
		final Object testingData[][] = {
			// User inputs valid data
			{
				"+34123456789", "test", null
			},
			// User inputs an invalid phone number
			{
				"test", "test", IllegalArgumentException.class
			},
			// User inputs an ECMA Script
			{
				"+34123456789", "<script>alert('ALERT');</script>", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.shippingAddressRegistrationTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/*
	 * Use case: Delete an existing shipping address
	 * Functional requirements:
	 * - Añadir y eliminar direcciones de envío
	 */

	@Test
	public void shippingAddressDeletionDriver() {
		final Object testingData[][] = {
			// User deletes one of his/her shipping addresses
			{
				"user3", 389, null
			},
			// User tries to remove another user's shipping address
			{
				"user2", 390, IllegalArgumentException.class
			},
			// An unidentified person tries to delete a shipping address
			{
				null, 391, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.shippingAddressDeletionTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ---------------------------

	protected void shippingAddressRegistrationTemplate(final String phone, final String country, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate("user1");

			ShippingAddress shippingAddress, reconstructed, saved;
			DataBinder dataBinder;
			BindingResult binding;

			shippingAddress = this.shippingAddressService.create();

			shippingAddress.setSaName("test");
			shippingAddress.setName("test");
			shippingAddress.setSurname("test");
			shippingAddress.setCountry(country);
			shippingAddress.setAddress("test");
			shippingAddress.setCity("test");
			shippingAddress.setZipcode("test");
			shippingAddress.setPhone(phone);

			dataBinder = new DataBinder(shippingAddress, "shippingAddress");
			binding = dataBinder.getBindingResult();

			reconstructed = this.shippingAddressService.reconstruct(shippingAddress, binding);

			Assert.isTrue(!binding.hasErrors());

			saved = this.shippingAddressService.save(reconstructed);
			Assert.isTrue(saved.getId() != 0);

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void shippingAddressDeletionTemplate(final String username, final int shippingAddressId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			ShippingAddress shippingAddress;
			Collection<OrderUser> orders;

			shippingAddress = this.shippingAddressService.findOne(shippingAddressId);

			this.shippingAddressService.delete(shippingAddress);

			orders = this.orderUserService.findOrdersByShippingAddress(shippingAddressId);

			for (final OrderUser o : orders)
				Assert.isNull(this.orderUserService.findOne(o.getId()).getShippingAddress());

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
