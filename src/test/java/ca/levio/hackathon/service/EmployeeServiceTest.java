package ca.levio.hackathon.service;

import ca.levio.hackathon.model.Employee;
import ca.levio.hackathon.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository repository;

    @BeforeEach
    void clearEmployees() {
        StepVerifier.create(repository.deleteAll()).expectComplete().verify();
    }

    @Test
    @Disabled
    public void testSaveEmployee() {
        Employee employee = new Employee(null, "Juan");
        StepVerifier.create(employeeService.createEmployee(employee))
                .expectNext(employee)
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindEmployeeById() {
        Employee employee = new Employee(null, "Juan");
        Employee employeeExpected = new Employee(1L, "Juan");
        StepVerifier.create(employeeService.createEmployee(employee))
                .expectNextCount(1)
                .verifyComplete();
        StepVerifier.create(employeeService.getEmployeeById(1L))
                .expectNext(employeeExpected)
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindAllEmployees() {
        Employee employeeJuan = new Employee(null, "Juan");
        Employee employeeIndra = new Employee(null, "Indra");
        Employee employeeRomero = new Employee(null, "Romero");

        StepVerifier.create(employeeService.createEmployee(employeeJuan)
                        .mergeWith(employeeService.createEmployee(employeeIndra))
                        .mergeWith(employeeService.createEmployee(employeeRomero)))
                .expectNextCount(3)
                .verifyComplete();

        Flux<Employee> getAllEmployeesFlux = employeeService.getAllEmployees();
        StepVerifier.create(getAllEmployeesFlux.map(Employee::getName).sort())
                .expectNext("Indra", "Juan",  "Romero")
                .expectComplete()
                .verify();
    }
}
