
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.transaction.annotation.Transactional;

import repositories.OrderQuantityRepository;
import domain.OrderQuantity;

@Transactional
public class StringToOrderQuantityConverter implements Converter<String, OrderQuantity> {

	@Autowired
	OrderQuantityRepository	orderQuantityRepository;


	@Override
	public OrderQuantity convert(final String text) {
		OrderQuantity res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.orderQuantityRepository.findOne(id);
			}
		} catch (final Throwable th) {
			throw new IllegalArgumentException(th);
		}

		return res;
	}

}
