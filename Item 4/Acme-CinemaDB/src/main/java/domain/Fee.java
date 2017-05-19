
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;

@Entity
@Access(AccessType.PROPERTY)
public class Fee extends DomainEntity {

	// Attributes -----------------------------------

	private double	value;


	@DecimalMin(value = "0.0")
	@Digits(integer = 6, fraction = 2)
	public double getValue() {
		return this.value;
	}

	public void setValue(final double value) {
		this.value = value;
	}

}
