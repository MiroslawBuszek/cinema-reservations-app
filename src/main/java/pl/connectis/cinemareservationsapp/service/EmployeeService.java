package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.connectis.cinemareservationsapp.model.Employee;
import pl.connectis.cinemareservationsapp.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

}
