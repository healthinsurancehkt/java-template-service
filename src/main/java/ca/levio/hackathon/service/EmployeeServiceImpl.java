package ca.levio.hackathon.service;

import ca.levio.hackathon.model.Employee;
import ca.levio.hackathon.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    @Override
    public Mono<Employee> createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Mono<Employee> getEmployeeById(Long idEmployee) {
        return employeeRepository.findById(idEmployee);
    }

    @Override
    public Mono<Employee> getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public Flux<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
