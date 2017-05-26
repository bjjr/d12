
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.OrderQuantityRepository;
import security.Authority;
import domain.OrderQuantity;
import domain.OrderUser;
import domain.Product;

@Service
@Transactional
public class OrderQuantityService {

	// Managed repository ---------------------------

	@Autowired
	private OrderQuantityRepository	orderQuantityRepository;

	// Supporting services --------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private OrderUserService		orderUserService;

	// Validator ------------------------------------

	@Autowired
	private Validator				validator;


	// Simple CRUD methods --------------------------

	public OrderQuantity create(final Product product) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));

		OrderQuantity res;

		res = new OrderQuantity();

		res.setQuantity(1);
		res.setProduct(product);

		return res;
	}

	public OrderQuantity save(final OrderQuantity orderQuantity) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));
		Assert.notNull(orderQuantity);

		OrderQuantity res;

		res = this.orderQuantityRepository.save(orderQuantity);

		return res;
	}

	public void delete(final OrderQuantity orderQuantity) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));
		Assert.notNull(orderQuantity);
		Assert.isTrue(this.orderQuantityRepository.exists(orderQuantity.getId()));

		this.orderQuantityRepository.delete(orderQuantity);
	}

	public OrderQuantity findOne(final int orderQuantityId) {
		Assert.isTrue(orderQuantityId != 0);

		OrderQuantity res;

		res = this.orderQuantityRepository.findOne(orderQuantityId);

		return res;
	}

	// Other business methods -----------------------

	public OrderQuantity reconstruct(final OrderQuantity orderQuantity, final BindingResult binding) {
		Assert.notNull(orderQuantity);

		OrderQuantity res, existing;
		OrderUser orderUser;

		existing = this.findOne(orderQuantity.getId());
		orderUser = this.orderUserService.findUnfinishedOrder();

		Assert.isTrue(orderUser.getOrderQuantities().contains(existing), "The id has been modified"); // Prevent orderQuantity's id modifications

		res = orderQuantity;
		res.setProduct(existing.getProduct());

		if (!this.checkProductStock(res))
			binding.rejectValue("quantity", "orderQuantity.stock.error"); // If the specified quantity in the form is greater than the available stock, inform the user.

		this.validator.validate(res, binding);

		return res;
	}

	/**
	 * Checks if the selected quantity is lower or equal to the available stock of the product.
	 * 
	 * @param orderQuantity
	 *            The number of products selected.
	 * @return True if the quantity is lower or equal to the product's stock. False if otherwise.
	 */

	public Boolean checkProductStock(final OrderQuantity orderQuantity) {
		Assert.notNull(orderQuantity);

		Boolean res;

		res = orderQuantity.getQuantity() <= orderQuantity.getProduct().getStock();

		return res;
	}
}
