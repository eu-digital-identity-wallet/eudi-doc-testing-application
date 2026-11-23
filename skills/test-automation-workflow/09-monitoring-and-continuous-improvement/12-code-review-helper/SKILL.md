---
name: code-review-helper
description: Use when reviewing test automation code changes (Page Objects, step definitions, test scripts, utilities) for quality, maintainability, and adherence to project conventions. Triggers on "review this code", "code review", "check my changes", "review the PR", "is this code good", "review the diff".
---

# Code Review Helper

Review test automation code changes for correctness, maintainability, and adherence to the EUDI Wallet test project's conventions. Provides structured, actionable feedback focused on issues that matter.

## 1. Scope of review

Review these file types:
- `*Elements.java` — Page Object locator files
- `*Steps.java` — Cucumber step definitions
- `*Test.java` / `*Runner.java` — Test runner classes
- `src/test/java/**/utils/**` — Utility/helper classes
- `src/test/java/**/base/**` — Base classes, driver factories
- `*.feature` — Gherkin feature files
- `pom.xml` — Dependency changes
- `env.properties` — Configuration changes

## 2. Review checklist

### 2.1 Correctness (will it work?)

- [ ] Locators match actual element attributes (no typos in IDs, text, selectors)
- [ ] Step definitions match the Gherkin step text exactly (word-for-word)
- [ ] No hardcoded values that should come from `env.properties` or data files
- [ ] Waits are explicit (`ExpectedConditions`, custom wait) — no `Thread.sleep()`
- [ ] Exception handling does not swallow errors silently
- [ ] Null safety: elements that may not exist are handled (presence check before interaction)
- [ ] Platform-specific code is in the correct platform file (Android code not in iOS file)

### 2.2 Maintainability (will it survive?)

- [ ] Locators use the highest-priority stable strategy (not XPath when a native option exists)
- [ ] No magic numbers or strings — use named constants or config values
- [ ] Methods are single-purpose (a method that does 5 things should be split)
- [ ] No duplicate code — repeated logic should be extracted to a utility or base method
- [ ] Naming is clear and consistent with the project's conventions
- [ ] Comments explain WHY, not WHAT (the code should be self-documenting for the WHAT)
- [ ] No commented-out code left in the file

### 2.3 Test quality (does it test the right thing?)

- [ ] Each test/scenario tests one thing (not 5 assertions checking 5 unrelated features)
- [ ] Test data is parameterized (not hardcoded in the step definition)
- [ ] Assertions verify the actual outcome (not just "no exception was thrown")
- [ ] Negative tests verify the expected error/failure state (not just absence of success)
- [ ] Test independence: no test depends on side effects of another test
- [ ] Cleanup: any data created during the test is cleaned up (or documented as acceptable)

### 2.4 Security

- [ ] No credentials, tokens, or API keys hardcoded in source files
- [ ] No sensitive data logged (user PII, tokens, passwords)
- [ ] `env.properties` changes do not expose secrets in the diff

### 2.5 Project conventions

- [ ] Page Objects follow the `public static By` field pattern
- [ ] Step definitions use `@Given`/`@When`/`@Then` with descriptive method names
- [ ] Tags follow the `@US_<EPIC>_TC_<NN>` pattern
- [ ] Feature files are organized by epic/story in the correct folder
- [ ] New dependencies in `pom.xml` are justified and use `test` scope
- [ ] Code compiles: `mvn -q test-compile -DskipTests`

## 3. Review process

### Step 1 — Get the diff
```
git diff <base>..HEAD
```
Or read the specific files if reviewing uncommitted changes.

### Step 2 — Classify the change
- **New feature test** — new scenarios, new Page Objects
- **Bug fix** — fixing a broken test
- **Refactoring** — restructuring without behavior change
- **Configuration** — dependency, environment, or pipeline changes

### Step 3 — Apply the checklist
Go through each section of the checklist. Note issues found.

### Step 4 — Severity classification

| Severity | Meaning | Action |
|---|---|---|
| **BLOCKER** | Code will not compile, will crash at runtime, or has a security issue | Must fix before merge |
| **MAJOR** | Code works but will break soon (fragile locator, missing wait, hardcoded value) | Should fix before merge |
| **MINOR** | Style, naming, or convention issue | Fix when convenient |
| **SUGGESTION** | Could be improved but current approach is acceptable | Optional |

## 4. Output format

```
## Code Review: <PR title / branch name>

**Type:** <New feature / Bug fix / Refactoring / Config>
**Files changed:** N
**Verdict:** ✅ APPROVE / ⚠️ APPROVE WITH COMMENTS / ❌ CHANGES REQUESTED

### Blockers (must fix)
1. `<file>:<line>` — <issue> → <fix>

### Major (should fix)
1. `<file>:<line>` — <issue> → <fix>

### Minor (nice to fix)
1. `<file>:<line>` — <issue> → <suggestion>

### Suggestions (optional)
1. `<file>:<line>` — <observation>

### Positive notes
- <what was done well>

### Summary
<2-3 sentence overall assessment>
```

## 5. Rules

- Be specific: always reference the exact file and line number.
- Be constructive: explain WHY something is an issue, not just WHAT is wrong.
- Provide the fix: for BLOCKER and MAJOR issues, show the corrected code.
- Do not nitpick: if the code works and follows conventions, approve it.
- Do not review code you did not write with different standards — apply the project's existing conventions.
- If the change is large (> 200 lines), suggest breaking it into smaller changes.
- Never approve code that has a BLOCKER issue.
- If you are uncertain whether something is an issue, mark it as a SUGGESTION, not a BLOCKER.
