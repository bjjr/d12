
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Status;

@Component
@Transactional
public class StatusToStringConverter implements Converter<Status, String> {

	@Override
	public String convert(Status status) {
		String res;

		if (status == null) {
			res = null;
		} else {
			res = String.valueOf(status.getId());
		}
		return res;
	}
}
