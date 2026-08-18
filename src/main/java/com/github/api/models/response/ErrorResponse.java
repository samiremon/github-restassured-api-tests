package com.github.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * Response model for GitHub API error responses (4xx, 5xx).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("documentation_url")
    private String documentationUrl;

    @JsonProperty("errors")
    private List<Map<String, Object>> errors;

    @JsonProperty("status")
    private String status;

    public ErrorResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public List<Map<String, Object>> getErrors() {
        return errors;
    }

    public void setErrors(List<Map<String, Object>> errors) {
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
