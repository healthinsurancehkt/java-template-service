package ca.levio.hackathon.service;

import ca.levio.hackathon.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WebFluxExampleService {

    Mono<Employee> createEmployee(Employee employee);
    Mono<Employee> getEmployeeById(Long idEmployee);

    Flux<Employee> getAllEmployees();
}
