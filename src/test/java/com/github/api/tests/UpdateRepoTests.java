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
 * Tests for PATCH /repos/{owner}/{repo} endpoint.
 */
public class UpdateRepoTests extends BaseTest {

    @Test(description = "Verify PATCH /repos/{owner}/{repo} updates description and homepage successfully")
    public void testUpdateRepository_Success() {
        requireAuthentication();

        String repoName = TestDataGenerator.generateUniqueRepoName("test-upd-");
        String initialDesc = "Initial description before update";

        CreateRepoPayload createPayload = CreateRepoPayload.builder()
                .name(repoName)
                .description(initialDesc)
                .isPrivate(false)
                .build();

        Response createResponse = repoService.createRepo(createPayload);
        createResponse.then().statusCode(201);
        registerRepoForCleanup(repoName);

        String updatedDesc = "Updated repository description - " + System.currentTimeMillis();
        String updatedHomepage = "https://github.com/updated-site";

        UpdateRepoPayload updatePayload = UpdateRepoPayload.builder()
                .description(updatedDesc)
                .homepage(updatedHomepage)
                .hasWiki(false)
                .build();

        Response updateResponse = repoService.updateRepo(getAuthenticatedUsername(), repoName, updatePayload);

        updateResponse.then()
                .statusCode(200)
                .body("name", equalTo(repoName))
                .body("description", equalTo(updatedDesc))
                .body("homepage", equalTo(updatedHomepage));

        RepositoryResponse repo = updateResponse.as(RepositoryResponse.class);
        assertThat(repo.getDescription()).isEqualTo(updatedDesc);
        assertThat(repo.getHomepage()).isEqualTo(updatedHomepage);
    }
}
