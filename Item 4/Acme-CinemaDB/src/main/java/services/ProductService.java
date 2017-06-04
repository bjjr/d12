
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProductRepository;
import security.Authority;
import domain.Content;
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

	@Autowired
	private ContentService		contentService;

	@Autowired
	private Validator			validator;

	@Autowired
	private ProducerService		producerService;


	// Simple CRUD methods --------------------------

	public Product create(final int contentId) {
		final Content content = this.contentService.findOne(contentId);

		final Collection<Content> allContentByPrincipal = this.contentService.findContentByProducerId(this.producerService.findByPrincipal().getId());
		Assert.isTrue(allContentByPrincipal.contains(content), "ProductService.create: You cant create a product in a content that doesnt belong to you");

		final Product product = new Product();
		product.setName("");
		product.setPrice(0.);
		product.setStock(0);
		product.setPicture("");
		product.setIdcode(this.generateIdCode());

		product.setContent(content);

		return product;
	}

	public Product save(final Product product) {
		Assert.isTrue(this.actorService.checkAuthority(Authority.USER) || this.actorService.checkAuthority(Authority.PRODUCER));

		if (this.actorService.checkAuthority(Authority.PRODUCER)) {
			final Content content = this.contentService.findOne(product.getContent().getId());
			final Collection<Content> allContentByPrincipal = this.contentService.findContentByProducerId(this.producerService.findByPrincipal().getId());
			Assert.isTrue(allContentByPrincipal.contains(content), "ProductService.create: You cant create a product in a content that doesnt belong to you");
		}

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

	public Collection<Product> findAll() {
		return this.productRepository.findAll();
	}

	// Other business methods -----------------------

	public Product reconstruct(final Product product, final BindingResult bindingResult) {
		final Product reconstructed = product;

		if (product.getId() == 0)
			reconstructed.setIdcode(this.generateIdCode());
		else {
			final Product aux = this.productRepository.findOne(product.getId());

			reconstructed.setIdcode(aux.getIdcode());
			reconstructed.setContent(aux.getContent());
		}

		this.validator.validate(reconstructed, bindingResult);

		return reconstructed;
	}

	public Collection<Product> findProductsByOrder(final int orderId) {
		Assert.isTrue(orderId != 0);

		Collection<Product> res;

		res = this.productRepository.findProductsByOrder(orderId);

		return res;
	}

	public Collection<Product> findAllProductsByContentId(final int contentId) {
		return this.productRepository.findAllProductsByContentId(contentId);
	}

	private String generateIdCode() {
		final List<String> allUniqueIDs = new ArrayList<>();

		for (final Product p : this.findAll())
			allUniqueIDs.add(p.getIdcode());

		String uniqueID = null;

		do {
			final String uniqueString = RandomStringUtils.randomAlphabetic(5).toUpperCase();
			final String uniqueNumber = RandomStringUtils.randomNumeric(5);

			uniqueID = uniqueString + "-" + uniqueNumber;
		} while (allUniqueIDs.contains(uniqueID));

		return uniqueID;
	}
}
