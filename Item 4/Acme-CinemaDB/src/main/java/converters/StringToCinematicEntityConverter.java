
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CinematicEntityRepository;
import domain.CinematicEntity;

@Component
@Transactional
public class StringToCinematicEntityConverter implements Converter<String, CinematicEntity> {

	@Autowired
	CinematicEntityRepository	cinematicEntityRepository;


	@Override
	public CinematicEntity convert(final String text) {
		CinematicEntity res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.cinematicEntityRepository.findOne(id);
			}
		} catch (final Throwable th) {
			throw new IllegalArgumentException(th);
		}

		return res;
	}
}
