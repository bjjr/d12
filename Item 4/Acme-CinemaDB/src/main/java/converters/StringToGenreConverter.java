
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GenreRepository;
import domain.Genre;

@Component
@Transactional
public class StringToGenreConverter implements Converter<String, Genre> {

	@Autowired
	GenreRepository	genreRepository;


	@Override
	public Genre convert(final String text) {
		Genre res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.genreRepository.findOne(id);
			}
		} catch (final Throwable th) {
			throw new IllegalArgumentException(th);
		}

		return res;
	}
}
