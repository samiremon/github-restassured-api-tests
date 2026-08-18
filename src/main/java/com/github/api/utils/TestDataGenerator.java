package com.github.api.utils;

import java.time.Instant;
import java.util.UUID;

/**
 * Utility class to generate test data for GitHub API tests.
 */
public class TestDataGenerator {

    private static final String PREFIX = "test-repo-";

    public static String generateUniqueRepoName() {
        return generateUniqueRepoName(PREFIX);
    }

    public static String generateUniqueRepoName(String prefix) {
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8);
        return prefix + Instant.now().getEpochSecond() + "-" + randomSuffix;
    }

    public static String generateDescription() {
        return "Automated Test Repository created by REST Assured on " + Instant.now();
    }

    public static String generateHomepage() {
        return "https://github.com";
    }
}
