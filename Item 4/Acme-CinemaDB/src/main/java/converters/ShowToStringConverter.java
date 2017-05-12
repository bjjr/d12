
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Show;

@Component
@Transactional
public class ShowToStringConverter implements Converter<Show, String> {

	@Override
	public String convert(final Show show) {
		String res;

		if (show == null)
			res = null;
		else
			res = String.valueOf(show.getId());
		return res;
	}
}
