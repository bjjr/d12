
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Query("select r from Review r where r.content.id = ?1 and r.draft = false")
	Collection<Review> findReviewsByContentId(final int contentId);

	@Query("select r from Review r where r.critic.id = ?1")
	Collection<Review> findReviewsByCriticId(final int criticId);

	@Query("select count(r) from Review r join r.critic c group by c order by count(r) asc")
	List<Long> findMinReviewCritic();

	@Query("select count(r)*1.0/(select count(c) from Critic c) from Review r")
	Double findAvgReviewCritic();

	@Query("select count(r) from Review r join r.critic c group by c order by count(r) desc")
	List<Long> findMaxReviewCritic();

	@Query("select sum(r.rating)*1.0/(select count(r) from Review r join r.content c where Type(c) = domain.Movie) from Review r join r.content c where Type(c) = domain.Movie")
	Double findAvgRatingMovies();

	@Query("select sum(r.rating)*1.0/(select count(r) from Review r join r.content c where Type(c) = domain.TvShow) from Review r join r.content c where Type(c) = domain.TvShow")
	Double findAvgRatingTvShows();
}
