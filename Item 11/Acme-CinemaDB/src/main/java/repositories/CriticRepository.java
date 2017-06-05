
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Critic;

@Repository
public interface CriticRepository extends JpaRepository<Critic, Integer> {

	@Query("select c from Critic c where c.userAccount.id = ?1")
	Critic findByUserAccountId(int userAccountId);

	@Query("select c.id from Critic c")
	List<Integer> findAllCriticId();

	@Query("select c.id from Review r join r.critic c")
	List<Integer> findAllCriticWithReviewsId();

}
