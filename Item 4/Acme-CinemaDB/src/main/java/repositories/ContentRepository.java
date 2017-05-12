
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {

}
