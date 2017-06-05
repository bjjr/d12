
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AssessableEntityRepository;
import domain.AssessableEntity;

@Component
@Transactional
public class StringToAssessableEntityConverter implements Converter<String, AssessableEntity> {

	@Autowired
	AssessableEntityRepository	assessableEntityRepository;


	@Override
	public AssessableEntity convert(final String text) {
		AssessableEntity res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.assessableEntityRepository.findOne(id);
			}
		} catch (final Throwable th) {
			throw new IllegalArgumentException(th);
		}

		return res;
	}
}
