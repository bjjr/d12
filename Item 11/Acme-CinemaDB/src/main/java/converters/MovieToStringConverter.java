
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Movie;

@Component
@Transactional
public class MovieToStringConverter implements Converter<Movie, String> {

	@Override
	public String convert(final Movie movie) {
		String res;

		if (movie == null)
			res = null;
		else
			res = String.valueOf(movie.getId());
		return res;
	}
}
