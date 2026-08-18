package com.github.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manages framework configuration properties loaded from config.properties
 * or overridden by system environment variables / JVM properties.
 */
public class ConfigManager {

    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE_PATH = "config.properties";

    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
            if (input != null) {
                properties.load(input);
            } else {
                System.out.println("Warning: " + CONFIG_FILE_PATH + " not found in classpath. Falling back to environment variables.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load " + CONFIG_FILE_PATH + ": " + e.getMessage());
        }
    }

    /**
     * Get property value by key with priority:
     * 1. System property (-Dkey=value)
     * 2. Environment variable (KEY or key)
     * 3. config.properties file
     * 4. Default fallback value
     */
    public static String getProperty(String key, String defaultValue) {
        // 1. Check JVM system property
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.trim().isEmpty()) {
            return sysProp.trim();
        }

        // 2. Check Environment variable
        String envKey = key.toUpperCase().replace('.', '_');
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.trim().isEmpty()) {
            return envValue.trim();
        }
        String directEnv = System.getenv(key);
        if (directEnv != null && !directEnv.trim().isEmpty()) {
            return directEnv.trim();
        }

        // 3. Check loaded properties
        String propValue = properties.getProperty(key);
        if (propValue != null && !propValue.trim().isEmpty()) {
            return propValue.trim();
        }

        return defaultValue;
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    public static String getBaseUri() {
        return getProperty("github.base.uri", "https://api.github.com");
    }

    public static String getToken() {
        return getProperty("github.token", "");
    }

    public static String getUsername() {
        return getProperty("github.username", "");
    }

    public static String getApiVersion() {
        return getProperty("github.api.version", "2022-11-28");
    }
}
