
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class AssessableEntity extends DomainEntity {

	// Relationships

	private Producer	producer;


	@Valid
	@ManyToOne(optional = true)
	public Producer getProducer() {
		return this.producer;
	}

	public void setProducer(final Producer producer) {
		this.producer = producer;
	}

}
