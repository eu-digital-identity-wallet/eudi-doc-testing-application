---
name: feature-file-generator
description: >
  Use when creating automated Gherkin .feature files for the EUDI Wallet test automation project
  from a GitHub epic/story URL, user story, acceptance criteria, or manual test case / manual
  feature file. Generates the Android and iOS automated feature files in the Automated_Regression_Tests
  folders (same filename, step words adapted to the automated vocabulary) and updates
  src/test/java/eu/europa/eudi/stepdefs/AutomatedStepDefs.java with any new step definitions needed.
version: 1.0.0
tags: [gherkin, cucumber, feature-files, bdd, mobile-testing, eudi-wallet, android, ios]
---

# Skill: Feature File Generator

> **Purpose:** Turn **manual test cases / user stories / acceptance criteria** into **executable automated
> Gherkin feature files** for the EUDI Wallet test automation project, for **both Android and iOS**, and
> keep the shared **`AutomatedStepDefs.java`** step-definition file in sync.

> **When to use:** Every time the user asks to
> *"create a feature file"*, *"generate the feature file for this story/issue"*, *"convert this manual
> test to automated"*, *"automate this user story"*, or provides a GitHub issue URL or manual `.feature`
> file and wants the automated version.

---

## 1. Input sources (pick whichever the user provides)

| Source | How to read it |
|---|---|
| **GitHub epic/story URL** | Fetch the issue (e.g. `gh issue view 243 --repo eu-digital-identity-wallet/eudi-doc-testing-application` or `webfetch`). Extract: title, description, acceptance criteria, and any linked manual test cases / related issues. |
| **Manual feature file** | Read the file from the repo, e.g. `src\test\resources\features\android\regressionTests\Manual_Regression_Tests\*.feature` or an epic folder like `src\test\resources\features\android\Pre_authorised_code_flow_OID4VCI_41\`. |
| **User story / acceptance criteria pasted in chat** | Use the text directly as the source of scenarios. |

Extract the story metadata you need:
- **Title** of the story/feature
- **Role / goal / benefit** (the `As a / I want / So that` lines)
- **GitHub issue number** (from the URL or the `#...` comment in the manual file)
- **Story tag abbreviation** (`US_<ABBR>`, e.g. `US_PACSD`)
- **Test case tags** (`US_<ABBR>_TC_01`, ...) and any quarter/label tags

---

## 2. Output files (MANDATORY locations)

| Platform | Path |
|---|---|
| **Android** | `src\test\resources\features\android\regressionTests\Automated_Regression_Tests\<FeatureFileName>.feature` |
| **iOS** | `src\test\resources\features\ios\regressionTests\Automated_Regression_Tests\<FeatureFileName>.feature` |

Rules:
- **Same filename on both platforms**, camelCase derived from the feature/story title
  (e.g. `preAuthorizationCodeSameDevice.feature`, `documentsScreen.feature`).
- **If a file with that name already exists** for a platform, **update/extend it** — never create a duplicate.
- The **step wording is identical** in the Android and iOS files; **only the platform tag differs**
  (`@ANDROID` vs `@IOS`).

---

## 3. Feature file anatomy (template)

```gherkin
@ANDROID @automated @US_PACSD @GENERAL_TESTS
Feature: Pre-authorization code same device
  As a user of the issuer service
  I want to be able to issue a credential using a transaction code
  So that it can be securely added to my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/243

  @US_PACSD_TC_01
  Scenario: User initiates credential issuance
    Given the user is accessing the issuer service
    When the user selects to issue a document using pre-authorization
    Then the user provides their personal information
    And a transaction code is generated
    And the document appears on the documents screen
```

### Tag rules
- **Platform tag first**: `@ANDROID` or `@IOS`.
- `@automated` **replaces** `@manual` (from the manual file).
- Story tag `@US_<ABBR>` is kept from the manual file / issue.
- Quarter tag (e.g. `@Q4_2024`) is kept when present in the manual file or issue labels; omit if unknown.
- `@GENERAL_TESTS` last on the feature tag line.
- Scenario tags: **strip `@manual:Passed` and any manual-only tags**; keep only the FIRST test-case tag
  (`@US_<ABBR>_TC_01`) on the single merged scenario — drop all other `@US_..._TC_0N` tags.
- Keep the `#https://github.com/.../issues/<number>` comment line exactly as-is.
- **Do not** add `@ANDROID`/`@IOS` on scenario lines (the platform tag belongs to the feature line).

---

## 4. Manual → automated transformation

### 4.1 Structure — ONE scenario with ALL steps
- Keep the Feature title and the `As a / I want / So that` block.
- **Merge ALL manual scenarios/steps into ONE single automated `Scenario`.** The automated feature file
  must contain exactly **one scenario** that holds **every step from every manual test case**, in flow
  order — the complete flow as one test case. Do NOT split steps across multiple scenarios.
- The merged scenario keeps only the first test-case tag (e.g. `@US_PACSD_TC_01`); all later
  `@US_..._TC_0N` tags are dropped (the feature-level tags carry the story).
- Name the merged scenario after the **whole flow** (e.g. "User initiates credential issuance"),
  not after the first manual scenario.
- `Background:` steps stay in the Background and are **NOT** copied into the scenario.
- Manual steps that are only flow continuations of the previous scenario (e.g. `the user has entered
  the PIN` after `the user enters the PIN`) collapse into the single step; every other step is kept.
- When a step varies by data (credential, issuer, issuance method, scenario), use a
  **Scenario Outline** with `<placeholder>`s and an `Examples:` table (still exactly one Scenario Outline).

### 4.2 Step rewording — REUSE FIRST, reword only when needed

> **GOLDEN RULE — REUSE FIRST:** before rewording or creating anything, **grep the exact step text across
> the whole stepdefs codebase** (`AutomatedStepDefs.java` AND `ManuallyStepDefs.java`, plus any other
> stepdef class in `src\test\java\eu\europa\eudi\stepdefs\`).
> - **If the exact step text already exists as a step definition** (used by any other feature file) →
>   **use that exact same step text in the new feature file** and **do NOT create a new stepdef**.
> - Only reword / create a new stepdef for steps that do **not** exist yet anywhere.
> - **Never create a duplicate stepdef** for a step that already exists — Cucumber fails with an
>   ambiguous-step error when two step definitions match the same text.

The step words change slightly from the manual wording (only when the step does NOT already exist)
so the automated feature files resolve to `AutomatedStepDefs.java` (the manual wording resolves to
`ManuallyStepDefs.java`):

| Manual wording (source) | Automated wording (target) |
|---|---|
| `the user is on issuer service` | `the user is accessing the issuer service` |
| `the user chooses to issue a doc with pre-authorization` | `the user selects to issue a document using pre-authorization` |
| `the user registers their personal data` | `the user provides their personal information` |
| `a transaction code has been created` | `a transaction code is generated` |
| `the user selects to register with the EUDI wallet app` | `the user chooses to register through the EUDI wallet app` |
| `the user is redirected to the EUDI wallet application` | `the user is navigated to the EUDI wallet application` |
| `the user enters the PIN` | `the user provides the PIN` |
| `the request from the issuer is displayed on the wallet app` | `the issuer request is shown in the wallet app` |
| `the user clicks on the ISSUE button` | `the user selects ISSUE button` |
| `the Wallet app requests the transaction code` | `the Wallet app prompts for the transaction code` |
| `the user enters the transaction code provided by the Issuer` | `the user enters the transaction code received from the Issuer` |
| `the Wallet application displays a success message` | `the Wallet application shows a successful issuance message` |
| `the user clicks on the CONTINUE button` | `the user selects the CONTINUE button` |
| `the doc is displayed in the dashboard screen` | `the document appears on the documents screen` |

**Rule of thumb:** the automated vocabulary prefers
- present tense `the user <action> ...`
- `selects <...> button` for buttons (not `clicks on`/`clicks`)
- `screen` over `dashboard`
- `provides` / `is navigated to` / `chooses to register through` over `enters` / `is redirected to` / `selects to register with`

**Crucially:** the reworded step text is exactly what must exist in `AutomatedStepDefs.java` — that is
what "a little change on the step words in order to create new stepdefs" means. Grep before inventing
wording; **reuse existing stepdefs whenever possible** (see section 5).

---

## 5. Step definitions in `AutomatedStepDefs.java`

**File:** `src\test\java\eu\europa\eudi\stepdefs\AutomatedStepDefs.java` (single shared file for Android and iOS).

For **every step** written in the feature files:

1. **Grep the exact step text across the WHOLE stepdefs codebase** (both `AutomatedStepDefs.java` and
   `ManuallyStepDefs.java`, and any other stepdef class under `src\test\java\eu\europa\eudi\stepdefs\`).
   If the exact wording already has a `@Given`/`@When`/`@Then`/`@And` anywhere →
   **reuse it, add nothing, do NOT create a new stepdef** (this covers steps shared with other feature files).
2. If it **does not exist anywhere**, add a new step-definition method following the file's style:
   ```java
   @Given("the user is accessing the issuer service")
   public void theUserIsAccessingTheIssuerService() {
       test.mobile().issuer().openIssuerService();
   }
   ```
   - Annotation text must match the feature step **exactly** (Cucumber matches on full text).
   - Method name: camelCase derived from the step text.
   - Body: call the existing `test.mobile().wallet() / .issuer() / .verifier()` helper — reuse helpers, only add new stepdef glue.
   - Placeholders: a feature `<placeholder>` (Scenario Outline) maps to `{}` in the stepdef text, e.g.
     `@And("the document appears on the documents screen for {}")`.
   - Platform-specific behavior inside one stepdef branches on
     `test.getSystemOperation().equals(Literals.General.IOS.label)` / `Literals.General.ANDROID.label`.
   - **Never duplicate an existing annotation text** — two stepdefs with the same text make Cucumber fail with an ambiguous-step error.

---

## 6. Workflow (run in order)

1. **Gather input** — fetch the issue URL or read the manual feature file / pasted story.
2. **Extract metadata** — title, role/goal/benefit, issue number, `US_<ABBR>` tags, TC tags.
3. **Check for existing files** in both `Automated_Regression_Tests` folders — update if present.
4. **Build the Android file** (`@ANDROID`), reusing existing step texts and rewording only new steps per section 4.
5. **Build the iOS file** — identical content, `@IOS` tag.
6. **Sync step definitions** — grep every step text across all stepdef classes; **reuse existing stepdefs** and add only the genuinely missing ones (section 5).
7. **Verify** — run the checks below, then hand off to the `gherkin-reviewer` skill.

---

## 7. Example (real transformation)

Manual file `preAuthorizationCodeSameDevice.feature`:

```gherkin
@ANDROID @manual @US_PACSD
Feature: Pre-authorization code same device
  As a user of the issuer service
  I want to be able to issue a credential using a transaction code
  So that it can be securely added to my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/243

  @US_PACSD_TC_01 @manual:Passed
  Scenario: User selects to issue a credential
    Given the user is on issuer service
    When the user chooses to issue a doc with pre-authorization
    Then the user registers their personal data
    And a transaction code has been created

  @US_PACSD_TC_02 @manual:Passed
  Scenario: User proceeds with the doc issuance
    Given the transaction code has been created
    When the user selects to register with the EUDI wallet app
    Then the user is redirected to the EUDI wallet application
    And the user enters the PIN

  @US_PACSD_TC_03 @manual:Passed
  Scenario: Handling issuer request on wallet app
    Given the user has entered the PIN
    When the request from the issuer is displayed on the wallet app
    Then the user clicks on the ISSUE button

  @US_PACSD_TC_04 @manual:Passed
  Scenario: Transaction code entry
    Given the user has clicked on the ISSUE button
    When the Wallet app requests the transaction code
    Then the user enters the transaction code provided by the Issuer

  @US_PACSD_TC_05 @manual:Passed
  Scenario: Document display
    Given the user entered the transaction code provided by the Issuer
    When the Wallet application displays a success message
    Then the user clicks on the CONTINUE button
    And the doc is displayed in the dashboard screen
```

Generated automated file (Android; iOS is identical with `@IOS`):

```gherkin
@ANDROID @automated @US_PACSD @GENERAL_TESTS
Feature: Pre-authorization code same device
  As a user of the issuer service
  I want to be able to issue a credential using a transaction code
  So that it can be securely added to my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/243

  @US_PACSD_TC_01
  Scenario: User initiates credential issuance
    Given the user is accessing the issuer service
    When the user selects to issue a document using pre-authorization
    Then the user provides their personal information
    And a transaction code is generated
    And the user chooses to register through the EUDI wallet app
    And the user is navigated to the EUDI wallet application
    And the user provides the PIN
    When the issuer request is shown in the wallet app
    Then the user selects ISSUE button
    And the Wallet app prompts for the transaction code
    And the user enters the transaction code received from the Issuer
    And the Wallet application shows a successful issuance message
    And the user selects the CONTINUE button
    Then the document appears on the documents screen
```

What changed:
- Tags: `@manual` → `@automated`, `@GENERAL_TESTS` added, all `@manual:Passed` removed.
- **All five manual scenarios merged into ONE automated scenario** holding every step in flow order
  (the whole flow as one test case); only `@US_PACSD_TC_01` is kept.
- Every step reworded to the automated vocabulary (section 4.2).
- Step definitions: most steps already exist in `AutomatedStepDefs.java`; **any that don't must be added**
  there (e.g. `the user is accessing the issuer service`).

---

## 8. Verification checklist (before finishing)

- [ ] **Same filename** in `...\android\regressionTests\Automated_Regression_Tests\` and `...\ios\regressionTests\Automated_Regression_Tests\`.
- [ ] Platform tag correct (`@ANDROID` / `@IOS`), plus `@automated`, `@US_<ABBR>`, `@GENERAL_TESTS`.
- [ ] Issue-URL comment line preserved.
- [ ] No `@manual` / `@manual:Passed` tags left in the automated files.
- [ ] **Exactly ONE scenario per feature file**, holding ALL steps from all manual test cases in flow order.
- [ ] **Existing stepdefs reused** — no new stepdef created for a step whose exact text already exists in any stepdef class.
- [ ] **Every step text exists exactly once** in the stepdefs codebase (no duplicates, no undefined steps).
- [ ] Scenario Outline `<placeholder>`s match the `Examples:` column headers exactly.
- [ ] Compiles: `mvn -q test-compile -DskipTests`.
- [ ] Optionally run the `gherkin-reviewer` skill on the new files before declaring done.
