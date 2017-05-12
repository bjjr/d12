
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Status extends DomainEntity {

	// Attributes
	private Integer	kind;


	@NotNull
	public Integer getKind() {
		return this.kind;
	}

	public void setKind(final Integer kind) {
		this.kind = kind;
	}

}
