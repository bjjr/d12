
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Product;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProductServiceTest extends AbstractTest {

	@Autowired
	private ProductService	productService;


	@Test
	public void createProductDriver() {
		final Object testingData[][] = {
			{
				// An administrator is not allowed to create a product -> Exception
				"admin", 297, IllegalArgumentException.class
			}, {
				// An unauthenticated user is not allowed to create a product -> Exception
				null, 297, IllegalArgumentException.class
			}, {
				// A producer but a product over a content that doesnt belong to him -> Exception
				"producer2", 297, IllegalArgumentException.class
			}, {
				// OK
				"producer3", 297, null
			}, {
				// An user cant create a product
				"user1", 297, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createProductTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void editProductDriver() {
		final Object testingData[][] = {
			{
				// An administrator is not allowed to create a product -> Exception
				"admin", 400, IllegalArgumentException.class
			}, {
				// An unauthenticated user is not allowed to create a product -> Exception
				null, 400, IllegalArgumentException.class
			}, {
				// A producer but a product over a content that doesnt belong to him -> Exception
				"producer2", 400, IllegalArgumentException.class
			}, {
				// OK
				"producer3", 400, null
			}, {
				// OK
				"user1", 400, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editProductTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void createProductTemplate(final String username, final int contentId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final Product product = this.productService.create(contentId);

			product.setName("Camiseta");
			product.setPrice(12);
			product.setStock(11);
			product.setPicture("http://wallpaper-gallery.net/images/picture/picture-14.jpg");

			this.productService.save(product);

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editProductTemplate(final String username, final int productId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);

			final Product product = this.productService.findOne(productId);

			product.setStock(12);

			this.productService.save(product);

			this.unauthenticate();
		} catch (final Throwable th) {
			caught = th.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
