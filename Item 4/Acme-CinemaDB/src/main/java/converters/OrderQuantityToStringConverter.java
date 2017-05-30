
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.OrderQuantity;

@Component
@Transactional
public class OrderQuantityToStringConverter implements Converter<OrderQuantity, String> {

	@Override
	public String convert(final OrderQuantity orderQuantity) {
		String res;

		if (orderQuantity == null)
			res = null;
		else
			res = String.valueOf(orderQuantity.getId());
		return res;
	}
}
