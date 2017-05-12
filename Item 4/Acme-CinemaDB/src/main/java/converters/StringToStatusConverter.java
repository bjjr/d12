
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.StatusRepository;
import domain.Status;

@Component
@Transactional
public class StringToStatusConverter implements Converter<String, Status> {

	@Autowired
	StatusRepository	statusRepository;


	@Override
	public Status convert(String text) {
		Status res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = statusRepository.findOne(id);
			}
		} catch (Throwable th) {
			throw new IllegalArgumentException(th);
		}

		return res;
	}
}
