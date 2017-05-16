
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LikeUser;

@Repository
public interface LikeUserRepository extends JpaRepository<LikeUser, Integer> {

	@Query("select lu from LikeUser lu where lu.user.id = ?1")
	Collection<LikeUser> findAllByUserAccountId(int id);

}
