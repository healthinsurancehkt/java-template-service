package ca.levio.hackathon.controller;

import ca.levio.hackathon.model.Employee;
import ca.levio.hackathon.repository.EmployeeRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Order(1)
    public void saveEmployeeTest() {
        webTestClient.post()
                .uri("/v1/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\"name\":\"John\"}"))
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("{\"id\":1,\"name\":\"John\"}");
    }

    @Test
    @Order(2)
    public void getEmployeeById() {
        webTestClient.get()
                .uri("/v1/employee/findById/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("{\"id\":1,\"name\":\"John\"}");

    }

    @Test
    @Order(3)
    public void getAllEmployee() {
        StepVerifier.create(employeeRepository.save(new Employee(null, "Indra")))
                .expectNextCount(1)
                .verifyComplete();

        webTestClient.get()
                .uri("/v1/employee/findAll")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[{\"id\":1,\"name\":\"John\"},{\"id\":2,\"name\":\"Indra\"}]");
    }

}
