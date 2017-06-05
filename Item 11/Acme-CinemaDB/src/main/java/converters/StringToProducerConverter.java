
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProducerRepository;
import domain.Producer;

@Component
@Transactional
public class StringToProducerConverter implements Converter<String, Producer> {

	@Autowired
	ProducerRepository	producerRepository;


	@Override
	public Producer convert(final String text) {
		Producer res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.producerRepository.findOne(id);
			}
		} catch (final Throwable th) {
			throw new IllegalArgumentException(th);
		}

		return res;
	}

}
