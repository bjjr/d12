
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LikeUser;

@Repository
public interface LikeUserRepository extends JpaRepository<LikeUser, Integer> {

	@Query("select lu from LikeUser lu where lu.user.id = ?1")
	Collection<LikeUser> findAllByPrincipal(int id);

	@Query("select lu from LikeUser lu where lu.user.id = ?1 and lu.assessableEntity.id  = ?2")
	LikeUser findByUserAndAssessableEntity(int userId, int assessableEntityId);

	@Query("select l from LikeUser l where l.assessableEntity.id = ?1 AND l.comment is not null")
	Collection<LikeUser> findCommentsByAssessableEntity(int assessableEntityId);

	@Query("select l from LikeUser l join l.assessableEntity a where l.user.id = ?1 and (type(a) = domain.Movie or type(a) = domain.TvShow)")
	Collection<LikeUser> findContentLikes(int userId);

	@Query("select l from LikeUser l join l.assessableEntity a where l.user.id = ?1 and type(a) = domain.CinematicEntity")
	Collection<LikeUser> findCinematicEntityLikes(int userId);

}
