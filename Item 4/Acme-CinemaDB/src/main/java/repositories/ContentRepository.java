
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {

	@Query("select c from Content c where c.title like %?1%")
	List<Content> searchContent(String s);

	@Query("select c from Content c where c.producer.id = ?1")
	List<Content> findAllProducer(int producerId);
	
	@Query("select c from Content c where c.producer.id = ?1")
	Collection<Content> findContentByProducerId(int producerId);

}
