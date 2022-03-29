package finance.data.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import finance.data.UserEntity;

public interface UserDao extends PagingAndSortingRepository<UserEntity, String> {

	public List<UserEntity> findAllByEmail(@Param("email") String email);

	public List<UserEntity> findAllById(@Param("id") String id);
}
