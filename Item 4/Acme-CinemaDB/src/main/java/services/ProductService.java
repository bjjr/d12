
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProductRepository;
import security.Authority;
import domain.Product;

@Service
@Transactional
public class ProductService {

	// Managed repository ---------------------------

	@Autowired
	private ProductRepository	productRepository;

	// Supporting services --------------------------

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods --------------------------

	public Product save(final Product product) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER) || this.actorService.checkAuthority(Authority.PRODUCER));

		Product res;

		res = this.productRepository.save(product);

		return res;
	}

	public Product findOne(final int productId) {
		Assert.isTrue(productId != 0);

		Product res;

		res = this.productRepository.findOne(productId);

		return res;
	}

	// Other business methods -----------------------

	public Collection<Product> findProductsByOrder(final int orderId) {
		Assert.isTrue(orderId != 0);

		Collection<Product> res;

		res = this.productRepository.findProductsByOrder(orderId);

		return res;
	}
}
