
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Product;

@Component
@Transactional
public class ProductToStringConverter implements Converter<Product, String> {

	@Override
	public String convert(final Product product) {
		String res;

		if (product == null)
			res = null;
		else
			res = String.valueOf(product.getId());
		return res;
	}
}
