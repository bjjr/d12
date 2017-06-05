
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SeasonRepository;
import domain.Season;

@Component
@Transactional
public class StringToSeasonConverter implements Converter<String, Season> {

	@Autowired
	SeasonRepository	seasonRepository;


	@Override
	public Season convert(final String text) {
		Season res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.seasonRepository.findOne(id);
			}
		} catch (final Throwable th) {
			throw new IllegalArgumentException(th);
		}

		return res;
	}
}
