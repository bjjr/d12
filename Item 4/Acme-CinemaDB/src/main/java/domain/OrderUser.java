
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class OrderUser extends DomainEntity {

	// Attributes

	private double	total;
	private Date	moment;


	public double getTotal() {
		return this.total;
	}

	public void setTotal(final double total) {
		this.total = total;
	}

	public Date getMoment() {
		return this.moment;
	}

	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	//Relationships

	private User						user;
	private ShippingAddress				shippingAddress;
	private Collection<OrderQuantity>	orderQuantities;


	@ManyToMany
	@NotEmpty
	public Collection<OrderQuantity> getOrderQuantities() {
		return this.orderQuantities;
	}

	public void setOrderQuantities(final Collection<OrderQuantity> orderQuantities) {
		this.orderQuantities = orderQuantities;
	}


	private Status	status;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public ShippingAddress getShippingAddress() {
		return this.shippingAddress;
	}

	public void setShippingAddress(final ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

}
