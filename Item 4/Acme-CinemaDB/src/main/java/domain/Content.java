
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Content extends AssessableEntity {

	// Attributes 

	private String				title;
	private int					year;
	private double				avgRating;
	private Collection<String>	images;
	private Collection<String>	videos;


	//Getters and Setters

	@NotBlank
	@NotNull
	@SafeHtml
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Min(value = 0)
	public int getYear() {
		return this.year;
	}

	public void setYear(final int year) {
		this.year = year;
	}

	public double getAvgRating() {
		return this.avgRating;
	}

	public void setAvgRating(final double avgRating) {
		this.avgRating = avgRating;
	}

	@URL
	@ElementCollection
	public Collection<String> getImages() {
		return this.images;
	}

	public void setImages(final Collection<String> images) {
		this.images = images;
	}

	@URL
	@ElementCollection
	public Collection<String> getVideos() {
		return this.videos;
	}

	public void setVideos(final Collection<String> videos) {
		this.videos = videos;
	}


	// Relationships

	private Collection<Genre>			genre;
	private Collection<CinematicEntity>	cinematicEntity;


	@ManyToMany
	public Collection<Genre> getGenre() {
		return this.genre;
	}

	public void setGenre(final Collection<Genre> genre) {
		this.genre = genre;
	}

	@ManyToMany
	public Collection<CinematicEntity> getCinematicEntity() {
		return this.cinematicEntity;
	}

	public void setCinematicEntity(final Collection<CinematicEntity> cinematicEntity) {
		this.cinematicEntity = cinematicEntity;
	}

}
