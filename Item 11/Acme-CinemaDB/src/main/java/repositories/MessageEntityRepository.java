
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageEntity;

@Repository
public interface MessageEntityRepository extends JpaRepository<MessageEntity, Integer> {

	@Query("select m from MessageEntity m where m.copy = false and m.sender.id = ?1")
	Collection<MessageEntity> findSentMessages(int principalId);

	@Query("select m from MessageEntity m where m.copy = true and m.recipient.id = ?1")
	Collection<MessageEntity> findReceivedMessages(int principalId);

}
