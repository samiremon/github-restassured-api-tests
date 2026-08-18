package com.github.api.tests;

import com.github.api.models.request.CreateRepoPayload;
import com.github.api.models.request.UpdateRepoPayload;
import com.github.api.models.response.RepositoryResponse;
import com.github.api.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * End-to-end lifecycle test for GitHub Repositories API.
 * Flow: POST (Create) -> GET (Verify) -> PATCH (Update) -> GET (Verify Updated) -> DELETE (Remove) -> GET (Verify 404).
 */
public class EndToEndRepoLifecycleTest extends BaseTest {

    @Test(description = "Execute full end-to-end repository CRUD lifecycle")
    public void testFullRepositoryLifecycle() {
        requireAuthentication();

        String repoName = TestDataGenerator.generateUniqueRepoName("test-e2e-");
        String initialDesc = "E2E Initial Description";
        String initialHomepage = "https://example.com/initial";
        String owner = getAuthenticatedUsername();

        // 1. CREATE (POST)
        CreateRepoPayload createPayload = CreateRepoPayload.builder()
                .name(repoName)
                .description(initialDesc)
                .homepage(initialHomepage)
                .isPrivate(false)
                .hasIssues(true)
                .autoInit(true)
                .build();

        Response createResponse = repoService.createRepo(createPayload);
        createResponse.then()
                .statusCode(201)
                .body("name", equalTo(repoName))
                .body("private", equalTo(false));

        RepositoryResponse createdRepo = createResponse.as(RepositoryResponse.class);
        assertThat(createdRepo.getName()).isEqualTo(repoName);
        assertThat(createdRepo.getDescription()).isEqualTo(initialDesc);

        // 2. READ (GET)
        Response getResponse = repoService.getRepo(owner, repoName);
        getResponse.then()
                .statusCode(200)
                .body("name", equalTo(repoName))
                .body("description", equalTo(initialDesc));

        // 3. UPDATE (PATCH)
        String updatedDesc = "E2E Updated Description on " + System.currentTimeMillis();
        String updatedHomepage = "https://example.com/updated";

        UpdateRepoPayload updatePayload = UpdateRepoPayload.builder()
                .description(updatedDesc)
                .homepage(updatedHomepage)
                .build();

        Response updateResponse = repoService.updateRepo(owner, repoName, updatePayload);
        updateResponse.then()
                .statusCode(200)
                .body("description", equalTo(updatedDesc))
                .body("homepage", equalTo(updatedHomepage));

        // 4. VERIFY UPDATE (GET)
        Response getUpdatedResponse = repoService.getRepo(owner, repoName);
        getUpdatedResponse.then()
                .statusCode(200)
                .body("description", equalTo(updatedDesc))
                .body("homepage", equalTo(updatedHomepage));

        // 5. DELETE (DELETE)
        Response deleteResponse = repoService.deleteRepo(owner, repoName);
        deleteResponse.then()
                .statusCode(204);

        // 6. VERIFY DELETION (GET)
        Response getDeletedResponse = repoService.getRepo(owner, repoName);
        getDeletedResponse.then()
                .statusCode(404)
                .body("message", equalTo("Not Found"));
    }
}
