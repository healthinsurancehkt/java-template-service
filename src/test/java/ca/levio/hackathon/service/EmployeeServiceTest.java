package ca.levio.hackathon.service;

import ca.levio.hackathon.model.Employee;
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
    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee(null,"Juan");
        StepVerifier.create(employeeService.createEmployee(employee))
                .expectNext(employee)
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindEmployeeById() {
        Employee employee = new Employee(null,"Juan");
        Employee employeeExpected = new Employee(1L,"Juan");
        employeeService.createEmployee(employee);
        StepVerifier.create(employeeService.getEmployeeById(1L))
                .expectNext(employeeExpected)
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindAllEmployees() {
        Employee employeeJuan = new Employee(null,"Juan");
        Employee employeeIndra = new Employee(null,"Indra");
        Employee employeeRomero = new Employee(null,"Romero");
        Employee employeeExpectedJuan = new Employee(1L,"Juan");
        Employee employeeExpectedIndra = new Employee(2L,"Indra");
        Employee employeeExpectedRomero = new Employee(3L,"Romero");
        employeeService.createEmployee(employeeJuan);
        employeeService.createEmployee(employeeIndra);
        employeeService.createEmployee(employeeRomero);
        Flux<Employee> fluxGetall = employeeService.getAllEmployees();
        fluxGetall.subscribe(System.out::println);
        StepVerifier.create(fluxGetall)
                .expectNext(employeeExpectedJuan, employeeExpectedIndra, employeeExpectedRomero)
                .expectComplete()
                .verify();
    }
}
