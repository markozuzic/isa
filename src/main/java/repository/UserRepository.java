package repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import model.User;

public interface UserRepository extends Repository<User, Long>{
		public Page<User> findAll(Pageable pageable);

}
