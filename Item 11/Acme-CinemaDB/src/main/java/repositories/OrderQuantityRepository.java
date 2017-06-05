
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.OrderQuantity;

@Repository
public interface OrderQuantityRepository extends JpaRepository<OrderQuantity, Integer> {

}
