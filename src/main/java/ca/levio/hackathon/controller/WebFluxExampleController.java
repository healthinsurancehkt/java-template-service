package ca.levio.hackathon.controller;

import ca.levio.hackathon.entity.Employee;
import ca.levio.hackathon.service.WebFluxExampleService;
import ca.levio.hackathon.service.WebFluxExampleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class WebFluxExampleController {

    private final WebFluxExampleService webFluxExampleService;

    @PostMapping("/create")
    public ResponseEntity<Mono<Employee>> createEmployee(@RequestBody Employee employee){
        Mono<Employee> e = webFluxExampleService.createEmployee(employee);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<Mono<Employee>> getEmployeeById(@PathVariable("id") Long idEmployee){
        Mono<Employee> e = webFluxExampleService.getEmployeeById(idEmployee);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Flux<Employee>> getAllEmployees(){
        Flux<Employee> e = webFluxExampleService.getAllEmployees();
        e.subscribe(System.out::println);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }
}
