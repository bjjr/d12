
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Review;

@Component
@Transactional
public class ReviewToStringConverter implements Converter<Review, String> {

	@Override
	public String convert(final Review review) {
		String res;

		if (review == null)
			res = null;
		else
			res = String.valueOf(review.getId());
		return res;
	}
}
