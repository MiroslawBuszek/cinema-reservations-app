package pl.connectis.cinemareservationsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.connectis.cinemareservationsapp.model.Employee;
import pl.connectis.cinemareservationsapp.service.EmployeeService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/all")
    public Iterable<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/employee/{id}")
    public List<Employee> getEmployeeById(@PathVariable long id) {
        return employeeService.findById(id);
    }

    @PostMapping("/employee")
    public Employee addEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PostMapping("/employee/many")
    public Iterable<Employee> addEmployeeList(@Valid @RequestBody Iterable<Employee> employeeList) {
        return employeeService.saveAll(employeeList);
    }

    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable long id) {
        employeeService.deleteById(id);
    }

}
