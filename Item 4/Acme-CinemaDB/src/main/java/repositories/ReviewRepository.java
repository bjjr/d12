
package repositories;

import java.util.Collection;

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
}
