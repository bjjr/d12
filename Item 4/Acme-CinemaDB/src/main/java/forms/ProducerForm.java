
package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import security.UserAccount;
import domain.CreditCard;
import domain.Producer;

public class ProducerForm {

	private Integer		id;
	private Integer		version;
	private String		name;
	private String		surname;
	private String		email;
	private String		phone;
	private String		country;
	private String		company;
	private CreditCard	creditCard;
	private UserAccount	userAccount;

	// Form ----------------------------------------

	private String		confirmPassword;


	public ProducerForm() {
		super();
	}

	public ProducerForm(final Producer producer) {
		this();
		this.id = producer.getId();
		this.version = producer.getVersion();
		this.name = producer.getName();
		this.surname = producer.getSurname();
		this.email = producer.getEmail();
		this.phone = producer.getPhone();
		this.country = producer.getCountry();
		this.company = producer.getCompany();
		this.creditCard = producer.getCreditCard();
		this.userAccount = producer.getUserAccount();
		this.confirmPassword = "";
	}

	public Producer getProducer() {
		Producer result;

		result = new Producer();
		result.setId(this.id);
		result.setVersion(this.version);
		result.setName(this.name);
		result.setSurname(this.surname);
		result.setEmail(this.email);
		result.setPhone(this.phone);
		result.setCountry(this.country);
		result.setCompany(this.company);
		result.setCreditCard(this.creditCard);
		result.setUserAccount(this.userAccount);

		return result;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "^\\+[1-9]{1}\\d{10,14}$")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@SafeHtml
	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	@NotBlank
	@SafeHtml
	public String getCompany() {
		return this.company;
	}

	public void setCompany(final String company) {
		this.company = company;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
