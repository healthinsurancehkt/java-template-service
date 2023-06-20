package ca.levio.hackathon.service;

import ca.levio.hackathon.entity.Employee;
import ca.levio.hackathon.repository.WebFluxExampleMockrepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class WebFluxExampleServiceImpl implements WebFluxExampleService{

    private final WebFluxExampleMockrepository webFluxExampleMockrepository;

    @Override
    public Mono<Employee> createEmployee(Employee employee) {
        return webFluxExampleMockrepository.createEmployee(employee);
    }

    @Override
    public Mono<Employee> getEmployeeById(Long idEmployee) {
        return webFluxExampleMockrepository.getEmployeeById(idEmployee);
    }

    @Override
    public Flux<Employee> getAllEmployees() {
        return webFluxExampleMockrepository.getAllEmployees();
    }
}
