
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Critic extends Actor {

	// Attributes

	private String	media;


	@SafeHtml
	@NotBlank
	@NotNull
	public String getMedia() {
		return this.media;
	}

	public void setMedia(final String media) {
		this.media = media;
	}

}
