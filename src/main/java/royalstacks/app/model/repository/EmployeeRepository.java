package royalstacks.app.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import royalstacks.app.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository <Employee, Integer> {


}
