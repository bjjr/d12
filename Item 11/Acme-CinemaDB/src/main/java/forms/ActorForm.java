
package forms;

import java.util.ArrayList;
import java.util.List;

import security.Authority;
import security.UserAccount;
import domain.Critic;
import domain.Producer;
import domain.User;

public class ActorForm {

	// Main attributes ------------------------------
	private String		name;
	private String		surname;
	private String		email;
	private String		phone;
	private String		country;
	private UserAccount	userAccount;

	// Producer attributes --------------------------

	private String		company;

	// Critic attributes ----------------------------

	private String		media;

	// Form attributes ------------------------------

	private String		confirmPassword;


	// Constructor ----------------------------------

	public ActorForm() {
		super();
		this.confirmPassword = "";
	}

	public ActorForm(final User user) {
		this.name = user.getName();
		this.surname = user.getSurname();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.country = user.getCountry();
		this.userAccount = user.getUserAccount();
		this.confirmPassword = "";
	}

	public ActorForm(final Critic critic) {
		this.name = critic.getName();
		this.surname = critic.getSurname();
		this.email = critic.getEmail();
		this.phone = critic.getPhone();
		this.country = critic.getCountry();
		this.userAccount = critic.getUserAccount();
		this.media = critic.getMedia();
		this.confirmPassword = "";
	}

	public ActorForm(final Producer producer) {
		this.name = producer.getName();
		this.surname = producer.getSurname();
		this.email = producer.getEmail();
		this.phone = producer.getPhone();
		this.country = producer.getCountry();
		this.userAccount = producer.getUserAccount();
		this.company = producer.getCompany();
		this.confirmPassword = "";
	}

	/**
	 * Returns an User from the data and assigns the USER Authority.
	 * 
	 * @return An User to be validated.
	 */

	public User getUser() {
		User result;
		Authority authority;
		List<Authority> authorities;

		authority = new Authority();
		authorities = new ArrayList<>();
		authority.setAuthority(Authority.USER);
		authorities.add(authority);

		result = new User();

		result.setName(this.name);
		result.setSurname(this.surname);
		result.setEmail(this.email);
		result.setPhone(this.phone);
		result.setCountry(this.country);
		this.userAccount.setAuthorities(authorities);
		result.setUserAccount(this.userAccount);

		return result;
	}

	/**
	 * Returns a Critic from the data and assigns the CRITIC Authority.
	 * 
	 * @return A Critic to be validated.
	 */

	public Critic getCritic() {
		Critic result;
		Authority authority;
		List<Authority> authorities;

		authority = new Authority();
		authorities = new ArrayList<>();
		authority.setAuthority(Authority.CRITIC);
		authorities.add(authority);

		result = new Critic();

		result.setName(this.name);
		result.setSurname(this.surname);
		result.setEmail(this.email);
		result.setPhone(this.phone);
		result.setCountry(this.country);
		this.userAccount.setAuthorities(authorities);
		result.setUserAccount(this.userAccount);

		result.setMedia(this.media);

		return result;
	}

	/**
	 * Returns a Producer from the data and assigns the PRODUCER Authority.
	 * 
	 * @return A Producer to be validated.
	 */

	public Producer getProducer() {
		Producer result;
		Authority authority;
		List<Authority> authorities;

		authority = new Authority();
		authorities = new ArrayList<>();
		authority.setAuthority(Authority.PRODUCER);
		authorities.add(authority);

		result = new Producer();

		result.setName(this.name);
		result.setSurname(this.surname);
		result.setEmail(this.email);
		result.setPhone(this.phone);
		result.setCountry(this.country);
		this.userAccount.setAuthorities(authorities);
		result.setUserAccount(this.userAccount);

		result.setCompany(this.company);

		return result;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(final String company) {
		this.company = company;
	}

	public String getMedia() {
		return this.media;
	}

	public void setMedia(final String media) {
		this.media = media;
	}

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
