package com.github.api.constants;

/**
 * Standard HTTP Header constants for GitHub REST API v3.
 */
public final class HeaderConstants {

    private HeaderConstants() {
        // Prevent instantiation
    }

    public static final String ACCEPT = "Accept";
    public static final String GITHUB_MEDIA_TYPE = "application/vnd.github+json";
    public static final String AUTHORIZATION = "Authorization";
    public static final String GITHUB_API_VERSION = "X-GitHub-Api-Version";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
}
