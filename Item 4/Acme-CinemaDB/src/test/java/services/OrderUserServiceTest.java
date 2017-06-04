
package services;

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
import domain.Product;
import domain.ShippingAddress;
import domain.User;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OrderUserServiceTest extends AbstractTest {

	// System under test ----------------------------

	@Autowired
	private OrderUserService		orderUserService;

	// Supporting services --------------------------

	@Autowired
	private ProductService			productService;

	@Autowired
	private UserService				userService;

	@Autowired
	private ShippingAddressService	shippingAddressService;


	// Set up ---------------------------------------

	// Tests ----------------------------------------

	/*
	 * Use case: Add a product to the shopping cart
	 * Functional requirements:
	 * - Realizar un pedido.
	 */

	@Test
	public void addProductDriver() {
		final Object testingData[][] = {
			// User selects a product with stock
			{
				"user3", 396, null
			},
			// User selects a product without stock (GET-Hacking)
			{
				"user3", 400, IllegalArgumentException.class
			},
			// A non logged user tries to add a product
			{
				null, 399, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.addProductTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/*
	 * Use case: Finish the current order (Shipping cart)
	 * Functional requirements:
	 * - Realizar un pedido
	 */

	@Test
	public void finishOrderDriver() {
		final Object testingData[][] = {
			// An user with a valid credit card finishes his/her order
			{
				"user3", 389, null
			},
			// An user does not provide a shipping address
			{
				"user2", 0, IllegalArgumentException.class
			},
			// An user inputs one of another user's shipping addresses
			{
				"user3", 388, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.finishOrderTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/*
	 * Use case: Change the order status' as the administrator
	 * Functional Requirements:
	 * - Marcar como ENVIADO o CANCELADO un pedido con estado EN PROCESO.
	 */

	@Test
	public void changeStatusDriver() {
		final Object testingData[][] = {
			// The admin tries to change an order status' to IN PROGRESS
			{
				414, 0, IllegalArgumentException.class
			},
			// The admin tries to change the status of a sent order.
			{
				415, 2, IllegalArgumentException.class
			},
			// The admin changes the status of an order with status IN PROGRESS.
			{
				414, 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.changeStatusTemplate((int) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ----------------------------

	protected void addProductTemplate(final String username, final int productId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			OrderUser orderUser;
			Product product;
			User user;

			this.orderUserService.addProduct(productId);

			orderUser = this.orderUserService.findUnfinishedOrder();
			product = this.productService.findOne(productId);
			user = this.userService.findByPrincipal();

			Assert.isTrue(!orderUser.getFinished());
			Assert.isNull(orderUser.getMoment());
			Assert.isNull(orderUser.getShippingAddress());
			Assert.isTrue(orderUser.getTotal() == product.getPrice() + 3.75);
			Assert.isTrue(orderUser.getStatus() == 0);
			Assert.isTrue(orderUser.getUser().equals(user));

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void finishOrderTemplate(final String username, final int shippingAddressId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			ShippingAddress shippingAddress;
			OrderUser orderUser, saved;
			DataBinder dataBinder;
			BindingResult binding;
			User principal;

			shippingAddress = this.shippingAddressService.findOne(shippingAddressId);
			orderUser = this.orderUserService.findUnfinishedOrder();

			if (orderUser.getOrderQuantities().isEmpty())
				this.orderUserService.addProduct(398);

			orderUser = this.orderUserService.findUnfinishedOrder();

			dataBinder = new DataBinder(orderUser, "orderUser");
			binding = dataBinder.getBindingResult();

			orderUser.setShippingAddress(shippingAddress);

			this.orderUserService.reconstruct(orderUser, binding);

			Assert.isTrue(!binding.hasErrors());

			saved = this.orderUserService.finishOrder(orderUser);
			principal = this.userService.findByPrincipal();

			Assert.isTrue(saved.getId() != 0);
			Assert.isTrue(saved.getFinished());
			Assert.notNull(saved.getMoment());
			Assert.notNull(saved.getUser());
			Assert.notNull(saved.getShippingAddress());
			Assert.isTrue(saved.getStatus() == 0);
			Assert.notEmpty(saved.getOrderQuantities());
			Assert.isTrue(saved.getUser().equals(principal));

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void changeStatusTemplate(final int orderUserId, final int status, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate("admin");

			OrderUser orderUser;

			orderUser = this.orderUserService.findOne(orderUserId);

			this.orderUserService.setOrderStatus(orderUser, status);

			orderUser = this.orderUserService.findOne(orderUserId);

			Assert.isTrue(orderUser.getStatus() == status);

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
