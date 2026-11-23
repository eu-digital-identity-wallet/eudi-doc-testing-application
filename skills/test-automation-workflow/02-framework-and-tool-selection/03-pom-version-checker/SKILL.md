---
name: pom-version-checker
description: Use when checking the test automation framework's dependency versions (Appium, Selenium, Serenity, Cucumber, Maven plugins) for compatibility, known vulnerabilities, and upgrade opportunities. Triggers on "check dependency versions", "are our libraries up to date", "check for CVEs", "upgrade framework", "dependency audit", "version conflict".
---

# POM Version Checker

Audit the test automation framework's Maven dependencies for version compatibility, known security vulnerabilities, deprecation warnings, and upgrade opportunities.

## 1. Read the project POM

Read `pom.xml` (repo root) and extract:
- All `<dependency>` entries (groupId, artifactId, version, scope)
- All `<plugin>` entries (groupId, artifactId, version)
- `<properties>` that define version variables
- `<dependencyManagement>` section
- Parent POM reference (if any)

Key dependencies to check in this project:

| Dependency | Why it matters |
|---|---|
| `io.appium:appium-java-client` | Core mobile driver — version must match Appium Server |
| `org.seleniumhq.selenium:selenium-java` | Web/hybrid element support |
| `net.serenity-bdd:serenity-core` / `serenity-junit` / `serenity-cucumber` | Reporting + BDD integration |
| `io.cucumber:cucumber-java` / `cucumber-junit` | Gherkin execution |
| `org.junit.jupiter:junit-jupiter` | Test runner |
| `com.browserstack:browserstack-client` | BrowserStack API integration |
| `org.apache.maven.plugins:maven-surefire-plugin` | Test execution plugin |
| `org.apache.maven.plugins:maven-failsafe-plugin` | Integration test plugin |
| `net.serenity-bdd.maven.plugins:serenity-maven-plugin` | Report generation |

## 2. Check for version conflicts

- Identify transitive dependency conflicts using:
  ```
  mvn dependency:tree -Dverbose
  ```
- Flag any dependency that appears at multiple versions.
- Flag any `<scope>compile</scope>` on a library that should be `test` scope.
- Flag duplicate dependencies (same groupId:artifactId declared more than once).

## 3. Check for known vulnerabilities

For each dependency, check against:
- Maven Central's latest release
- GitHub Security Advisories (GHSA)
- NVD (National Vulnerability Database) for CVEs

Report format per vulnerable dependency:
```
### <groupId>:<artifactId> <version>
- CVE: <CVE-ID> (severity: <HIGH/MEDIUM/LOW>)
- Description: <one-line summary>
- Fixed in: <version>
- Recommendation: upgrade to <version>
```

## 4. Check for deprecations and EOL

- Flag dependencies that are marked deprecated in their latest release notes.
- Flag dependencies whose last release was > 12 months ago (stale).
- Flag Appium/Selenium version mismatches (e.g. Appium 2.x requires Selenium 4.x).

## 5. Check Appium Server compatibility

The local Appium Server version must be compatible with `appium-java-client`:

| appium-java-client | Compatible Appium Server |
|---|---|
| 8.x–9.x | Appium 1.22+ |
| 10.x+ | Appium 2.0+ |

Verify the Appium Server version (if available locally):
```
appium --version
```

## 6. Output

### Summary table

| Dependency | Current | Latest | Status | Action |
|---|---|---|---|---|
| io.appium:appium-java-client | 9.3.0 | 10.3.0 | ⚠️ Behind | Upgrade |
| org.seleniumhq.selenium:selenium-java | 4.21.0 | 4.25.0 | ⚠️ Behind | Upgrade |
| ... | ... | ... | ✅ Current | None |

### Vulnerabilities found
(List or "No known CVEs for current versions.")

### Conflicts detected
(List or "No conflicts.")

### Recommended upgrade plan
Ordered list of upgrades with:
- New version
- Breaking changes (if any)
- Migration notes
- Risk level (Safe / Moderate / High)

## 7. Rules

- Never modify `pom.xml` — this skill only reports. The user decides what to upgrade.
- Never recommend a version that has not been released to Maven Central.
- Always check for breaking changes before recommending a major version bump.
- If a dependency is managed by a BOM (Bill of Materials), note that the version is inherited.
- Distinguish between `test` scope and `compile` scope — only flag scope issues for test libraries.
