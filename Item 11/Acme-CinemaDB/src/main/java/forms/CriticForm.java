
package forms;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;

import security.Authority;
import security.UserAccount;
import domain.Critic;

public class CriticForm {

	private String		media;
	private String		name;
	private String		surname;
	private String		email;
	private String		phone;
	private String		country;
	private UserAccount	userAccount;

	// Form ----------------------------------------

	private String		confirmPassword;


	public CriticForm() {
		super();
		this.confirmPassword = "";
	}

	public CriticForm(final Critic critic) {
		this.name = critic.getName();
		this.surname = critic.getSurname();
		this.email = critic.getEmail();
		this.phone = critic.getPhone();
		this.country = critic.getCountry();
		this.media = critic.getMedia();
		this.userAccount = critic.getUserAccount();
		this.confirmPassword = "";
	}

	public Critic getCritic() {
		Critic result;
		final Authority authority = new Authority();
		final List<Authority> authorities = new ArrayList<>();
		authority.setAuthority(Authority.CRITIC);
		authorities.add(authority);

		result = new Critic();

		result.setName(this.name);
		result.setSurname(this.surname);
		result.setEmail(this.email);
		result.setPhone(this.phone);
		result.setCountry(this.country);
		result.setMedia(this.media);
		this.userAccount.setAuthorities(authorities);
		result.setUserAccount(this.userAccount);

		return result;
	}

	public String getMedia() {
		return this.media;
	}

	public void setMedia(final String media) {
		this.media = media;
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

	@Pattern(regexp = "^\\+[1-9]{1}\\d{10,14}$")
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

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
