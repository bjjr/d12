
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class LikeUser extends DomainEntity {

	// Attributes 

	private String	comment;


	//Getters and Setters

	@SafeHtml
	@Length(min = 0, max = 1000)
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}


	// Relationships

	private AssessableEntity	assessableEntity;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public AssessableEntity getAssessableEntity() {
		return this.assessableEntity;
	}

	public void setAssessableEntity(final AssessableEntity assessableEntity) {
		this.assessableEntity = assessableEntity;
	}

}
