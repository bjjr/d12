
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Integer> {

	@Query("select p from Producer p where p.userAccount.id = ?1")
	Producer findByUserAccountId(int userAccountId);

	@Query("select p.id from Producer p")
	List<Integer> findAllProducerId();

	@Query("select p.id from Movie m join m.producer p")
	List<Integer> findAllProducerWithMoviesId();

}
