
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.OrderUserRepository;
import security.Authority;
import domain.OrderQuantity;
import domain.OrderUser;
import domain.Product;
import domain.ShippingAddress;
import domain.User;

@Service
@Transactional
public class OrderUserService {

	// Managed repository ---------------------------

	@Autowired
	private OrderUserRepository		orderUserRepository;

	// Managed services -----------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserService				userService;

	@Autowired
	private ProductService			productService;

	@Autowired
	private OrderQuantityService	orderQuantityService;

	@Autowired
	private ShippingAddressService	shippingAddressService;

	// Validator ------------------------------------

	@Autowired
	private Validator				validator;


	// Constructors ---------------------------------

	public OrderUserService() {
		super();
	}

	// Simple CRUD methods --------------------------

	public OrderUser create() {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));

		OrderUser res;
		User principal;

		res = new OrderUser();
		principal = this.userService.findByPrincipal();

		res.setTotal(3.75);
		res.setUser(principal);
		res.setFinished(false);
		res.setStatus(0);
		res.setOrderQuantities(new HashSet<OrderQuantity>());

		return res;
	}

	public OrderUser save(final OrderUser orderUser) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER) || this.actorService.checkAuthority(Authority.ADMIN));
		Assert.notNull(orderUser);

		OrderUser res;

		res = this.orderUserRepository.save(orderUser);

		return res;
	}

	public void delete(final OrderUser orderUser) {
		Assert.notNull(orderUser);
		Assert.isTrue(!orderUser.getFinished(), "Only unfinished orders can be deleted"); // Only unfinished orders can be deleted

		this.orderUserRepository.delete(orderUser);
	}

	public OrderUser findOne(final int orderUserId) {
		Assert.isTrue(orderUserId != 0);

		OrderUser res;

		res = this.orderUserRepository.findOne(orderUserId);

		return res;
	}

	// Other business methods -----------------------

	public Collection<OrderUser> findOrdersByUser(final Integer status) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));

		Collection<OrderUser> res;
		User principal;

		principal = this.userService.findByPrincipal();
		res = null;

		if (status == null)
			res = this.orderUserRepository.findOrdersByUser(principal.getId());
		else if (status == 0)
			res = this.orderUserRepository.findInProgressOrdersByUser(principal.getId());
		else if (status == 1)
			res = this.orderUserRepository.findSentOrdersByUser(principal.getId());
		else if (status == 2)
			res = this.orderUserRepository.findCancelledOrdersByUser(principal.getId());

		return res;
	}

	public Collection<OrderUser> findOrders(final Integer status) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.ADMIN));

		Collection<OrderUser> res;

		res = null;

		if (status == null)
			res = this.orderUserRepository.findAllOrders();
		else if (status == 0)
			res = this.orderUserRepository.findInProgressOrders();
		else if (status == 1)
			res = this.orderUserRepository.findSentOrders();
		else if (status == 2)
			res = this.orderUserRepository.findCancelledOrders();

		return res;
	}

	public Collection<OrderUser> findOrdersByShippingAddress(final int shippingAddressId) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));
		Assert.isTrue(shippingAddressId != 0);

		Collection<OrderUser> res;

		res = this.orderUserRepository.findOrdersByShippingAddress(shippingAddressId);

		return res;
	}

	/**
	 * Finds an unfinished order done by the User. If the User does not have one,
	 * then a new one is created in order to buy products.
	 * This could be considered as a shopping cart.
	 * 
	 * @return The unfinished order or a new unfinished order
	 */

	public OrderUser findUnfinishedOrder() {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));

		List<OrderUser> orders;
		OrderUser res;
		User principal;

		principal = this.userService.findByPrincipal();
		orders = new LinkedList<>(this.orderUserRepository.findUnfinishedOrder(principal.getId()));

		if (orders.isEmpty())
			res = this.create();
		else
			res = orders.get(0);

		return res;
	}

	public void addProduct(final int productId) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));

		Product product;
		Collection<Product> orderProducts;
		OrderUser orderUser;
		OrderQuantity orderQuantity, savedOrderQuantity;

		product = this.productService.findOne(productId);

		Assert.isTrue(product.getStock() > 0, "You can't add a product with no stock available"); // Check selected product's stock

		orderUser = this.findUnfinishedOrder();

		if (orderUser.getId() != 0) {
			orderProducts = this.productService.findProductsByOrder(orderUser.getId());
			Assert.isTrue(!orderProducts.contains(product), "You can't add the same product in your order, modify the quantity.");
		}

		orderQuantity = this.orderQuantityService.create(product);

		savedOrderQuantity = this.orderQuantityService.save(orderQuantity);

		orderUser.getOrderQuantities().add(savedOrderQuantity);

		this.updateTotal(orderUser);
	}

	public void removeProduct(final int orderQuantityId) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));

		OrderUser orderUser, saved;
		OrderQuantity orderQuantity;

		orderUser = this.findUnfinishedOrder();
		orderQuantity = this.orderQuantityService.findOne(orderQuantityId);

		Assert.isTrue(orderUser.getOrderQuantities().contains(orderQuantity), "You can't remove a product that does not exist in your shopping cart");

		orderUser.getOrderQuantities().remove(orderQuantity);

		saved = this.updateTotal(orderUser);

		if (saved.getOrderQuantities().isEmpty())
			this.delete(saved);
	}

	public OrderUser updateTotal(final OrderUser orderUser) {
		Double total;
		OrderUser res;

		total = 3.75;

		for (final OrderQuantity oq : orderUser.getOrderQuantities())
			total += oq.getQuantity() * oq.getProduct().getPrice();

		orderUser.setTotal(total);

		res = this.save(orderUser);

		return res;
	}

	public OrderUser reconstruct(final OrderUser orderUser, final BindingResult binding) {
		Assert.notNull(orderUser);
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));

		OrderUser res, unfinished;
		Collection<ShippingAddress> shippingAddresses;

		unfinished = this.findUnfinishedOrder();
		shippingAddresses = this.shippingAddressService.findShippingAddressesByUser();

		Assert.isTrue(orderUser.getId() == unfinished.getId(), "You are not modifying your shopping cart"); // Prevent post-hacking via id

		if (!shippingAddresses.contains(orderUser.getShippingAddress()))
			binding.rejectValue("shippingAddress", "order.shippingAddress.error");

		res = orderUser;
		res.setOrderQuantities(unfinished.getOrderQuantities());
		res.setFinished(unfinished.getFinished());
		res.setUser(unfinished.getUser());

		this.validator.validate(res, binding);

		return res;
	}
	/**
	 * Given a validated order, this method updates the products' stocks and finishes the order
	 * 
	 * @param orderUser
	 */

	public OrderUser finishOrder(final OrderUser orderUser) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));

		Date moment;
		OrderUser res;

		for (final OrderQuantity oq : orderUser.getOrderQuantities())
			Assert.isTrue(this.orderQuantityService.checkProductStock(oq)); // Check if the quantities in the order can be satisfied.

		for (final OrderQuantity oq : orderUser.getOrderQuantities()) {
			Product product;
			int remainingStock;

			product = oq.getProduct();
			remainingStock = product.getStock() - oq.getQuantity(); // Update products' stocks
			product.setStock(remainingStock);
			this.productService.save(product);
		}

		moment = new Date(System.currentTimeMillis() - 1000);
		orderUser.setMoment(moment);
		orderUser.setFinished(true);

		res = this.updateTotal(orderUser);

		return res;
	}

	/**
	 * Modifies the status of the specified order. The order must have the "In progress" status (0).
	 * 
	 * @param orderUser
	 *            The order to be modified.
	 * @param status
	 *            The new status. 1 establishes "Sent" status. 2 establishes the "Cancelled" status.
	 */

	public void setOrderStatus(final OrderUser orderUser, final int status) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.ADMIN));
		Assert.isTrue(orderUser.getStatus() == 0);
		Assert.isTrue(status == 1 || status == 2);

		orderUser.setStatus(status);

		this.save(orderUser);
	}
}
