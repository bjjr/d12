
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LikeUser;

@Repository
public interface LikeUserRepository extends JpaRepository<LikeUser, Integer> {

	@Query("select l from LikeUser l join l.assessableEntity a where l.user.id = ?1 and (type(a) = domain.Movie or type(a) = domain.TvShow)")
	Collection<LikeUser> findContentLikes(int userId);

	@Query("select l from LikeUser l join l.assessableEntity a where l.user.id = ?1 and type(a) = domain.CinematicEntity")
	Collection<LikeUser> findCinematicEntityLikes(int userId);
}
