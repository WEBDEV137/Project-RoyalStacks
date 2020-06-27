package royalstacks.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import royalstacks.app.model.Employee;
import royalstacks.app.model.repository.EmployeeRepository;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Employee> findById(int userid){
        return employeeRepository.findById(userid);
    }
}
