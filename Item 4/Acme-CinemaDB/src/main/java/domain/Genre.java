
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Genre extends DomainEntity {

	// Attributes
	private int	kind;


	public int getKind() {
		return this.kind;
	}

	public void setKind(final int kind) {
		this.kind = kind;
	}

}
