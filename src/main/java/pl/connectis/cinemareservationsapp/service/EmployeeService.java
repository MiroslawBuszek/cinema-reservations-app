package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Iterable<Employee> saveAll(Iterable<Employee> employeeList) {
        return employeeRepository.saveAll(employeeList);
    }

    public void deleteById(long id) {
        employeeRepository.deleteById(id);
    }

}
