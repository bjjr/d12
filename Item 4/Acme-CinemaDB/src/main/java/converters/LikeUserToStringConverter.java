
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.LikeUser;

@Component
@Transactional
public class LikeUserToStringConverter implements Converter<LikeUser, String> {

	@Override
	public String convert(final LikeUser likeUser) {
		String res;

		if (likeUser == null)
			res = null;
		else
			res = String.valueOf(likeUser.getId());
		return res;
	}
}
