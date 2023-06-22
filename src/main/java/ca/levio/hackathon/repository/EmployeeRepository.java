package ca.levio.hackathon.repository;

import ca.levio.hackathon.model.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long> {

    Mono<Employee> findByName(String name);
}
