package ca.levio.hackathon.repository;

import ca.levio.hackathon.model.Employee;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class WebFluxExampleMockrepository {

    private List<Employee> listOfEmployees;

    public Mono<Employee> createEmployee(Employee employee){
        Mono<Employee> employeeMono = Mono.just(employee);
        if(listOfEmployees == null){
            listOfEmployees = new ArrayList<>();
            listOfEmployees.add(employee);

            return employeeMono;
        }

        listOfEmployees.add(employee);
        return employeeMono;
    }
    public Mono<Employee> getEmployeeById(Long idEmployee){
        if(listOfEmployees.isEmpty()) return Mono.empty();
        Optional<Employee> optionalEmployee = listOfEmployees.stream().filter(e-> e.getId().equals(idEmployee)).findFirst();
        return optionalEmployee.map(Mono::just).orElseGet(Mono::empty);
    }

    public Flux<Employee> getAllEmployees(){
        return Flux.fromIterable(listOfEmployees);
    }
}
