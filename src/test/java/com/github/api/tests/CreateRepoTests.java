package com.github.api.tests;

import com.github.api.models.request.CreateRepoPayload;
import com.github.api.models.response.RepositoryResponse;
import com.github.api.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for POST /user/repos endpoint.
 */
public class CreateRepoTests extends BaseTest {

    @Test(description = "Verify POST /user/repos without authentication returns 401 Unauthorized")
    public void testCreateRepository_WithoutAuth_Returns401() {
        CreateRepoPayload payload = CreateRepoPayload.builder()
                .name(TestDataGenerator.generateUniqueRepoName())
                .description("Unauthenticated test repo")
                .build();

        Response response = repoService.createRepoUnauthenticated(payload);

        response.then()
                .statusCode(401)
                .body("message", containsString("Requires authentication"));
    }

    @Test(description = "Verify POST /user/repos with invalid token returns 401 Unauthorized")
    public void testCreateRepository_WithInvalidToken_Returns401() {
        CreateRepoPayload payload = CreateRepoPayload.builder()
                .name(TestDataGenerator.generateUniqueRepoName())
                .description("Invalid token test repo")
                .build();

        Response response = repoService.createRepoWithInvalidToken(payload, "invalid_dummy_token_12345");

        response.then()
                .statusCode(401)
                .body("message", equalTo("Bad credentials"));
    }

    @Test(description = "Verify POST /user/repos creates a new public repository successfully")
    public void testCreatePublicRepository_Success() {
        requireAuthentication();

        String repoName = TestDataGenerator.generateUniqueRepoName("test-pub-");
        String description = "Automated test public repository";
        String homepage = "https://github.com";

        CreateRepoPayload payload = CreateRepoPayload.builder()
                .name(repoName)
                .description(description)
                .homepage(homepage)
                .isPrivate(false)
                .hasIssues(true)
                .hasWiki(true)
                .autoInit(true)
                .build();

        Response response = repoService.createRepo(payload);

        response.then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("name", equalTo(repoName))
                .body("private", equalTo(false))
                .body("description", equalTo(description));

        registerRepoForCleanup(repoName);

        RepositoryResponse repoResponse = response.as(RepositoryResponse.class);
        assertThat(repoResponse).isNotNull();
        assertThat(repoResponse.getName()).isEqualTo(repoName);
        assertThat(repoResponse.getIsPrivate()).isFalse();
        assertThat(repoResponse.getDescription()).isEqualTo(description);
        assertThat(repoResponse.getOwner().getLogin()).isEqualTo(getAuthenticatedUsername());
    }

    @Test(description = "Verify POST /user/repos creates a new private repository successfully")
    public void testCreatePrivateRepository_Success() {
        requireAuthentication();

        String repoName = TestDataGenerator.generateUniqueRepoName("test-priv-");
        CreateRepoPayload payload = CreateRepoPayload.builder()
                .name(repoName)
                .description("Automated test private repository")
                .isPrivate(true)
                .autoInit(false)
                .build();

        Response response = repoService.createRepo(payload);

        response.then()
                .statusCode(201)
                .body("name", equalTo(repoName))
                .body("private", equalTo(true));

        registerRepoForCleanup(repoName);

        RepositoryResponse repoResponse = response.as(RepositoryResponse.class);
        assertThat(repoResponse.getIsPrivate()).isTrue();
    }
}
