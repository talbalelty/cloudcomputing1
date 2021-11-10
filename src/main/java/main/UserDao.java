package main;

import java.util.List;

import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<UserEntity, String> {
  // @Query(value = "select * " + "from items i " + "where lower(i.type) = :type
  // and "
  // + "(lower(i.name) like :name or lower(i.item_attributes) like
  // :itemAttributes)", nativeQuery = true)
  public List<UserEntity> findAllByEmailLike(@Param("email") String email, Pageable pageable);

}