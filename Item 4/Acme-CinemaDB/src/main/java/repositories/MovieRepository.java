
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	@Query("select count(c) from Content c join c.producer p where Type(c) = domain.Movie group by p order by count(c) asc")
	List<Long> findMinMoviesProducer();

	@Query("select count(c) from Content c join c.producer p where Type(c) = domain.Movie group by p order by count(c) desc")
	List<Long> findMaxMoviesProducer();

	@Query("select count(c)*1.0/(select count(p) from Producer p) from Content c where Type(c) = domain.Movie")
	List<Long> findAvgMoviesProducer();

	@Query("select c.coordinates.country,count(c) from Chorbi c group by c.coordinates.country")
	List<String[]> findNumberOfMoviesPerType();
}
