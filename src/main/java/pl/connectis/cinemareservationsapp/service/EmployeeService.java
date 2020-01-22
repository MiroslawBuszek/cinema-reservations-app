package pl.connectis.cinemareservationsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.connectis.cinemareservationsapp.model.Employee;
import pl.connectis.cinemareservationsapp.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id);
    }

    public Employee findByLogin(String login) {
        return employeeRepository.findByLogin(login);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Iterable<Employee> saveAll(Iterable<Employee> employeeList) {
        return employeeRepository.saveAll(employeeList);
    }

    @Transactional
    public Employee updateById(long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id);
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        return existingEmployee;
    }

    public void deleteById(long id) {
        employeeRepository.deleteById(id);
    }

}
