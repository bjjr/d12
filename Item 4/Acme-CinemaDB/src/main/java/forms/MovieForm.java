
package forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import domain.Movie;
import domain.TvShow;

public class MovieForm {

	// Attributes 
	private int					id;
	private String				title;
	private int					year;
	private Collection<String>	images;
	private Collection<String>	videos;

	// Form ----------------------------------------

	private List<Integer>		genres;


	public MovieForm() {
		super();
		this.genres = new ArrayList<Integer>();
	}
	public MovieForm(final Movie movie) {
		this.id = movie.getId();
		this.title = movie.getTitle();
		this.year = movie.getYear();
		this.images = movie.getImages();
		this.videos = movie.getVideos();
		this.genres = new ArrayList<Integer>();
	}

	public MovieForm(final TvShow tvShow) {
		this.id = tvShow.getId();
		this.title = tvShow.getTitle();
		this.year = tvShow.getYear();
		this.images = tvShow.getImages();
		this.videos = tvShow.getVideos();
		this.genres = new ArrayList<Integer>();
	}

	public Movie getMovie() {
		Movie result;

		result = new Movie();

		result.setId(this.id);
		result.setTitle(this.title);
		result.setYear(this.year);
		result.setImages(this.images);
		result.setVideos(this.videos);

		return result;
	}

	public TvShow getTvShow() {
		TvShow result;

		result = new TvShow();

		result.setId(this.id);
		result.setTitle(this.title);
		result.setYear(this.year);
		result.setImages(this.images);
		result.setVideos(this.videos);

		return result;
	}
	//Getters and Setters

	public int getId() {
		return this.id;
	}
	public void setId(final int id) {
		this.id = id;
	}

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

	@NotNull
	@NotEmpty
	public List<Integer> getGenres() {
		return this.genres;
	}

	public void setGenres(final List<Integer> genres) {
		this.genres = genres;
	}

}
