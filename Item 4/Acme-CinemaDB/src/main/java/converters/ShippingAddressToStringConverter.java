
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ShippingAddress;

@Component
@Transactional
public class ShippingAddressToStringConverter implements Converter<ShippingAddress, String> {

	@Override
	public String convert(final ShippingAddress shippingAddress) {
		String res;

		if (shippingAddress == null)
			res = null;
		else
			res = String.valueOf(shippingAddress.getId());
		return res;
	}
}
