
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.OrderUser;

@Repository
public interface OrderUserRepository extends JpaRepository<OrderUser, Integer> {

}
