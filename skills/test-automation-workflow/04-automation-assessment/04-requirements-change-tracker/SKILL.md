---
name: requirements-change-tracker
description: Use when checking the EU Digital Identity Wallet GitHub project board (https://github.com/orgs/eu-digital-identity-wallet/projects/20) for changed/updated stories, epics, issues, or PRs, and determining which local .feature test files are affected. Triggers on "requirements changed", "which tests are affected", "track requirement changes", "check the project board", "what changed".
---

# Requirements Change Tracker

Track requirement changes on the EUDI Wallet GitHub project board and identify which local `.feature` test files are affected.

## 1. Fetch the project board state

Project: `https://github.com/orgs/eu-digital-identity-wallet/projects/20` (GitHub Projects v2).

Preferred: `gh` CLI (fastest, supports auth).
```
gh auth status
gh project view 20 --owner eu-digital-identity-wallet --format json
gh api graphql -f query='
  query {
    organization(login: "eu-digital-identity-wallet") {
      projectV2(number: 20) {
        title
        items(first: 100) {
          nodes {
            content { ... on Issue { number title state url } ... on PullRequest { number title state url } }
            fieldValueByName(name: "Status") { ... on ProjectV2ItemFieldSingleSelectValue { name } }
            updatedAt
            createdAt
          }
        }
      }
    }
  }'
```
If `gh` is unavailable/unauthenticated, fall back to `webfetch` on the public project page and issue URLs. If you cannot authenticate, report that the view is limited and proceed with whatever is reachable.

Extract for each item: number, title, type (issue/PR/epic), status field value, `createdAt`, `updatedAt`, `url`.

## 2. Determine what changed

- **Baseline snapshot**: maintain a snapshot of the board in `skills/requirements-change-tracker/state/last-snapshot.json` (write it on every successful fetch). On the first run (no snapshot) use "recent activity" (items updated in the last 14 days, or added since the local feature files were last touched — check `git log -1 --format=%ci` for the features tree).
- **Compare**: new items not in the snapshot, items whose `updatedAt` moved, items whose status field changed (e.g. moved to Done, In Progress, Blocked), items whose linked PR was merged.
- If a snapshot exists, diff it and report every delta. Update the snapshot at the end of the run.

## 3. Read the local feature files

- Root: `C:\Users\ftheofil\Projects\eu-digital-identity-walleteudi-doc-testing-application-internal\src\test\resources\features`
- Structure: one folder per epic/story, named `...<Title>_<IssueNumber>` (e.g. `Re_issue_attestation_..._225`), each containing one or more `.feature` files.
- Feature files reference requirements in three places:
  - Folder name: the trailing `_<number>` is the GitHub issue number.
  - Tags: `@US_<STORY_ID>_TC_<N>` and similar.
  - Comments: GitHub URLs (e.g. `#https://github.com/.../issues/351`).

## 4. Match changed requirements to affected tests

For each changed item (issue/PR/story/epic):
1. Extract its number (and any referenced story numbers).
2. Find matching feature files:
   - `glob` for `**/*_<number>/**/*.feature` (folder match).
   - `grep` the features tree for the number, the story tag (`@US_...`), or the issue URL.
3. For each match, read the feature file and the GitHub issue/PR to determine the nature of the change (new requirement, acceptance criteria changed, flow changed, deprecated).
4. A story/epic can affect multiple features (e.g. same story has android + ios variants). Report all of them.

## 5. Output

Report per changed requirement:

```
### <Issue/Story #number>: <title>
Status: <old status → new status>   (or: New / PR merged / updated)
URL: <github url>
Affected feature file(s):
- <relative path to .feature>
  → Impact: <what changed and which scenarios/steps are likely affected>
  → Action: <update existing scenarios / add new scenario / remove / verify only>
```

End with:
- **Summary table**: requirement | affected file(s) | impact | recommended action
- **Unaffected check note**: list stories on the board that have no matching local feature (new requirements with no tests yet) — these are candidates for NEW feature files.
- If nothing changed since the last snapshot: state that explicitly.

## Notes

- Android and iOS usually have separate feature files for the same story — check both.
- A story being closed/Done does not mean its tests are removed; flag it for verification.
- Never modify feature files — this skill only reports. The user decides what to change.
