
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Critic;

@Component
@Transactional
public class CriticToStringConverter implements Converter<Critic, String> {

	@Override
	public String convert(final Critic critic) {
		String res;

		if (critic == null)
			res = null;
		else
			res = String.valueOf(critic.getId());

		return res;
	}

}
