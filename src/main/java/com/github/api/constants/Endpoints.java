package com.github.api.constants;

/**
 * GitHub REST API endpoints constants.
 * Reference: https://docs.github.com/en/rest/repos
 */
public final class Endpoints {

    private Endpoints() {
        // Prevent instantiation
    }

    // Repository Endpoints
    public static final String USER_REPOS = "/user/repos";
    public static final String REPOS_OWNER_REPO = "/repos/{owner}/{repo}";
    public static final String USERS_REPOS = "/users/{username}/repos";
    public static final String ORGS_REPOS = "/orgs/{org}/repos";
    public static final String AUTHENTICATED_USER = "/user";
}
