package ldc.repository;

import ldc.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Administrator on 2017/9/19.
 */
public interface UserRepository extends JpaRepository<User,Long>{
    @Query("select t from User t where t.name =?1 and t.email=?2")
    User findByNameAndAndEmail(String name,String email);

    Page<User> findByName(@Param("name") String name, Pageable pageRequest);
}
