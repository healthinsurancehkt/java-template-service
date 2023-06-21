package ca.levio.hackathon;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Use this test base to create test that will generate open api spec using runtime code introspection.
 * It must set up proper controller(s) context which is application specific
 *
 * <code>
 *
 * @RedisTest //e.g. if redis required to build context
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 * public class SpecGenTest extends SpecGenTestBase {
 * }
 *
 * </code>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@Tag(name="openapi")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class SpecGenTestBase {
    @Autowired
    private WebTestClient client;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void generateOpenapiSpec() {
            var dir = System.getProperty("openapi.dir", "openapi");
            var uri = "/v3/api-docs";

            log.info("Generating API docs from: " + uri);

            var yaml = client.get()
                    .uri(uri + ".yaml")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String.class)
                    .returnResult()
                    .getResponseBody();


    }
}
