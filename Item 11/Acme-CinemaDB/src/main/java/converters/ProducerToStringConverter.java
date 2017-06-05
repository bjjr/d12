
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Producer;

@Component
@Transactional
public class ProducerToStringConverter implements Converter<Producer, String> {

	@Override
	public String convert(final Producer producer) {
		String res;

		if (producer == null)
			res = null;
		else
			res = String.valueOf(producer.getId());

		return res;
	}

}
