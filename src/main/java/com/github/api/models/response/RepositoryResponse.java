package com.github.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response model mapping GitHub Repository payload.
 * Reference: https://docs.github.com/en/rest/repos
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("private")
    private Boolean isPrivate;

    @JsonProperty("owner")
    private OwnerResponse owner;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("description")
    private String description;

    @JsonProperty("fork")
    private Boolean fork;

    @JsonProperty("url")
    private String url;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("pushed_at")
    private String pushedAt;

    @JsonProperty("git_url")
    private String gitUrl;

    @JsonProperty("ssh_url")
    private String sshUrl;

    @JsonProperty("clone_url")
    private String cloneUrl;

    @JsonProperty("svn_url")
    private String svnUrl;

    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("stargazers_count")
    private Integer stargazersCount;

    @JsonProperty("watchers_count")
    private Integer watchersCount;

    @JsonProperty("language")
    private String language;

    @JsonProperty("has_issues")
    private Boolean hasIssues;

    @JsonProperty("has_projects")
    private Boolean hasProjects;

    @JsonProperty("has_downloads")
    private Boolean hasDownloads;

    @JsonProperty("has_wiki")
    private Boolean hasWiki;

    @JsonProperty("has_pages")
    private Boolean hasPages;

    @JsonProperty("forks_count")
    private Integer forksCount;

    @JsonProperty("archived")
    private Boolean archived;

    @JsonProperty("disabled")
    private Boolean disabled;

    @JsonProperty("open_issues_count")
    private Integer openIssuesCount;

    @JsonProperty("visibility")
    private String visibility;

    @JsonProperty("default_branch")
    private String defaultBranch;

    public RepositoryResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public OwnerResponse getOwner() {
        return owner;
    }

    public void setOwner(OwnerResponse owner) {
        this.owner = owner;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFork() {
        return fork;
    }

    public void setFork(Boolean fork) {
        this.fork = fork;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPushedAt() {
        return pushedAt;
    }

    public void setPushedAt(String pushedAt) {
        this.pushedAt = pushedAt;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getSshUrl() {
        return sshUrl;
    }

    public void setSshUrl(String sshUrl) {
        this.sshUrl = sshUrl;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public void setCloneUrl(String cloneUrl) {
        this.cloneUrl = cloneUrl;
    }

    public String getSvnUrl() {
        return svnUrl;
    }

    public void setSvnUrl(String svnUrl) {
        this.svnUrl = svnUrl;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(Integer stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public Integer getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(Integer watchersCount) {
        this.watchersCount = watchersCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public Boolean getHasDownloads() {
        return hasDownloads;
    }

    public void setHasDownloads(Boolean hasDownloads) {
        this.hasDownloads = hasDownloads;
    }

    public Boolean getHasWiki() {
        return hasWiki;
    }

    public void setHasWiki(Boolean hasWiki) {
        this.hasWiki = hasWiki;
    }

    public Boolean getHasPages() {
        return hasPages;
    }

    public void setHasPages(Boolean hasPages) {
        this.hasPages = hasPages;
    }

    public Integer getForksCount() {
        return forksCount;
    }

    public void setForksCount(Integer forksCount) {
        this.forksCount = forksCount;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getOpenIssuesCount() {
        return openIssuesCount;
    }

    public void setOpenIssuesCount(Integer openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }
}
