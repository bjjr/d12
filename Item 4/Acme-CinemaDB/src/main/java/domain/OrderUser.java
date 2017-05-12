
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class OrderUser extends DomainEntity {

	// Attributes

	private double	total;


	public double getTotal() {
		return this.total;
	}

	public void setTotal(final double total) {
		this.total = total;
	}


	//Relationships

	private User				user;
	private ShippingAddress		shippingAddress;
	private Collection<Product>	products;
	private Status				status;


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
	@NotEmpty
	@ManyToMany
	public Collection<Product> getProducts() {
		return this.products;
	}

	public void setProducts(final Collection<Product> products) {
		this.products = products;
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
