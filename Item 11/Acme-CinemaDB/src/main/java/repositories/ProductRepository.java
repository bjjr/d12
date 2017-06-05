
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("select p from OrderUser o join o.orderQuantities oq join oq.product p where o.id = ?1 and o.finished = false")
	Collection<Product> findProductsByOrder(int orderId);

	@Query("select p from Product p where p.content.id = ?1")
	Collection<Product> findAllProductsByContentId(int id);
}
