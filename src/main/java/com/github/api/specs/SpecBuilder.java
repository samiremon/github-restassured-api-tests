package com.github.api.specs;

import com.github.api.config.ConfigManager;
import com.github.api.constants.HeaderConstants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Builds reusable REST Assured Request and Response Specifications.
 */
public class SpecBuilder {

    /**
     * Creates standard authenticated RequestSpecification.
     */
    public static RequestSpecification getRequestSpec() {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getBaseUri())
                .setContentType(ContentType.JSON)
                .addHeader(HeaderConstants.ACCEPT, HeaderConstants.GITHUB_MEDIA_TYPE)
                .addHeader(HeaderConstants.GITHUB_API_VERSION, ConfigManager.getApiVersion())
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.BODY);

        String token = ConfigManager.getToken();
        if (token != null && !token.trim().isEmpty()) {
            builder.addHeader(HeaderConstants.AUTHORIZATION, "Bearer " + token.trim());
        }

        return builder.build();
    }

    /**
     * Creates unauthenticated RequestSpecification (without Authorization header).
     */
    public static RequestSpecification getUnauthenticatedRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getBaseUri())
                .setContentType(ContentType.JSON)
                .addHeader(HeaderConstants.ACCEPT, HeaderConstants.GITHUB_MEDIA_TYPE)
                .addHeader(HeaderConstants.GITHUB_API_VERSION, ConfigManager.getApiVersion())
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.BODY)
                .build();
    }

    /**
     * Creates RequestSpecification with invalid token for negative testing.
     */
    public static RequestSpecification getInvalidAuthRequestSpec(String invalidToken) {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getBaseUri())
                .setContentType(ContentType.JSON)
                .addHeader(HeaderConstants.ACCEPT, HeaderConstants.GITHUB_MEDIA_TYPE)
                .addHeader(HeaderConstants.GITHUB_API_VERSION, ConfigManager.getApiVersion())
                .addHeader(HeaderConstants.AUTHORIZATION, "Bearer " + invalidToken)
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.BODY)
                .build();
    }

    /**
     * Creates ResponseSpecification with expected HTTP status code.
     */
    public static ResponseSpecification getResponseSpec(int expectedStatusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .log(LogDetail.ALL)
                .build();
    }
}
