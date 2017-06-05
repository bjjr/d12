
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chapter;
import domain.Season;
import domain.TvShow;

@Repository
public interface TvShowRepository extends JpaRepository<TvShow, Integer> {

	@Query("select s from Season s join s.tvShow tv where ?1 = tv.id")
	List<Season> getSeasons(int tvShowId);

	@Query("select c from Chapter c join c.season s where ?1 = s.id")
	List<Chapter> getChapter(int seasonId);

	@Query("select tv from TvShow tv where tv.producer.id = ?1")
	List<TvShow> findAllProducer(int producerId);

	@Query("select count(t), g.kind from TvShow t join t.genres g group by g")
	List<Integer[]> findNumberOfTvShowsPerType();

}
