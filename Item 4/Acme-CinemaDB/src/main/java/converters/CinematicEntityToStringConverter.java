
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CinematicEntity;

@Component
@Transactional
public class CinematicEntityToStringConverter implements Converter<CinematicEntity, String> {

	@Override
	public String convert(final CinematicEntity cinematicEntity) {
		String res;

		if (cinematicEntity == null)
			res = null;
		else
			res = String.valueOf(cinematicEntity.getId());

		return res;
	}

}
