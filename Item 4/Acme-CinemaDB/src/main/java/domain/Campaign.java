
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Campaign extends DomainEntity {

	//Attributes
	private Date				start;
	private Date				end;
	private Collection<String>	images;
	private int					max;
	private int					timesDisplayed;
	private Boolean				approved;
	private double				fee;


	//Getters and Setters

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getStart() {
		return this.start;
	}

	public void setStart(final Date start) {
		this.start = start;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getEnd() {
		return this.end;
	}

	public void setEnd(final Date end) {
		this.end = end;
	}

	@NotNull
	@ElementCollection
	@NotEmpty
	public Collection<String> getImages() {
		return this.images;
	}

	public void setImages(final Collection<String> images) {
		this.images = images;
	}

	@Min(value = 10)
	public int getMax() {
		return this.max;
	}

	public void setMax(final int max) {
		this.max = max;
	}

	public int getTimesDisplayed() {
		return this.timesDisplayed;
	}

	public void setTimesDisplayed(final int timesDisplayed) {
		this.timesDisplayed = timesDisplayed;
	}

	public Boolean getApproved() {
		return this.approved;
	}

	public void setApproved(final Boolean approved) {
		this.approved = approved;
	}

	public double getFee() {
		return this.fee;
	}

	public void setFee(final double fee) {
		this.fee = fee;
	}


	//Relationships

	private Producer	producer;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Producer getProducer() {
		return this.producer;
	}

	public void setProducer(final Producer producer) {
		this.producer = producer;
	}

}
