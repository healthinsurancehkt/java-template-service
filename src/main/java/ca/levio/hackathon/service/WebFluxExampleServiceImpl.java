package ca.levio.hackathon.service;

import ca.levio.hackathon.entity.Employee;
import ca.levio.hackathon.repository.WebFluxExampleMockrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class WebFluxExampleServiceImpl implements WebFluxExampleService{

    private final WebFluxExampleMockrepository webFluxExampleMockrepository;

    @Autowired
    public WebFluxExampleServiceImpl(WebFluxExampleMockrepository webFluxExampleMockrepository){
        this.webFluxExampleMockrepository = webFluxExampleMockrepository;
    }

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
