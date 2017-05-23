
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ShippingAddressRepository;
import security.Authority;
import domain.OrderUser;
import domain.ShippingAddress;
import domain.User;

@Service
@Transactional
public class ShippingAddressService {

	// Managed repository ---------------------------

	@Autowired
	private ShippingAddressRepository	shippingAddressRepository;

	// Supporting services --------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private UserService					userService;

	@Autowired
	private OrderUserService			orderUserService;

	// Validator ------------------------------------

	@Autowired
	private Validator					validator;


	// Simple CRUD methods --------------------------

	public ShippingAddress create() {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));

		ShippingAddress res;
		User principal;

		res = new ShippingAddress();
		principal = this.userService.findByPrincipal();

		res.setName(principal.getName());
		res.setSurname(principal.getSurname());
		res.setCountry(principal.getCountry());
		res.setPhone(principal.getPhone());

		return res;
	}

	public ShippingAddress save(final ShippingAddress shippingAddress) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));
		Assert.notNull(shippingAddress);

		ShippingAddress res;

		res = this.shippingAddressRepository.save(shippingAddress);

		return res;
	}

	public void delete(final ShippingAddress shippingAddress) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));
		Assert.notNull(shippingAddress);
		Assert.isTrue(this.shippingAddressRepository.exists(shippingAddress.getId()));

		this.checkOwnership(shippingAddress);

		Collection<OrderUser> orders;

		orders = this.orderUserService.findOrdersByShippingAddress(shippingAddress.getId());

		for (final OrderUser o : orders) {
			o.setShippingAddress(null); // Delete shipping address references in orders
			this.orderUserService.save(o);
		}

		this.shippingAddressRepository.delete(shippingAddress);
	}

	public ShippingAddress findOne(final int shippingAddressId) {
		Assert.isTrue(shippingAddressId != 0);

		ShippingAddress res;

		res = this.shippingAddressRepository.findOne(shippingAddressId);

		this.checkOwnership(res);

		return res;
	}

	// Other business methods -----------------------

	public ShippingAddress reconstruct(final ShippingAddress shippingAddress, final BindingResult binding) {
		Assert.notNull(shippingAddress);
		Assert.isTrue(shippingAddress.getId() == 0); //Shipping addresses can only be created, not modified.

		ShippingAddress res;
		User principal;

		res = shippingAddress;
		principal = this.userService.findByPrincipal();

		res.setUser(principal);

		this.validator.validate(res, binding);

		return res;
	}

	private void checkOwnership(final ShippingAddress shippingAddress) {
		User principal, saUser;

		principal = this.userService.findByPrincipal();
		saUser = shippingAddress.getUser();

		Assert.isTrue(principal.equals(saUser), "You did not register this shipping address");
	}

	public Collection<ShippingAddress> findShippingAddressesByUser() {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER));

		Collection<ShippingAddress> res;
		User principal;

		principal = this.userService.findByPrincipal();
		res = this.shippingAddressRepository.findShippingAddressesByUser(principal.getId());

		return res;
	}

}
