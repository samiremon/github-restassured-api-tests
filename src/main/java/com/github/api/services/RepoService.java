package com.github.api.services;

import com.github.api.constants.Endpoints;
import com.github.api.models.request.CreateRepoPayload;
import com.github.api.models.request.UpdateRepoPayload;
import com.github.api.specs.SpecBuilder;
import io.restassured.response.Response;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Service class encapsulating GitHub Repositories API operations.
 */
public class RepoService {

    /**
     * Create a new repository for the authenticated user.
     * POST /user/repos
     */
    public Response createRepo(CreateRepoPayload payload) {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .body(payload)
                .when()
                .post(Endpoints.USER_REPOS);
    }

    /**
     * Attempt creating a repository without authentication (for negative tests).
     */
    public Response createRepoUnauthenticated(CreateRepoPayload payload) {
        return given()
                .spec(SpecBuilder.getUnauthenticatedRequestSpec())
                .body(payload)
                .when()
                .post(Endpoints.USER_REPOS);
    }

    /**
     * Attempt creating a repository with invalid token (for negative tests).
     */
    public Response createRepoWithInvalidToken(CreateRepoPayload payload, String invalidToken) {
        return given()
                .spec(SpecBuilder.getInvalidAuthRequestSpec(invalidToken))
                .body(payload)
                .when()
                .post(Endpoints.USER_REPOS);
    }

    /**
     * Get repository details by owner and repo name.
     * GET /repos/{owner}/{repo}
     */
    public Response getRepo(String owner, String repoName) {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .pathParam("owner", owner)
                .pathParam("repo", repoName)
                .when()
                .get(Endpoints.REPOS_OWNER_REPO);
    }

    /**
     * Get public repository details without auth.
     * GET /repos/{owner}/{repo}
     */
    public Response getRepoUnauthenticated(String owner, String repoName) {
        return given()
                .spec(SpecBuilder.getUnauthenticatedRequestSpec())
                .pathParam("owner", owner)
                .pathParam("repo", repoName)
                .when()
                .get(Endpoints.REPOS_OWNER_REPO);
    }

    /**
     * List repositories for the authenticated user.
     * GET /user/repos
     */
    public Response listAuthenticatedUserRepos() {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .when()
                .get(Endpoints.USER_REPOS);
    }

    /**
     * List repositories for the authenticated user with query parameters (e.g. per_page, sort).
     * GET /user/repos
     */
    public Response listAuthenticatedUserRepos(Map<String, ?> queryParams) {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .queryParams(queryParams)
                .when()
                .get(Endpoints.USER_REPOS);
    }

    /**
     * List public repositories for a specified user.
     * GET /users/{username}/repos
     */
    public Response listUserRepos(String username) {
        return given()
                .spec(SpecBuilder.getUnauthenticatedRequestSpec())
                .pathParam("username", username)
                .when()
                .get(Endpoints.USERS_REPOS);
    }

    /**
     * List public repositories for a specified user with query parameters.
     * GET /users/{username}/repos
     */
    public Response listUserRepos(String username, Map<String, ?> queryParams) {
        return given()
                .spec(SpecBuilder.getUnauthenticatedRequestSpec())
                .pathParam("username", username)
                .queryParams(queryParams)
                .when()
                .get(Endpoints.USERS_REPOS);
    }

    /**
     * Update a repository.
     * PATCH /repos/{owner}/{repo}
     */
    public Response updateRepo(String owner, String repoName, UpdateRepoPayload payload) {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .pathParam("owner", owner)
                .pathParam("repo", repoName)
                .body(payload)
                .when()
                .patch(Endpoints.REPOS_OWNER_REPO);
    }

    /**
     * Delete a repository.
     * DELETE /repos/{owner}/{repo}
     */
    public Response deleteRepo(String owner, String repoName) {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .pathParam("owner", owner)
                .pathParam("repo", repoName)
                .when()
                .delete(Endpoints.REPOS_OWNER_REPO);
    }

    /**
     * Get authenticated user profile.
     * GET /user
     */
    public Response getAuthenticatedUser() {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .when()
                .get(Endpoints.AUTHENTICATED_USER);
    }
}
