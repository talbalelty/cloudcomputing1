package main;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<UserEntity, String> {
  public List<UserEntity> findAllByEmailLike(@Param("email") String email, Pageable pageable);

  public List<UserEntity> findAllByBirthdateLike(@Param("birthdate") String birthdate, Pageable pageable);

  public List<UserEntity> findAllByRolesLike(@Param("roles") String roles, Pageable pageable);
}