
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProductRepository;
import domain.Product;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository	productRepository;


	public Collection<Product> findAll() {
		return this.productRepository.findAll();
	}

	public Product save(final Product product) {
		Assert.notNull(product);

		final Product productSaved = this.productRepository.save(product);

		return productSaved;
	}

	public void delete(final Product product) {
		this.productRepository.delete(product);
	}

	public Product findOne(final int productId) {
		return this.productRepository.findOne(productId);
	}

	public Collection<Product> findAllProductsByContentId(final int contentId) {
		return this.productRepository.findAllProductsByContentId(contentId);
	}

}
