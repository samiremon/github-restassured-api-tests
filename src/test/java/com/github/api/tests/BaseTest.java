package com.github.api.tests;

import com.github.api.config.ConfigManager;
import com.github.api.services.RepoService;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.api.reporting.TextReportListener;
import org.testng.annotations.Listeners;

/**
 * Base test class managing shared services, authentication checks, cleanup, and text reporting.
 */
@Listeners(TextReportListener.class)
public abstract class BaseTest {

    protected RepoService repoService = new RepoService();
    private final List<String> createdReposForCleanup = Collections.synchronizedList(new ArrayList<>());
    private static String authenticatedUsername;

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {
        String token = ConfigManager.getToken();
        if (token != null && !token.trim().isEmpty()) {
            try {
                Response response = repoService.getAuthenticatedUser();
                if (response.getStatusCode() == 200) {
                    authenticatedUsername = response.jsonPath().getString("login");
                    System.out.println("Authenticated with GitHub user: " + authenticatedUsername);
                } else {
                    System.out.println("Warning: Token provided but received status code " + response.getStatusCode());
                }
            } catch (Exception e) {
                System.out.println("Warning: Unable to verify authenticated user: " + e.getMessage());
            }
        } else {
            System.out.println("No GitHub token configured. Authenticated CRUD tests requiring write permissions will be skipped or tested for auth rejection.");
        }
    }

    /**
     * Helper to verify if authentication token is present.
     * Skips test execution if write permissions / token is missing.
     */
    protected void requireAuthentication() {
        String token = ConfigManager.getToken();
        if (token == null || token.trim().isEmpty()) {
            throw new SkipException("Skipping test: GitHub token ('github.token' or 'GITHUB_TOKEN') is not configured.");
        }
    }

    /**
     * Get current authenticated username (either fetched from API, config, or fallback).
     */
    protected String getAuthenticatedUsername() {
        if (authenticatedUsername != null && !authenticatedUsername.isEmpty()) {
            return authenticatedUsername;
        }
        String configured = ConfigManager.getUsername();
        if (configured != null && !configured.isEmpty()) {
            return configured;
        }
        return "authenticated-user";
    }

    /**
     * Register a created repository for automatic cleanup after class execution.
     */
    protected void registerRepoForCleanup(String repoName) {
        if (repoName != null && !repoName.trim().isEmpty()) {
            createdReposForCleanup.add(repoName);
        }
    }

    @AfterClass(alwaysRun = true)
    public void cleanupRepositories() {
        String token = ConfigManager.getToken();
        if (token == null || token.trim().isEmpty() || createdReposForCleanup.isEmpty()) {
            return;
        }

        String owner = getAuthenticatedUsername();
        for (String repoName : createdReposForCleanup) {
            try {
                Response response = repoService.deleteRepo(owner, repoName);
                if (response.getStatusCode() == 204) {
                    System.out.println("Successfully cleaned up test repo: " + owner + "/" + repoName);
                } else {
                    System.out.println("Cleanup note: Repo " + repoName + " returned status " + response.getStatusCode());
                }
            } catch (Exception e) {
                System.err.println("Error cleaning up repo " + repoName + ": " + e.getMessage());
            }
        }
        createdReposForCleanup.clear();
    }
}
