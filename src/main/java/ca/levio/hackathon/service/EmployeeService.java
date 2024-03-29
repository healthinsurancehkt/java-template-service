package ca.levio.hackathon.service;

import ca.levio.hackathon.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    Mono<Employee> createEmployee(Employee employee);
    Mono<Employee> getEmployeeById(Long id);

    Mono<Employee> getEmployeeByName(String name);
    Flux<Employee> getAllEmployees();
}
