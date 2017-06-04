
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

	@Query("select count(c)*1.0/(select count(s) from Season s) from Chapter c")
	Double findAvgChaptersPerSeason();

	@Query("select count(c) from Chapter c join c.season s group by s order by count(c) desc")
	List<Long> findMaxChaptersPerSeason();

}
