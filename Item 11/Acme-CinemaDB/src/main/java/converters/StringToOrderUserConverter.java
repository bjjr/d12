
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OrderUserRepository;
import domain.OrderUser;

@Component
@Transactional
public class StringToOrderUserConverter implements Converter<String, OrderUser> {

	@Autowired
	OrderUserRepository	orderUserRepository;


	@Override
	public OrderUser convert(final String text) {
		OrderUser res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.orderUserRepository.findOne(id);
			}
		} catch (final Throwable th) {
			throw new IllegalArgumentException(th);
		}

		return res;
	}

}
