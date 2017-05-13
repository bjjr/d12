
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Review extends DomainEntity {

	// Attributes

	private String	title;
	private String	body;
	private int		rating;
	private boolean	draft;


	@NotBlank
	@NotNull
	@Size(min = 1, max = 100)
	@SafeHtml
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@NotNull
	@Size(min = 1, max = 3500)
	@SafeHtml
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@Range(min = 0, max = 5)
	public int getRating() {
		return this.rating;
	}

	public void setRating(final int rating) {
		this.rating = rating;
	}

	public boolean isDraft() {
		return this.draft;
	}

	public void setDraft(final boolean draft) {
		this.draft = draft;
	}


	//Relationships
	private Critic	critic;
	private Content	content;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Critic getCritic() {
		return this.critic;
	}

	public void setCritic(final Critic critic) {
		this.critic = critic;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Content getContent() {
		return this.content;
	}

	public void setContent(final Content content) {
		this.content = content;
	}

}
