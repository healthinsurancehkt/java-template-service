package ca.levio.hackathon.controller;

import ca.levio.hackathon.model.Employee;
import ca.levio.hackathon.service.WebFluxExampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Tag(name = "Employee Dashboard")
public class WebFluxExampleController {

    private final WebFluxExampleService webFluxExampleService;

    @PostMapping("/create")
    @Operation(summary = "Create new Employee")
    public ResponseEntity<Mono<Employee>> createEmployee(@RequestBody Employee employee){
        Mono<Employee> e = webFluxExampleService.createEmployee(employee);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }
    @GetMapping("/findById/{id}")
    @Operation(summary = "Fetch Employee data by ID")
    public ResponseEntity<Mono<Employee>> getEmployeeById(@PathVariable("id") Long idEmployee){
        Mono<Employee> e = webFluxExampleService.getEmployeeById(idEmployee);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    @Operation(summary = "Fetch all Employee Data")
    public ResponseEntity<Flux<Employee>> getAllEmployees(){
        Flux<Employee> e = webFluxExampleService.getAllEmployees();
        return new ResponseEntity<>(e, HttpStatus.OK);
    }
}
