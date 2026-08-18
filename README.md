# GitHub REST Assured API Testing Framework

A test automation framework built in **Java 17** using **REST Assured**, **TestNG**, **Jackson**, **AssertJ**, and **JSON Schema Validator** for testing the [GitHub REST API v3 (Repositories)](https://docs.github.com/en/rest/repos).

---

## 📁 Project Structure

```
REST Assured Project/
├── pom.xml                               # Maven project dependencies & plugins
└── src/
    ├── main/
    │   └── java/com/github/api/
    │       ├── config/
    │       │   └── ConfigManager.java    # Dynamic configuration & token management
    │       ├── constants/
    │       │   ├── Endpoints.java        # GitHub API route paths
    │       │   └── HeaderConstants.java  # Headers (Accept, API-Version, etc.)
    │       ├── models/
    │       │   ├── request/              # Jackson request POJOs (CreateRepoPayload, UpdateRepoPayload)
    │       │   └── response/             # Jackson response POJOs (RepositoryResponse, OwnerResponse, ErrorResponse)
    │       ├── services/
    │       │   └── RepoService.java      # Reusable API client encapsulating REST Assured calls
    │       ├── specs/
    │       │   └── SpecBuilder.java      # Request/Response Specifications (Auth, Base URI, Headers)
    │       └── utils/
    │           └── TestDataGenerator.java# Random data generators for unique test executions
    └── test/
        ├── java/com/github/api/tests/
        │   ├── BaseTest.java             # Suite setup, token discovery, automatic cleanup
        │   ├── GetRepoTests.java         # Public repo retrieval, schema validation, 404 handling
        │   ├── ListReposTests.java       # User repos listing, query params, pagination
        │   ├── CreateRepoTests.java      # Public/Private repo creation, 401 Unauthorized tests
        │   ├── UpdateRepoTests.java      # PATCH repo updates (description, homepage, settings)
        │   ├── DeleteRepoTests.java      # DELETE repo and 404 verification
        │   └── EndToEndRepoLifecycleTest.java # Full CRUD lifecycle test in sequence
        └── resources/
            ├── config.properties         # Base URL and GitHub token configuration
            ├── schemas/
            │   └── repository-schema.json# JSON Schema for GitHub Repository response validation
            └── testng.xml                # TestNG test suite configuration
```

---

## 🚀 Key Features

- **Standard Design Pattern**: Clean separation of models (POJOs), API service clients (`RepoService`), specifications (`SpecBuilder`), and tests.
- **POJO Serialization & Deserialization**: Jackson annotations for type-safe requests and responses.
- **Builder Pattern**: Fluent builder patterns for constructing API request payloads (`CreateRepoPayload`, `UpdateRepoPayload`).
- **Response Validation**: Fluent assertions via **AssertJ**, JSONPath matchers via **Hamcrest**, and **JSON Schema Validation**.
- **Automated Test Cleanup**: `BaseTest` tracks created test repositories and deletes them automatically in `@AfterClass` teardown.
- **Graceful Authentication Handling**: Public read and 401 negative tests run out-of-the-box. Tests requiring write permissions check for token availability and skip cleanly if not provided.

---

## 🔑 GitHub Authentication Setup

GitHub API write operations (create, update, delete) require a **GitHub Personal Access Token (PAT)**.

### 1. Generating a Personal Access Token
1. Go to **GitHub** → **Settings** → **Developer Settings** → **Personal Access Tokens** → **Tokens (classic)** (or Fine-grained tokens).
2. Generate a token with the following scopes:
   - `repo` (Full control of private repositories)
   - `delete_repo` (Permission to delete repositories)
3. Copy the generated token.

### 2. Providing the Token to the Tests
You can provide the token in **any of the following 3 ways**:

#### Option A: In `src/test/resources/config.properties`
```properties
github.base.uri=https://api.github.com
github.api.version=2022-11-28
github.token=ghp_yourPersonalAccessTokenHere
github.username=yourGitHubUsername
```

#### Option B: Environment Variable (Recommended for CI/CD)
- On Windows (PowerShell):
  ```powershell
  $env:GITHUB_TOKEN="ghp_yourPersonalAccessTokenHere"
  $env:GITHUB_USERNAME="yourGitHubUsername"
  ```
- On Linux/macOS:
  ```bash
  export GITHUB_TOKEN="ghp_yourPersonalAccessTokenHere"
  export GITHUB_USERNAME="yourGitHubUsername"
  ```

#### Option C: JVM System Property (Command Line / Maven)
```bash
mvn test -Dgithub.token=ghp_yourPersonalAccessTokenHere -Dgithub.username=yourGitHubUsername
```

---

## 🧪 Running the Tests

### In IntelliJ IDEA / Eclipse:
- Right-click `src/test/resources/testng.xml` → **Run 'testng.xml'**
- Or right-click any test class in `src/test/java/com/github/api/tests/` and select **Run**.

### Via Maven CLI:
```bash
mvn clean test
```

---

## 📄 Plain Text Test Reports (.txt)

After executing tests, a clean and lightweight plain-text execution report is automatically generated:
- **`reports/test-report.txt`**

> **Note:** Ignored/skipped tests are intentionally excluded from this report so you only see actual executed tests (Passes and Failures) along with their execution time and descriptions.

### Viewing the Report:
Open `reports/test-report.txt` directly in IntelliJ IDEA or any text editor.

---

## 📋 API Test Coverage Overview

| HTTP Method | Endpoint | Description | Expected Status |
|---|---|---|---|
| `GET` | `/repos/{owner}/{repo}` | Fetch repository details | `200 OK` |
| `GET` | `/repos/{owner}/{repo}` | Validate JSON schema | `200 OK` |
| `GET` | `/repos/{owner}/{repo}` | Non-existent repository | `404 Not Found` |
| `GET` | `/users/{username}/repos` | List public user repositories | `200 OK` |
| `GET` | `/users/{username}/repos?per_page=2` | Pagination testing | `200 OK` |
| `POST` | `/user/repos` | Create repo without auth | `401 Unauthorized` |
| `POST` | `/user/repos` | Create public/private repo | `201 Created` |
| `PATCH` | `/repos/{owner}/{repo}` | Update description & settings | `200 OK` |
| `DELETE` | `/repos/{owner}/{repo}` | Delete test repository | `204 No Content` |
| `CRUD` | Full Lifecycle | Create → Read → Update → Delete → Verify 404 | End-to-End |
