
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Invoice extends DomainEntity {

	// Attributes 

	private Boolean	paid;
	private Date	bilingDate;
	private Double	total;


	@NotNull
	public Boolean getPaid() {
		return this.paid;
	}

	public void setPaid(final Boolean paid) {
		this.paid = paid;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getBilingDate() {
		return this.bilingDate;
	}

	public void setBilingDate(final Date bilingDate) {
		this.bilingDate = bilingDate;
	}

	public Double getTotal() {
		return this.total;
	}

	public void setTotal(final Double total) {
		this.total = total;
	}


	// Relationships

	private Administrator	administrator;
	private Campaign		campaign;


	@NotNull
	@Valid
	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(final Administrator administrator) {
		this.administrator = administrator;
	}

	@NotNull
	@Valid
	public Campaign getCampaign() {
		return this.campaign;
	}

	public void setCampaign(final Campaign campaign) {
		this.campaign = campaign;
	}

}
