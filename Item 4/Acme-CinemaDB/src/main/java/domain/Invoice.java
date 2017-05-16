
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
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
	private Date	billingDate;
	private double	total;


	@NotNull
	public Boolean getPaid() {
		return this.paid;
	}

	public void setPaid(final Boolean paid) {
		this.paid = paid;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getBillingDate() {
		return this.billingDate;
	}

	public void setBillingDate(final Date billingDate) {
		this.billingDate = billingDate;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(final double total) {
		this.total = total;
	}


	// Relationships

	private Campaign	campaign;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Campaign getCampaign() {
		return this.campaign;
	}

	public void setCampaign(final Campaign campaign) {
		this.campaign = campaign;
	}

}
