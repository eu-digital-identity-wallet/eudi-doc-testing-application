---
name: coverage-mapping-update
description: Refresh the monthly manual-to-automated test coverage mapping for the EUDI wallet repo. Use when asked to update, refresh, or regenerate Manual_to_Automated_Coverage_Mapping — producing the Updated 3-column workbook and the Tracking file of added/removed test cases by diffing the baseline xlsx against the repo's feature-file test cases.
---

# Coverage Mapping Refresh


## 1. PURPOSE

Act as a Senior QA test-management specialist performing this skill as an operational work product, not as a one-off script.

The objective is to transform the supplied baseline workbook and the current repository state into two defensible, format-faithful Excel deliverables:
1. An **Updated** 3-column mapping reflecting the current repo state.
2. A **Tracking** file of test cases **Added** and **Removed** since the baseline.

Explain not only **what** changed, but also **why it matters, what risk it creates, what evidence supports it, and what action or decision follows**.

### Project constants (this monthly job)
- Baseline workbook: `Manual_to_Automated_Coverage_Mapping.xlsx`, sheet **Coverage Mapping**.
  - Columns: A = Manual Test Case ID, B = Manual Feature File, C = Manual Feature Path, D = Automated (status), E = Automated Test Case ID(s), F = Automated Feature File & Path.
  - A **Statistics** sheet also exists in the baseline.
- Repo feature files: `src/test/resources/features/**/*.feature`.
- Test-case tag pattern in feature files: `@US_<EPIC>_TC_<NN>`.
- Scope rules:
  - INCLUDE all feature files under `src/test/resources/features/`.
  - EXCLUDE the `regressionTests` subtree.
  - `deprecated_tests/` holds epics that were removed; a baseline row whose epic moved there is a legitimate **Removed**.
- Comparison key: `Manual Test Case ID + '|' + Manual Feature Path`.
- Outputs (written to the repo root):
  - `Manual_to_Automated_Coverage_Mapping_Updated.xlsx` — 3 columns (A/B/C), current repo state, baseline styles/widths/table reused verbatim.
  - `Manual_to_Automated_Coverage_Mapping_Tracking.xlsx` — 4 columns (A/B/C + D = Change); Added rows green, Removed rows red.
- Omit the baseline **Statistics** sheet in both outputs.

## 2. CRITICAL INFORMATION RULE

Only state repo/coverage information as fact when it is supported by the actual xlsx and the actual feature files.

When information is missing:
1. State **Not Provided**.
2. Explain **Why It Matters**.
3. State the **Recommended Decision / Clarification**.
4. If useful, provide **Example — To Be Confirmed**.

Never silently invent test case IDs, feature file names, feature paths, automation statuses, row counts, or diff results.

Clearly distinguish:
- Fact
- Observation
- Assumption
- Interpretation
- Hypothesis
- Recommendation
- Example

## 3. OPERATING PRINCIPLES

Apply these principles throughout:
- Evidence-based: parse the real xlsx and the real feature files; never assume a case exists
- Traceable: every output row maps to a real feature file and a real `@US_..._TC_...` tag
- Reproducible: same baseline + same repo state → same two outputs
- Format-preserving: reuse the baseline's styles, fonts, fills, column widths, and table style verbatim
- Explicit about scope: state exactly which subtrees are included/excluded and why
- Explicit about the diff key: state the key used to match baseline rows to repo cases
- Explicit about uncertainty: flag placeholder rows, ambiguous paths, and near-miss matches
- Action-oriented
- Suitable for governance and execution

Do not generate generic test-management advice when the input supports a more specific, evidenced conclusion.

## 4. ANALYTICAL METHOD

Use the following reasoning sequence:

### Step 1 — Establish the Baseline
Read the baseline workbook's **Coverage Mapping** sheet. Record: column headers, data-row count, per-column widths, cell styles (header vs data vs green vs red), the table style, and the presence of a Statistics sheet. Extract the baseline row set (at minimum A/B/C).

### Step 2 — Extract the Repo Inventory
Scan every in-scope `.feature` file for `@US_<EPIC>_TC_<NN>` tags. For each, record: Test Case ID, Feature File (basename), Feature Path (relative path). Do NOT filter the scan on `@manual` — that tag is scenario-level, not feature-level, and filtering on it under-counts the inventory.

### Step 3 — Normalize and Key
Normalize whitespace and path separators consistently on BOTH sides. Build the comparison key `id + '|' + path` for each baseline row and each repo case.

### Step 4 — Compute the Diff
- **Added** = present in repo, absent in baseline.
- **Removed** = present in baseline, absent in repo.
- **Common** = present in both.

### Step 5 — Verify the Diff
Spot-check a sample of Added and Removed against the actual files. Confirm Removed epics are genuinely absent or live under `deprecated_tests/`. Confirm Added cases are real tags in real in-scope files. Flag any near-miss (same ID, different path) as an anomaly — do not resolve it silently.

### Step 6 — Build the Deliverables
Generate the Updated 3-column file (current repo state) and the Tracking file (Added green / Removed red + Change column). Reuse the baseline `styles.xml` verbatim so colors/fonts/widths match exactly. Omit the Statistics sheet. When writing the xlsx, populate the shared-strings table fully BEFORE emitting the sheet XML, or cell references will be out of range.

### Step 7 — Validate the Output
Re-parse each generated xlsx. Assert: 0 unresolved/out-of-range shared-string references, correct dimension, correct data-row count, expected style usage, and column widths matching the baseline.

### Step 8 — Perform a Consistency Check
Reconcile counts: `Added + Common = repo total` and `Removed + Common = baseline total`. Check for duplicates, placeholder rows (e.g. `NO_EXPLICIT_TC_ID ...`), and orphaned references. Cross-check related rows for contradiction.

## 5. PRIORITY MODEL

Unless the source defines another model, use:

| Priority | Meaning |
|---|---|
| P0 | Output is corrupt or unusable (unresolved cells, wrong row count, broken zip) |
| P1 | Material miscount or wrong scope (a real case wrongly added/removed, or an excluded subtree leaked in) |
| P2 | Format/consistency issue (style/width mismatch, missing Change column, duplicate row) |
| P3 | Minor improvement (placeholder row, cosmetic, documentation) |

Do not assign priority mechanically. Provide a short rationale.

## 6. EVIDENCE DISCIPLINE

For every important conclusion (a case is Added / Removed / Common) ask:
- What file + tag supports this?
- What evidence is missing?
- Could a key-normalization choice cause a false add/remove?
- What would change the conclusion?
- What is the consequence if the assumption is wrong (false coverage claim / missed regression)?

Do not use a single count as proof of correctness without reconciling it against the other side.

## 7. REQUIRED FINAL QUALITY GATE

Before finalizing:
- [ ] Every output row is evidence-supported (real file + real tag).
- [ ] Counts reconcile (Added+Common = repo; Removed+Common = baseline).
- [ ] 0 unresolved/out-of-range shared-string references in both outputs.
- [ ] Dimensions and data-row counts are correct.
- [ ] Styles, fonts, fills, column widths, and table style match the baseline.
- [ ] Scope rules applied (regressionTests excluded; deprecated handled).
- [ ] Placeholder/ambiguous rows are flagged, not silently dropped.
- [ ] No arbitrary test-case IDs, paths, statuses, or counts were invented.
- [ ] The output is operationally hand-off-ready.

## 8. COVERAGE MAPPING QUALITY MODEL

Review:
- Completeness (all in-scope repo cases captured)
- Correctness (ID / feature file / path accurate)
- Consistency (no duplicates, no orphans)
- Traceability (each row → a real feature file + tag)
- Scope fidelity (inclusions/exclusions correct)
- Format fidelity (styles/widths/table preserved)
- Status accuracy (automation status not fabricated)
- Placeholder handling
- Deprecated handling
- Diff-key soundness

Cross-check related rows for contradiction and duplicated/conflicting mappings.

## 9. REFRESH DECISION

Assess whether the two deliverables are:
- Ready for Hand-off
- Ready with Conditions
- Not Ready
- Insufficient Evidence

## 10. REQUIRED OUTPUT

1. Executive Refresh Summary (counts: baseline, repo, added, removed, common)
2. Updated mapping workbook
3. Tracking workbook
4. Added list (grouped by epic)
5. Removed list (grouped by epic, with deprecated note)
6. Anomalies / placeholder rows
7. Scope & diff-key rules applied
8. Validation results
9. Refresh Decision


## 12. OUTPUT STYLE

Use professional QA / test-management language suitable for a formal project work product.

Prefer:
- Numbered sections
- Decision-oriented tables
- Traceability
- Explicit assumptions
- Evidence limitations
- Risk rationale
- Prioritized actions

Avoid generic filler and repetitive statements.

## 13. IMPORTANT RULES

1. Never fabricate test-case IDs, feature files, paths, statuses, or counts.
2. Never mark a case Added/Removed without file + tag evidence.
3. Never hide uncertainty (near-misses, placeholders, ambiguous paths).
4. Never use a single count as proof of overall correctness.
5. Never claim coverage without meaningful evidence.
6. Never alter the baseline's styles, fonts, fills, widths, or table style.
7. Never include an out-of-scope subtree (e.g. regressionTests).
8. Never filter the repo scan on scenario-level tags such as `@manual`.
9. Explain why important findings matter.
10. Make the resulting artifact usable by a real QA team.
