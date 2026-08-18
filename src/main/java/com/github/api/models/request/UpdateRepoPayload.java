package com.github.api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Payload model for updating a GitHub repository.
 * Endpoint: PATCH /repos/{owner}/{repo}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateRepoPayload {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("private")
    private Boolean isPrivate;

    @JsonProperty("has_issues")
    private Boolean hasIssues;

    @JsonProperty("has_projects")
    private Boolean hasProjects;

    @JsonProperty("has_wiki")
    private Boolean hasWiki;

    @JsonProperty("archived")
    private Boolean archived;

    public UpdateRepoPayload() {
    }

    private UpdateRepoPayload(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.homepage = builder.homepage;
        this.isPrivate = builder.isPrivate;
        this.hasIssues = builder.hasIssues;
        this.hasProjects = builder.hasProjects;
        this.hasWiki = builder.hasWiki;
        this.archived = builder.archived;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Boolean getHasIssues() {
        return hasIssues;
    }

    public void setHasIssues(Boolean hasIssues) {
        this.hasIssues = hasIssues;
    }

    public Boolean getHasProjects() {
        return hasProjects;
    }

    public void setHasProjects(Boolean hasProjects) {
        this.hasProjects = hasProjects;
    }

    public Boolean getHasWiki() {
        return hasWiki;
    }

    public void setHasWiki(Boolean hasWiki) {
        this.hasWiki = hasWiki;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public static class Builder {
        private String name;
        private String description;
        private String homepage;
        private Boolean isPrivate;
        private Boolean hasIssues;
        private Boolean hasProjects;
        private Boolean hasWiki;
        private Boolean archived;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder homepage(String homepage) {
            this.homepage = homepage;
            return this;
        }

        public Builder isPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public Builder hasIssues(Boolean hasIssues) {
            this.hasIssues = hasIssues;
            return this;
        }

        public Builder hasProjects(Boolean hasProjects) {
            this.hasProjects = hasProjects;
            return this;
        }

        public Builder hasWiki(Boolean hasWiki) {
            this.hasWiki = hasWiki;
            return this;
        }

        public Builder archived(Boolean archived) {
            this.archived = archived;
            return this;
        }

        public UpdateRepoPayload build() {
            return new UpdateRepoPayload(this);
        }
    }
}
