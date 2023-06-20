package ca.levio.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(
		description = "Demo API",
		version = "1.0"
))
@SpringBootApplication
public class JavaTemplateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaTemplateServiceApplication.class, args);
	}

}
