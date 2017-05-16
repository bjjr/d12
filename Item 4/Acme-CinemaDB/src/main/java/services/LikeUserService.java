
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.LikeUserRepository;
import domain.LikeUser;
import domain.User;

@Transactional
@Service
public class LikeUserService {

	@Autowired
	private UserService			userService;

	@Autowired
	private LikeUserRepository	likeUserRepository;


	public Collection<LikeUser> findAllByPrincipal() {
		final User currentUser = this.userService.findByPrincipal();
		final Collection<LikeUser> likeUserByPrincipal = this.likeUserRepository.findAllByUserAccountId(currentUser.getId());

		return likeUserByPrincipal;
	}

	public Collection<LikeUser> findAll() {
		return this.likeUserRepository.findAll();
	}

}
