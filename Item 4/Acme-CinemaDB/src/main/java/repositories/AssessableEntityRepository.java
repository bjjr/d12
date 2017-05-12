
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.AssessableEntity;

@Repository
public interface AssessableEntityRepository extends JpaRepository<AssessableEntity, Integer> {

}
