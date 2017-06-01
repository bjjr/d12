
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAccountId(int userAccountId);

	@Query("select u.id from User u")
	List<Integer> findAllUserId();

	@Query("select u.id from OrderUser o join o.user u")
	List<Integer> findAllUsersWithOrdersId();

}
