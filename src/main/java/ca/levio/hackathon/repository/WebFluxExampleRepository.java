package ca.levio.hackathon.repository;

import ca.levio.hackathon.model.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebFluxExampleRepository extends ReactiveCrudRepository<Employee, Long> {
}
