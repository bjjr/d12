
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.TvShowRepository;
import domain.TvShow;

@Component
@Transactional
public class StringToTvShowConverter implements Converter<String, TvShow> {

	@Autowired
	TvShowRepository	showRepository;


	@Override
	public TvShow convert(final String text) {
		TvShow res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.showRepository.findOne(id);
			}
		} catch (final Throwable th) {
			throw new IllegalArgumentException(th);
		}

		return res;
	}
}
