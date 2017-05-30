
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	@Query("select m from Movie m where m.producer.id = ?1")
	List<Movie> findAllProducer(int producerId);

}
