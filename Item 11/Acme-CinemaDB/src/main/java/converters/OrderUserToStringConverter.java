
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.OrderUser;

@Component
@Transactional
public class OrderUserToStringConverter implements Converter<OrderUser, String> {

	@Override
	public String convert(final OrderUser orderUser) {
		String res;

		if (orderUser == null)
			res = null;
		else
			res = String.valueOf(orderUser.getId());
		return res;
	}
}
