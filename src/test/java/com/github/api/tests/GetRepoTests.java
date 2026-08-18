package com.github.api.tests;

import com.github.api.models.response.RepositoryResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for GET /repos/{owner}/{repo} endpoint.
 */
public class GetRepoTests extends BaseTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";

    @Test(description = "Verify GET /repos/{owner}/{repo} returns 200 and correct repository details")
    public void testGetPublicRepository_Success() {
        Response response = repoService.getRepoUnauthenticated(OWNER, REPO);

        // Verify status code & content type
        response.then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("name", equalTo("Hello-World"))
                .body("owner.login", equalTo("octocat"))
                .body("private", equalTo(false))
                .body("default_branch", notNullValue());

        // Deserialize to POJO and assert with AssertJ
        RepositoryResponse repoResponse = response.as(RepositoryResponse.class);
        assertThat(repoResponse).isNotNull();
        assertThat(repoResponse.getName()).isEqualTo("Hello-World");
        assertThat(repoResponse.getOwner()).isNotNull();
        assertThat(repoResponse.getOwner().getLogin()).isEqualTo("octocat");
        assertThat(repoResponse.getIsPrivate()).isFalse();
        assertThat(repoResponse.getHtmlUrl()).contains("github.com/octocat/Hello-World");
    }

    @Test(description = "Verify GET /repos/{owner}/{repo} response conforms to JSON schema")
    public void testGetRepository_SchemaValidation() {
        InputStream schemaStream = getClass().getClassLoader().getResourceAsStream("schemas/repository-schema.json");
        assertThat(schemaStream).as("repository-schema.json must exist in test resources").isNotNull();

        Response response = repoService.getRepoUnauthenticated(OWNER, REPO);

        response.then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(schemaStream));
    }

    @Test(description = "Verify GET /repos/{owner}/{repo} with non-existent repo returns 404 Not Found")
    public void testGetNonExistentRepository_NotFound() {
        String nonExistentRepo = "non-existing-repository-xyz-" + System.currentTimeMillis();

        Response response = repoService.getRepoUnauthenticated(OWNER, nonExistentRepo);

        response.then()
                .statusCode(404)
                .body("message", equalTo("Not Found"));
    }
}
