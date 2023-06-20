package ca.levio.hackathon.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table(name = "Employee")
public class Employee {

    @Id
    @Column("id")
    private Long id;
    @Column("name")
    private String name;
}
