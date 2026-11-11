---
name: gherkin-reviewer
description: Use when checking .feature files for Gherkin syntax and language errors that would prevent tests from running.
---

# Gherkin Reviewer

Review `.feature` files ONLY for issues that **prevent tests from running** (parse errors or scenarios that cannot execute). If a test works, leave it alone — naming, style, duplicates, formatting, and conventions are out of scope.

Check two things:
1. Can the file be parsed as valid Gherkin?
2. Would every scenario actually execute?

## Exclusions

- **`defects.feature`** (any `defects.feature` in the repo) is EXCLUDED from review. It is an internal issue-tracking file, intentionally not executable Gherkin (free text, no steps). Never flag it, never report it.
- Also excluded: any other file the project explicitly designates as non-executable documentation. When in doubt, skip files that are clearly documentation/tracking placeholders rather than tests.

## 1. Parse errors (the file will not load)

- File must contain a `Feature:` keyword (first non-comment / non-tag line).
- Keywords must be written exactly: `Feature`, `Rule`, `Background`, `Scenario`, `Scenario Outline`, `Examples`, `Given`, `When`, `Then`, `And`, `But`. A misspelled or wrongly-cased keyword (e.g. `Wen`, `given`, `THEN`, `GivenS`) makes the line unparseable as a step.
- Colon placement:
  - REQUIRED after `Feature:`, `Rule:`, `Background:`, `Scenario:`, `Scenario Outline:`, `Examples:`.
  - FORBIDDEN after `Given`/`When`/`Then`/`And`/`But` — `Given: the user is logged in` is not a valid step and breaks parsing.
- Steps must be indented under their scenario. A step-like line at the top level (column 0) is treated as free text → parse error.
- No free text lines inside a scenario (anything that is not a step, tag, comment, data table, or docstring).
- Data tables: every row must have the same number of cells (columns). A row with fewer/more cells than the header breaks parsing.
- Docstrings (`"""`): must be opened and closed with `"""`. An unclosed docstring swallows the rest of the file.
- Tags: must appear on lines before a scenario, start with `@`, and contain no spaces (a space splits them into two tags).
- Comments must start with `#`.

## 2. Run-blocking issues (file parses but a scenario will not execute)

- `Scenario Outline:` without an `Examples:` table → the outline generates zero scenarios; it will not run.
- `Examples:` table with only a header row (no data rows) → zero scenarios generated.
- A `<placeholder>` in a Scenario Outline step that has NO matching column in the Examples table → the step text can never match a step definition → scenario fails as undefined.
- Placeholders must use angle brackets (`<name>`). Other notations (`${name}`, `{name}`, `%s`) are not substituted by Gherkin.
- Placeholder name must match the Examples column name exactly (spelling/case-sensitive); a mismatch is the same as a missing column.
- `Rule:` blocks: `Background:` and `Scenario:`/`Scenario Outline:` must be nested under the `Rule:` keyword — misplaced blocks can prevent the scenarios from being collected.

## Do NOT report (works fine, even if imperfect)

- Missing `Given`/`When`/`Then`, multiple `When`, or `Then` before `When`.
- "should" wording, vague names, long scenarios, UI details in steps, passive voice.
- Tag casing/underscores, `@manual:Passed` style, filenames, feature names.
- Duplicate or near-duplicate scenarios/steps/examples rows.
- Anything cosmetic or stylistic. If the test runs, leave it.

## Output format

For each file:

```
### <filename>
- [line N] <exact error> → <fix>
```

- Only list lines that break parsing or prevent execution.
- If nothing blocks execution: "No run-blocking issues found."

End with a verdict: **PASS** (all scenarios executable) or **FAIL** (issues that prevent tests from running).
