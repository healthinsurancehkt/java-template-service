package ca.levio.hackathon;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Use this test base to create test that will generate open api spec using runtime code introspection.
 * It must set up proper controller(s) context which is application specific
 *
 * <code>
 *
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 * public class SpecGenTest extends SpecGenTestBase {
 * }
 *
 * </code>
 */
@Slf4j
@Tag("openapi")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class SpecGenTestBase {

    @Autowired
    private WebTestClient client;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void generateOpenapiSpec() {

        try {
            var dir = System.getProperty("openapi.dir", "openapi");
            var uri = "/v3/api-docs";

            log.info("Generating API docs from: " + uri);

            var yaml = client.get()
                    .uri(uri)
                    .header("Accept", "application/yaml")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String.class)
                    .returnResult()
                    .getResponseBody();

            var path = Path.of(dir);

            Files.createDirectories(path);
            Files.writeString(Path.of(dir, "openapi.yaml"), yaml);

            log.info("Wrote docs to: " + path.toAbsolutePath());
        } catch (Throwable t) {
            log.error("Error generating: " + t.getMessage(), t);
        }
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        }
    }
}
