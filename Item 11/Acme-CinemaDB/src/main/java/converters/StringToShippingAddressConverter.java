
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ShippingAddressRepository;
import domain.ShippingAddress;

@Component
@Transactional
public class StringToShippingAddressConverter implements Converter<String, ShippingAddress> {

	@Autowired
	ShippingAddressRepository	shippingAddressRepository;


	@Override
	public ShippingAddress convert(final String text) {
		ShippingAddress res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.shippingAddressRepository.findOne(id);
			}
		} catch (final Throwable th) {
			throw new IllegalArgumentException(th);
		}

		return res;
	}
}
