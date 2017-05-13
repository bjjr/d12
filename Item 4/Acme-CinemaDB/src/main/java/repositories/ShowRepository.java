
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.TvShow;

@Repository
public interface ShowRepository extends JpaRepository<TvShow, Integer> {

}
