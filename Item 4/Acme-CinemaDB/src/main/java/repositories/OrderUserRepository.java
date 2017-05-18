
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.OrderUser;

@Repository
public interface OrderUserRepository extends JpaRepository<OrderUser, Integer> {

	@Query("select o from OrderUser o where o.user.id = ?1")
	Collection<OrderUser> findOrdersByUser(int userId);
}
