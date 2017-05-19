
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CinematicEntity;
import domain.Content;

@Repository
public interface CinematicEntityRepository extends JpaRepository<CinematicEntity, Integer> {

	@Query("select c from CinematicEntity c where c.name like %?1% or c.surname like %?1%")
	List<CinematicEntity> searchCinematicEntity(String s);

	@Query("select c from Content c join c.cinematicEntities ce where ce.id = ?1")
	List<Content> getContents(int cinematicEntityId);

}
