
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Product extends DomainEntity {

	// Attributes

	private double	price;
	private int		stock;
	private String	picture;
	private String	idcode;


	public double getPrice() {
		return this.price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(final int stock) {
		this.stock = stock;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotBlank
	@NotNull
	public String getIdcode() {
		return this.idcode;
	}

	public void setIdcode(final String idcode) {
		this.idcode = idcode;
	}


	//Relationships

	private Content	content;


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
