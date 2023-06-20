package ca.levio.hackathon.service;

import ca.levio.hackathon.model.Employee;
import ca.levio.hackathon.repository.WebFluxExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class WebFluxExampleMySqlServiceImpl implements WebFluxExampleService{

    private final WebFluxExampleRepository webFluxExampleRepository;
    @Override
    public Mono<Employee> createEmployee(Employee employee) {
        return webFluxExampleRepository.save(employee);
    }

    @Override
    public Mono<Employee> getEmployeeById(Long idEmployee) {
        return webFluxExampleRepository.findById(idEmployee);
    }

    @Override
    public Flux<Employee> getAllEmployees() {
        return webFluxExampleRepository.findAll();
    }
}
