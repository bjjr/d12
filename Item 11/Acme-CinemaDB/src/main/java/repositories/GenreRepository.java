
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

	@Query("select g from Genre g where g.kind = ?1")
	Genre genreByKind(Integer kind);

}
