
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OrderUserRepository;
import security.Authority;
import domain.OrderUser;

@Service
@Transactional
public class OrderUserService {

	// Managed repository ---------------------------

	@Autowired
	private OrderUserRepository	orderUserRepository;

	// Managed services -----------------------------

	@Autowired
	private ActorService		actorService;


	// Constructors ---------------------------------

	public OrderUserService() {
		super();
	}

	// Simple CRUD methods --------------------------

	public OrderUser save(final OrderUser orderUser) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER) || this.actorService.checkAuthority(Authority.ADMIN));
		Assert.notNull(orderUser);

		OrderUser res;

		res = this.orderUserRepository.save(orderUser);

		return res;
	}

	// Other business methods -----------------------

	public Collection<OrderUser> findOrdersByUser(final int userId) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));
		Assert.isTrue(userId != 0);

		Collection<OrderUser> res;

		res = this.orderUserRepository.findOrdersByUser(userId);

		return res;
	}

	public Collection<OrderUser> findOrdersByShippingAddress(final int shippingAddressId) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));
		Assert.isTrue(shippingAddressId != 0);

		Collection<OrderUser> res;

		res = this.orderUserRepository.findOrdersByShippingAddress(shippingAddressId);

		return res;
	}
}
