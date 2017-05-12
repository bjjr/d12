
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.CinematicEntity;

@Repository
public interface CinematicEntityRepository extends JpaRepository<CinematicEntity, Integer> {

}
