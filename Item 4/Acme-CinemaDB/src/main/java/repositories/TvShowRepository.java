
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

}
