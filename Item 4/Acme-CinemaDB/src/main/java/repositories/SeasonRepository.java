
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Season;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer> {

	@Query("select count(s)*1.0/(select count(t) from TvShow t) from Season s")
	Double findAvgSeasonsPerTvShow();

	@Query("select count(s) from Season s join s.tvShow t group by t order by count(s) desc")
	List<Long> findMaxSeasonsPerTvShow();

}
