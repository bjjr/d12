
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialIdentity extends DomainEntity {

	// Attributes

	private String	username;
	private String	path;


	// Getters and Setters

	@NotNull
	@NotBlank
	@SafeHtml
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@URL
	@NotNull
	public String getPath() {
		return this.path;
	}

	public void setPath(final String path) {
		this.path = path;
	}


	// Relationships

	private User	user;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}
