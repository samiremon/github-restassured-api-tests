package com.github.api.tests;

import com.github.api.models.request.CreateRepoPayload;
import com.github.api.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Tests for DELETE /repos/{owner}/{repo} endpoint.
 */
public class DeleteRepoTests extends BaseTest {

    @Test(description = "Verify DELETE /repos/{owner}/{repo} successfully deletes an existing repository")
    public void testDeleteRepository_Success() {
        requireAuthentication();

        String repoName = TestDataGenerator.generateUniqueRepoName("test-del-");

        CreateRepoPayload payload = CreateRepoPayload.builder()
                .name(repoName)
                .description("Repository created for deletion test")
                .isPrivate(false)
                .build();

        Response createResponse = repoService.createRepo(payload);
        createResponse.then().statusCode(201);

        String owner = getAuthenticatedUsername();

        // Perform DELETE
        Response deleteResponse = repoService.deleteRepo(owner, repoName);
        deleteResponse.then().statusCode(204);

        // Verify GET now returns 404
        Response getResponse = repoService.getRepo(owner, repoName);
        getResponse.then().statusCode(404);
    }

    @Test(description = "Verify DELETE /repos/{owner}/{repo} on non-existent repo returns 404 Not Found")
    public void testDeleteNonExistentRepository_Returns404() {
        requireAuthentication();

        String owner = getAuthenticatedUsername();
        String nonExistentRepo = "non-existing-repo-to-delete-" + System.currentTimeMillis();

        Response response = repoService.deleteRepo(owner, nonExistentRepo);

        response.then()
                .statusCode(404)
                .body("message", equalTo("Not Found"));
    }
}
