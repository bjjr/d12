
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.TvShow;

@Component
@Transactional
public class TvShowToStringConverter implements Converter<TvShow, String> {

	@Override
	public String convert(final TvShow show) {
		String res;

		if (show == null)
			res = null;
		else
			res = String.valueOf(show.getId());
		return res;
	}
}
