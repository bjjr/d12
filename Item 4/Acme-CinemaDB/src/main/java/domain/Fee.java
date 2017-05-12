
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Fee extends DomainEntity {

	// Attributes -----------------------------------

	private double	value;


	@Min(value = 0)
	public double getValue() {
		return this.value;
	}

	public void setValue(final double value) {
		this.value = value;
	}

}
