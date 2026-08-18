package com.github.api.tests;

import com.github.api.models.response.RepositoryResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for listing repositories.
 * GET /users/{username}/repos and GET /user/repos
 */
public class ListReposTests extends BaseTest {

    private static final String PUBLIC_USER = "octocat";

    @Test(description = "Verify GET /users/{username}/repos returns 200 and list of public repositories")
    public void testListUserRepositories_Success() {
        Response response = repoService.listUserRepos(PUBLIC_USER);

        response.then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("size()", greaterThan(0))
                .body("[0].owner.login", equalTo(PUBLIC_USER));

        List<RepositoryResponse> repos = response.jsonPath().getList("", RepositoryResponse.class);
        assertThat(repos).isNotEmpty();
        assertThat(repos.get(0).getOwner().getLogin()).isEqualTo(PUBLIC_USER);
    }

    @Test(description = "Verify GET /users/{username}/repos pagination with per_page query param")
    public void testListUserRepositories_Pagination() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", 2);
        queryParams.put("page", 1);
        queryParams.put("sort", "created");
        queryParams.put("direction", "asc");

        Response response = repoService.listUserRepos(PUBLIC_USER, queryParams);

        response.then()
                .statusCode(200)
                .body("size()", lessThanOrEqualTo(2));
    }

    @Test(description = "Verify GET /user/repos returns authenticated user's repositories")
    public void testListAuthenticatedUserRepositories() {
        requireAuthentication();

        Response response = repoService.listAuthenticatedUserRepos();

        response.then()
                .statusCode(200)
                .contentType(containsString("application/json"));

        List<RepositoryResponse> repos = response.jsonPath().getList("", RepositoryResponse.class);
        assertThat(repos).isNotNull();
    }
}
