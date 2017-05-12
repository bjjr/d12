
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {

}
