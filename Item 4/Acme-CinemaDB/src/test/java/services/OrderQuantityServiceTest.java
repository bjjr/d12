
package services;

import java.util.ArrayList;
import java.util.List;

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
import domain.OrderQuantity;
import domain.OrderUser;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OrderQuantityServiceTest extends AbstractTest {

	// System under test ----------------------------

	@Autowired
	private OrderQuantityService	orderQuantityService;

	// Supporting services --------------------------

	@Autowired
	private OrderUserService		orderUserService;

	// Set up ---------------------------------------

	private OrderQuantity			orderQuantity1;
	private OrderQuantity			orderQuantity2;


	// Creating a shopping cart with a product

	@Override
	public void setUp() {
		this.authenticate("user2");

		OrderUser orderUser;
		List<OrderQuantity> orderQuantities;

		this.orderUserService.addProduct(396);
		this.orderUserService.addProduct(397);

		orderUser = this.orderUserService.findUnfinishedOrder();

		orderQuantities = new ArrayList<>(orderUser.getOrderQuantities());

		this.orderQuantity1 = orderQuantities.get(0);
		this.orderQuantity2 = orderQuantities.get(1);

		this.unauthenticate();
	}

	// Tests ----------------------------------------

	/*
	 * Use case: Select an order's product amount.
	 * Functional requirements:
	 * - Realizar un pedido.
	 */

	@Test
	public void editProductQuantityDriver() {
		final Object testingData[][] = {
			// User inputs a valid amount
			{
				2, null
			},
			// User inputs a quantity greater than the available stock
			{
				30, IllegalArgumentException.class
			},
			// User inputs a quantity lower than 1
			{
				-5, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editProductQuantityTemplate((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/*
	 * Use case: Remove a product from the shipping cart
	 * Functional requirements:
	 * - Realizar un pedido
	 */

	@Test
	public void removeProductQuantityDriver() {
		final Object testingData[][] = {
			// User removes a product from the shopping cart
			{
				"user2", this.orderQuantity1.getId(), null
			},
			// User tries to remove a product not present in his/her shopping cart
			{
				"user3", this.orderQuantity2.getId(), IllegalArgumentException.class
			},
			// An unidentified person tries to remove a product in a shopping cart
			{
				null, this.orderQuantity2.getId(), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.removeProductQuantityTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ----------------------------

	protected void editProductQuantityTemplate(final int quantity, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate("user2");

			OrderQuantity reconstructed;
			DataBinder dataBinder;
			BindingResult binding;

			dataBinder = new DataBinder(this.orderQuantity1, "orderQuantity");

			binding = dataBinder.getBindingResult();

			this.orderQuantity1.setQuantity(quantity);

			reconstructed = this.orderQuantityService.reconstruct(this.orderQuantity1, binding);

			Assert.isTrue(!binding.hasErrors());

			this.orderQuantityService.save(reconstructed);

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void removeProductQuantityTemplate(final String username, final int orderQuantityId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			this.orderUserService.removeProduct(orderQuantityId);

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
