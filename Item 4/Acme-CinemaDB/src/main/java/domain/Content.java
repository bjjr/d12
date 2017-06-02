
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "title")
})
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

	@NotNull
	@ElementCollection
	public Collection<String> getImages() {
		return this.images;
	}

	public void setImages(final Collection<String> images) {
		this.images = images;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getVideos() {
		return this.videos;
	}

	public void setVideos(final Collection<String> videos) {
		this.videos = videos;
	}


	// Relationships

	private Collection<Genre>			genres;
	private Collection<CinematicEntity>	cinematicEntities;


	@NotNull
	@NotEmpty
	@ManyToMany
	public Collection<Genre> getGenres() {
		return this.genres;
	}

	public void setGenres(final Collection<Genre> genres) {
		this.genres = genres;
	}

	@NotNull
	@ManyToMany
	public Collection<CinematicEntity> getCinematicEntities() {
		return this.cinematicEntities;
	}

	public void setCinematicEntities(final Collection<CinematicEntity> cinematicEntities) {
		this.cinematicEntities = cinematicEntities;
	}

}
