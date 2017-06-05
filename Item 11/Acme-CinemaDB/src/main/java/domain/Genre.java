
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "kind")
})
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
