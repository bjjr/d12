
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ShowRepository;
import domain.Show;

@Component
@Transactional
public class StringToShowConverter implements Converter<String, Show> {

	@Autowired
	ShowRepository	showRepository;


	@Override
	public Show convert(final String text) {
		Show res;
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
