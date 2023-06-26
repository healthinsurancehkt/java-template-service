package ca.levio.hackathon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/greet")
@Tag(name = "Greet Controller")
public class GreetController {

    @GetMapping
    @Operation(summary = "Get a greet")
    public Mono<ResponseEntity<String>> getEmployeeById() {
        return Mono.just(ResponseEntity.ok("Hey there!"));
    }
}
