
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.OrderUser;

@Repository
public interface OrderUserRepository extends JpaRepository<OrderUser, Integer> {

	@Query("select o from OrderUser o where o.user.id = ?1 and o.finished = true")
	Collection<OrderUser> findOrdersByUser(int userId);

	@Query("select avg(o.total) from OrderUser o where o.finished = true and o.status = 1")
	Double findAvgTotalMoneyOrdersFinished();

	@Query("select count(o) from OrderUser o join o.user u group by u order by count(o) asc")
	List<Long> findMinOrderUserPerUser();

	@Query("select count(o)*1.0/(select count(u) from User u) from OrderUser o")
	Double findAvgOrderUserPerUser();

	@Query("select count(o) from OrderUser o join o.user u group by u order by count(o) desc")
	List<Long> findMaxOrderUserPerUser();

	@Query("select count(o)*100./(select count(o) from OrderUser o) from OrderUser o where o.status = 0")
	Double findPercentageOrdersInProgress();

	@Query("select count(o)*100./(select count(o) from OrderUser o) from OrderUser o where o.status = 1")
	Double findPercentageOrdersSent();

	@Query("select count(o)*100./(select count(o) from OrderUser o) from OrderUser o where o.status = 2")
	Double findPercentageOrdersCancelled();

	@Query("select o from OrderUser o where o.user.id = ?1 and o.finished = true and o.status = 0")
	Collection<OrderUser> findInProgressOrdersByUser(int userId);

	@Query("select o from OrderUser o where o.user.id = ?1 and o.finished = true and o.status = 1")
	Collection<OrderUser> findSentOrdersByUser(int userId);

	@Query("select o from OrderUser o where o.user.id = ?1 and o.finished = true and o.status = 2")
	Collection<OrderUser> findCancelledOrdersByUser(int userId);

	@Query("select o from OrderUser o where o.finished = true")
	Collection<OrderUser> findAllOrders();

	@Query("select o from OrderUser o where o.finished = true and o.status = 0")
	Collection<OrderUser> findInProgressOrders();

	@Query("select o from OrderUser o where o.finished = true and o.status = 1")
	Collection<OrderUser> findSentOrders();

	@Query("select o from OrderUser o where o.finished = true and o.status = 2")
	Collection<OrderUser> findCancelledOrders();

	@Query("select o from OrderUser o where o.shippingAddress.id = ?1")
	Collection<OrderUser> findOrdersByShippingAddress(int shippingAddressId);

	@Query("select o from OrderUser o where o.user.id = ?1 and o.finished = false")
	Collection<OrderUser> findUnfinishedOrder(int userId);

}
