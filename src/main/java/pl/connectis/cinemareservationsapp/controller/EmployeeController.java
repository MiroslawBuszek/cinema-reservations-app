package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.exceptions.BadRequestException;
import pl.connectis.cinemareservationsapp.exceptions.ResourceNotFoundException;
import pl.connectis.cinemareservationsapp.model.Employee;
import pl.connectis.cinemareservationsapp.service.EmployeeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public Iterable<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping
    public Employee getEmployeeById(@RequestParam long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new ResourceNotFoundException("employee {id=" + id + "} was not found");
        }
        return employee;
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
        if (employeeService.findByLogin(employee.getLogin()) != null) {
            throw new BadRequestException("employee {login=" + employee.getLogin() + "} was found");
        }
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
    }

    @PostMapping("/many")
    public ResponseEntity<Iterable> addEmployeeList(@Valid @RequestBody Iterable<Employee> employeeList) {
        for (Employee employee : employeeList) {
            if (employeeService.findByLogin(employee.getLogin()) != null) {
                throw new BadRequestException("employy {login=" + employee.getLogin() + "} was found");
            }
        }
        return new ResponseEntity<>(employeeService.saveAll(employeeList), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Employee> updateEmployee(@RequestParam long id, @Valid @RequestBody Employee employee) {
        Employee existingEmployee = employeeService.findById(id);
        if (existingEmployee == null) {
            throw new ResourceNotFoundException("employee {id=" + id + "} was not found");
        } else if (!employee.getLogin().equals(existingEmployee.getLogin())) {
            throw new BadRequestException("{login=" + employee.getLogin() +
                    "} does not correspond to employee of {id=" + id + "}");
        } else {
            return new ResponseEntity<>(employeeService.updateById(id, employee), HttpStatus.CREATED);
        }
    }

    @DeleteMapping
    public ResponseEntity deleteEmployee(@RequestParam long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new ResourceNotFoundException("employee {id=" + id + "} was not found");
        }
        employeeService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
