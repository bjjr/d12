
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.AssessableEntity;

@Component
@Transactional
public class AssessableEntityToStringConverter implements Converter<AssessableEntity, String> {

	@Override
	public String convert(final AssessableEntity assessableEntity) {
		String res;

		if (assessableEntity == null)
			res = null;
		else
			res = String.valueOf(assessableEntity.getId());

		return res;
	}

}
