package ldc.repository;

import ldc.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017/9/20.
 */
public interface DepartmentRepository extends JpaRepository<Department,Long>{
}
