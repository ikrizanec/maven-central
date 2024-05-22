package com.infobip.pmf.course.mavencentral.api;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "/prepare_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LibraryControllerTest {

    @LocalServerPort
    private int port;
    private RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    public void shouldGetAllLibraries() throws Exception{
        // when
        var response = restClient.get()
                .uri("/libraries")
                .retrieve()
                .toEntity(String.class);
        //then
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
