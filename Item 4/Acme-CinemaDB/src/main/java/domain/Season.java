
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Season extends DomainEntity {

	// Attributes
	private String	name;


	@NotBlank
	@NotNull
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}


	//Relationships

	private Collection<Chapter>	chapters;


	@NotNull
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Chapter> getChapters() {
		return this.chapters;
	}

	public void setChapters(final Collection<Chapter> chapters) {
		this.chapters = chapters;
	}

}
