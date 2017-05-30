
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Fee extends DomainEntity {

	// Attributes -----------------------------------

	private double	value;


	@NotNull
	public double getValue() {
		return this.value;
	}

	public void setValue(final double value) {
		this.value = value;
	}

}
