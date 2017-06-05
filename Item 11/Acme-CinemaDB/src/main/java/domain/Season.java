
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
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

	private TvShow	tvShow;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public TvShow getTvShow() {
		return this.tvShow;
	}

	public void setTvShow(final TvShow tvShow) {
		this.tvShow = tvShow;
	}

}
