
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Season;

@Component
@Transactional
public class SeasonToStringConverter implements Converter<Season, String> {

	@Override
	public String convert(final Season season) {
		String res;

		if (season == null)
			res = null;
		else
			res = String.valueOf(season.getId());
		return res;
	}
}
