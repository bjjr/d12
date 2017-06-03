
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "finished"), @Index(columnList = "status")
})
public class OrderUser extends DomainEntity {

	// Attributes

	private double	total;
	private Date	moment;
	private Boolean	finished;
	private int		status;


	public double getTotal() {
		return this.total;
	}

	public void setTotal(final double total) {
		this.total = total;
	}

	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotNull
	public Boolean getFinished() {
		return this.finished;
	}

	public void setFinished(final Boolean finished) {
		this.finished = finished;
	}

	@Range(min = 0, max = 2)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}


	//Relationships

	private User						user;
	private ShippingAddress				shippingAddress;
	private Collection<OrderQuantity>	orderQuantities;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Valid
	@ManyToOne(optional = true)
	public ShippingAddress getShippingAddress() {
		return this.shippingAddress;
	}

	public void setShippingAddress(final ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@ManyToMany
	@NotEmpty
	public Collection<OrderQuantity> getOrderQuantities() {
		return this.orderQuantities;
	}

	public void setOrderQuantities(final Collection<OrderQuantity> orderQuantities) {
		this.orderQuantities = orderQuantities;
	}

}
