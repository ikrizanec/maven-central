package com.infobip.pmf.course.mavencentral;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.BDDAssertions.catchThrowableOfType;
import static org.assertj.core.api.BDDAssertions.then;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "/prepare_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class MavenCentralIntegrationTest {

    @LocalServerPort
    private int port;
    private RestClient restClient;

    @BeforeEach
    void setUp() { restClient = RestClient.create("http://localhost:" + port);}

    @Test
    void shouldGetLibrary() throws JSONException {
        var response = restClient.get()
                .uri("/libraries")
                .header(HttpHeaders.AUTHORIZATION, "App la9psd71atbpgeg7fvvx")
                .retrieve()
                .toEntity(String.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldGetUnauthorizedException() throws JSONException {
        var exception = catchThrowableOfType(
                () -> restClient.get()
                        .uri("/libraries")
                        .header(HttpHeaders.AUTHORIZATION, "App nekikiriviApiKey")
                        .retrieve()
                        .toEntity(String.class),
                HttpClientErrorException.class
        );
        then(exception.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldGetLibraryNotFoundException() {
        String invalidLibraryId = "100000000000";
        var exception = catchThrowableOfType(
                () -> restClient.get()
                        .uri("/libraries/" + invalidLibraryId)
                        .header(HttpHeaders.AUTHORIZATION, "App la9psd71atbpgeg7fvvx")
                        .retrieve()
                        .toEntity(String.class),
                HttpClientErrorException.class
        );
        then(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateNewLibrary() throws JSONException {
        String newLibraryJson = """
        {
            "groupId": "org.web",
            "artifactId": "spring-boot-web-client",
            "name": "Web client",
            "description": "Web client"
        }
        """;
        var response = restClient.post()
                .uri("/libraries")
                .contentType(MediaType.APPLICATION_JSON)
                .body(newLibraryJson)
                .header(HttpHeaders.AUTHORIZATION, "App la9psd71atbpgeg7fvvx")
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        JSONAssert.assertEquals(newLibraryJson, response.getBody(), false);

        var exception = catchThrowableOfType(
                () -> restClient.post()
                        .uri("/libraries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(newLibraryJson)
                        .header(HttpHeaders.AUTHORIZATION, "App la9psd71atbpgeg7fvvx")
                        .retrieve()
                        .toEntity(String.class),
                HttpClientErrorException.class
        );
        then(exception.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldGetBadRequestForInvalidLibraryInput() throws JSONException {
        String invalidLibraryJson = """
                   {
                    "name": "Valid name",
                    "wrong_attribute": "some value"
                   }
                """;
        var exception = catchThrowableOfType(
                () -> restClient.post()
                        .uri("/libraries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "App la9psd71atbpgeg7fvvx")
                        .body(invalidLibraryJson)
                        .retrieve()
                        .toEntity(String.class),
                HttpClientErrorException.class
        );
        then(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldGetVersions() {
        String validLibraryID = "1";
        var response = restClient.get()
                .uri("/libraries/" + validLibraryID + "/versions" )
                .header(HttpHeaders.AUTHORIZATION, "App la9psd71atbpgeg7fvvx")
                .retrieve()
                .toEntity(String.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldGetVersionNotFoundException() {
        String invalidVersionId = "1000000000";
        var exception = catchThrowableOfType(
                () -> restClient.get()
                        .uri("/libraries/1/versions/" + invalidVersionId)
                        .header(HttpHeaders.AUTHORIZATION, "App la9psd71atbpgeg7fvvx")
                        .retrieve()
                        .toEntity(String.class),
                HttpClientErrorException.class
        );
        then(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldGetVersionDefinitionException() throws JSONException {
        String newVersionJson = """
        {
            "semanticVersion": "6.10.11",
            "description": "New version",
            "deprecated": false
        }
        """;
        var response = restClient.post()
                .uri("/libraries/1/versions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(newVersionJson)
                .header(HttpHeaders.AUTHORIZATION, "App la9psd71atbpgeg7fvvx")
                .retrieve()
                .toEntity(String.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        var exception = catchThrowableOfType(
                () -> restClient.post()
                        .uri("/libraries/1/versions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(newVersionJson)
                        .header(HttpHeaders.AUTHORIZATION, "App la9psd71atbpgeg7fvvx")
                        .retrieve()
                        .toEntity(String.class),
                HttpClientErrorException.class
        );
        then(exception.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldGetBadRequestForInvalidVersionInput() throws JSONException {
        String invalidVersionJson = """
                   {
                    "description": "Description",
                    "releaseDate": "2024-05-23T00:00:00",
                    "wrong_attribute": "1"
                   }
                """;
        var exception = catchThrowableOfType(
                () -> restClient.post()
                        .uri("/libraries/1/versions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "App la9psd71atbpgeg7fvvx")
                        .body(invalidVersionJson)
                        .retrieve()
                        .toEntity(String.class),
                HttpClientErrorException.class
        );
        then(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
