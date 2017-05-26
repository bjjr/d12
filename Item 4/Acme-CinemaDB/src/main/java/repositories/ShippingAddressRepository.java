
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ShippingAddress;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer> {

	@Query("select s from ShippingAddress s where s.user.id = ?1")
	Collection<ShippingAddress> findShippingAddressesByUser(int userId);
}
