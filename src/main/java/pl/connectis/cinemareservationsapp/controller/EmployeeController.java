package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.exceptions.ResourceExistsException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Employee;
import pl.connectis.cinemareservationsapp.service.EmployeeService;

import javax.validation.Valid;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/all")
    public Iterable<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new ResourceNotFoundException("employee {id=" + id + "} was not found");
        }
        return employee;
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
        if (employeeService.findByLogin(employee.getLogin()) != null) {
            throw new ResourceExistsException("employee {login=" + employee.getLogin() + "} was found");
        }
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
    }

    @PostMapping("/employee/many")
    public ResponseEntity<Iterable> addEmployeeList(@Valid @RequestBody Iterable<Employee> employeeList) {
        for (Employee employee : employeeList) {
            if (employeeService.findByLogin(employee.getLogin()) != null) {
                throw new ResourceExistsException("employy {login=" + employee.getLogin() + "} was found");
            }
        }
        return new ResponseEntity<>(employeeService.saveAll(employeeList), HttpStatus.CREATED);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @Valid @RequestBody Employee employee) {
        Employee existingEmployee = employeeService.findById(id);
        if (existingEmployee == null) {
            throw new ResourceNotFoundException("employee {id=" + id + "} was not found");
        } else if (employeeService.findByLogin(employee.getLogin()) == null) {
            throw new ResourceNotFoundException("employee {login=" + employee.getLogin() + "} was not found");
        } else {
            return new ResponseEntity<>(employeeService.updateById(id, employee), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity deleteEmployee(@PathVariable long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new ResourceNotFoundException("employee {id=" + id + "} was not found");
        }
        employeeService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
