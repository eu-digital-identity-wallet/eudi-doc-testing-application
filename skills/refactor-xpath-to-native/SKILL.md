---
name: refactor-xpath-to-native
description: >
  General-purpose refactoring skill for Appium/Selenium mobile test projects.
  Use whenever you need to replace brittle XPath locators with faster, more stable
  native Appium locators (Accessibility ID, Resource-ID, iOS Predicate/Class Chain,
  Android UiSelector). Works on ANY codebase - not tied to a specific app.
version: 1.0.0
tags: [appium, selenium, locators, xpath, refactoring, mobile-testing]
---

# Skill: Refactor XPath Locators → Native Appium Locators

> **Purpose:** A **general-purpose, reusable refactoring tool** to systematically replace brittle XPath
> locators with faster, more stable **native** Appium locators in any Appium/Selenium mobile test project.
>
> **When to use:** Every time you add new elements, fix flaky tests, or refactor locator files —
> regardless of the app under test.

---

## Locator Priority (ALWAYS follow this order)

| Priority | Strategy | Platform | When to use |
|----------|----------|----------|-------------|
| **1** | **Accessibility ID** (`AppiumBy.accessibilityId`) | Both | Element has a `content-desc` (Android) or `name`/`label` (iOS) |
| **2** | **Resource-ID** (`By.id`) | Android | Element has a stable `resource-id` |
| **3** | **iOS Predicate** (`AppiumBy.iOSNsPredicateString`) | iOS | Text-based, partial match, or multiple conditions |
| **4** | **iOS Class Chain** (`AppiumBy.iOSClassChain`) | iOS | Hierarchical / indexed lookups |
| **5** | **Android UiSelector** (`AppiumBy.androidUIAutomator`) | Android | Text-based or dynamic resource-id |
| **6** | **XPath** (`By.xpath`) | Both | ⚠️ **LAST RESORT** — only for complex parent/child/sibling relationships |

> **Rule of thumb:** If you write `By.xpath(...)`, stop and ask:
> *"Can I express this with an accessibility-id, resource-id, predicate, class-chain, or UiSelector first?"*

---

## CRITICAL iOS GOTCHA: `name` != `label`

> **This is the #1 cause of broken tests after converting iOS XPaths. Read this first!**

On iOS, **`name` (accessibility identifier) and `label` (visible text) are DIFFERENT attributes**:

```xml
<!-- Example: a button whose visible text is "Authenticate" -->
<XCUIElementTypeButton
    name="home_tab_screen_authorization_button"   <!-- accessibility identifier -->
    label="Authenticate"                           <!-- visible text -->
    ... />
```

### The Rule
- **If the original XPath used `@label="..."`** -> the predicate MUST use **`label ==`** (NOT `name ==`)
- **If the original XPath used `@name="..."`** -> the predicate uses **`name ==`**

### WRONG (breaks tests!)
```java
// Original: By.xpath("//XCUIElementTypeButton[@label=\"Authenticate\"]")
// Converted to name ==  -> FAILS because name is "home_tab_screen_authorization_button"
AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND name == 'Authenticate'")
```

### CORRECT
```java
// Original: By.xpath("//XCUIElementTypeButton[@label=\"Authenticate\"]")
// Converted to label ==  -> WORKS
AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND label == 'Authenticate'")
```

### How to check before converting
When you dump the page source, look at BOTH attributes:
```xml
<XCUIElementTypeButton name="home_tab_screen_authorization_button" label="Authenticate" .../>
```
- `name` = the developer-assigned accessibility identifier (stable, but not human-readable)
- `label` = what the user sees (matches `@label` in XPath)

### Quick mapping table
| Original XPath attribute | Predicate keyword |
|---|---|
| `@name="X"` | `name == 'X'` |
| `@label="X"` | `label == 'X'` |
| `contains(@name, "X")` | `name CONTAINS[c] 'X'` |
| `contains(@label, "X")` | `label CONTAINS[c] 'X'` |
| `starts-with(@name, "X")` | `name BEGINSWITH 'X'` |
| `starts-with(@label, "X")` | `label BEGINSWITH 'X'` |

> **Tip:** When in doubt, use BOTH in an OR:
> `AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND (name == 'Authenticate' OR label == 'Authenticate')")`
> This is the most robust option but slightly slower.

---

## The Refactoring Workflow (run every time)

### Step 1 — Find all XPaths in your codebase
```bash
# Any project layout — point this at your locator/source folder
grep -rn "By\.xpath(" src/test/java
# or the whole repo:
grep -rn "By\.xpath(" .
```

### Step 2 — Inspect each element's real attributes
Run the app (BrowserStack / local device / emulator) and dump the page source:

```java
// In any test / step:
System.out.println(driver.getPageSource());
```

Or use **Appium Inspector** / `uiautomatorviewer` (Android) / **Xcode Accessibility Inspector** (iOS).

Look at what the element actually exposes — `resource-id`, `content-desc`, `name`, `label`, `text`, `value`.

### Step 3 — Apply the conversion (see cheat-sheets below)
For each XPath, pick the **highest-priority** strategy that the element supports.

### Step 4 — Verify & compile
```bash
# Maven
mvn -q test-compile -DskipTests
# Gradle
./gradlew compileTestJava
# Plain javac (if not using a build tool)
javac -cp "appium-java-client.jar;selenium-java.jar" <your files>
```

### Step 5 — Track progress
```bash
# Count XPath vs native per file (adjust path to your project)
for f in $(find src/test -name "*Elements*.java"); do
  total=$(grep -cE "public static.*By [a-zA-Z]" "$f")
  xpath=$(grep -cE "By\.xpath\(" "$f")
  echo "$(basename $f): total=$total | xpath=$xpath"
done
```

---

## Conversion Cheat-Sheet

### ANDROID

| Current XPath | Better Native Locator |
|---|---|
| `//android.widget.TextView[@text="Welcome"]` | `AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").text(\"Welcome\")")` |
| `//android.widget.TextView[contains(@text, "Welcome")]` | `AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").textContains(\"Welcome\")")` |
| `//android.widget.TextView[starts-with(@text, "Expires")]` | `AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").textStartsWith(\"Expires\")")` |
| `//android.widget.Button[@text="Submit"]` | `AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").text(\"Submit\")")` |
| `//android.view.View[@content-desc="Add"]` | `AppiumBy.accessibilityId("Add")` |
| `//android.view.View[@resource-id="com.pkg:id/my_id"]` | `By.id("com.pkg:id/my_id")` |
| `//android.view.View[@resource-id=".../dynamic_part"]` | `AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*dynamic_part.*\")")` |
| `//android.widget.TextView[@text="A" or @text="B"]` | `AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").textMatches(\"(?i).*A.*|.*B.*\")")` |
| `//android.widget.TextView[contains(@text, "PID")]` | `AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").textContains(\"PID\")")` |
| `(//android.widget.CheckBox)[8]` | `AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.CheckBox\").index(8)")` |

### iOS

| Current XPath | Better Native Locator |
|---|---|
| `//XCUIElementTypeStaticText[@name="Welcome"]` | `AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND name == 'Welcome'")` |
| `//XCUIElementTypeStaticText[@label="Welcome"]` | `AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND label == 'Welcome'")` |
| `//XCUIElementTypeStaticText[contains(@name, "PID")]` | `AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND name CONTAINS[c] 'PID'")` |
| `//XCUIElementTypeStaticText[starts-with(@name, "Expires")]` | `AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND name BEGINSWITH 'Expires'")` |
| `//XCUIElementTypeStaticText[ends-with(@name, "Mdoc")]` | `AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND name ENDSWITH 'Mdoc'")` |
| `//XCUIElementTypeButton[@name="Submit"]` | `AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND name == 'Submit'")` |
| `//XCUIElementTypeButton[@label="Back"]` | `AppiumBy.accessibilityId("back_button")` *(if stable name exists)* |
| `//XCUIElementTypeStaticText[@name="A" or @name="B"]` | `AppiumBy.iOSNsPredicateString("(type == 'XCUIElementTypeStaticText' AND name == 'A') OR (type == 'XCUIElementTypeStaticText' AND name == 'B')")` |
| `(//XCUIElementTypeButton[@name="X"])[2]` | `AppiumBy.iOSClassChain("(**/XCUIElementTypeButton[\`name == 'X'\`])[2]")` |
| `//XCUIElementTypeButton[@name="Continue"]` | `AppiumBy.iOSClassChain("**/XCUIElementTypeButton[\`name == 'Continue'\`]")` |

---

## Decision Flow (pick the right strategy)

```
Start with an element to locate
        │
        ▼
Does it have a stable content-desc / accessibility name?
   ┌──── YES ────► AppiumBy.accessibilityId("...")   ✅ DONE
   │
   ▼ NO
Does it have a stable resource-id (Android)?
   ┌──── YES ────► By.id("package:id/...")           ✅ DONE
   │
   ▼ NO
Is it iOS and text-based?
   ┌──── YES ────► AppiumBy.iOSNsPredicateString(...) ✅ DONE
   │
   ▼ NO
Is it iOS and needs hierarchy/index?
   ┌──── YES ────► AppiumBy.iOSClassChain(...)        ✅ DONE
   │
   ▼ NO
Is it Android and text-based / dynamic id?
   ┌──── YES ────► AppiumBy.androidUIAutomator(...)   ✅ DONE
   │
   ▼ NO
   ────────────► By.xpath(...)  ⚠️ last resort
```

---

## Android UiSelector Reference (full API)

```java
// By text (exact)
new UiSelector().text("Welcome")

// By text (partial / contains)
new UiSelector().textContains("Welcome")

// By text (regex / starts-with)
new UiSelector().textStartsWith("Expires")
new UiSelector().textMatches("(?i).*pid.*")

// By class + text
new UiSelector().className("android.widget.TextView").text("Submit")

// By resource-id (exact)
new UiSelector().resourceId("username")

// By resource-id (regex / partial - GREAT for dynamic ids)
new UiSelector().resourceIdMatches(".*my_dynamic_suffix.*")

// By content-desc (accessibility)
new UiSelector().description("Arrow down")
new UiSelector().descriptionContains("Close")

// By index / position
new UiSelector().className("android.widget.CheckBox").index(8)

// By Nth match (XPath [2] -> instance(1), 0-indexed!)
// XPath: (//android.view.View[@content-desc="Arrow right"])[2]
new UiSelector().description("Arrow right").instance(1)   // 2nd match

// Index mapping: [1]->instance(0), [2]->instance(1), [3]->instance(2), [N]->instance(N-1)

// By parent-child / sibling
new UiSelector().text("PID").fromParent(new UiSelector().className("android.widget.CheckBox"))
new UiSelector().className("android.widget.TextView").text("X").fromParent(
    new UiSelector().className("android.widget.CheckBox"))

// Chaining (AND logic)
new UiSelector().className("android.widget.Button").text("Submit").enabled(true)

// Scroll into view
new UiSelector().text("Welcome").scrollable(true)
```

**Full usage in a locator field:**
```java
public static By myElement = AppiumBy.androidUIAutomator(
    "new UiSelector().className(\"android.widget.TextView\").text(\"Welcome\")");
```

---

## iOS Predicate Reference (full API)

```java
// Exact type + name
AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND name == 'Welcome'")

// Case-insensitive contains
AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND name CONTAINS[c] 'PID'")

// Starts-with / ends-with
AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND name BEGINSWITH 'Expires'")
AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND name ENDSWITH 'Mdoc'")

// Multiple OR conditions
AppiumBy.iOSNsPredicateString(
    "(type == 'XCUIElementTypeStaticText' AND name == 'A') OR " +
    "(type == 'XCUIElementTypeStaticText' AND name == 'B')")

// Label-based
AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND label == 'Submit'")

// Value-based
AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeSwitch' AND value == '1'")

// State filters
AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND enabled == true")
AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND visible == true")

// NOT / negation
AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND NOT name CONTAINS 'Cancel'")

// Combined
AppiumBy.iOSNsPredicateString(
    "type == 'XCUIElementTypeStaticText' AND name CONTAINS[c] 'PID' AND visible == true")
```

### iOS Class Chain Reference

```java
// Simple
AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`name == 'Continue'`]")

// Indexed (2nd match) - XPath (//...)[2]
AppiumBy.iOSClassChain("(**/XCUIElementTypeButton[`name == 'X'`])[2]")

// With predicate inside
AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name CONTAINS 'PID'`]")

// Descendant from a known parent
AppiumBy.iOSClassChain("**/XCUIElementTypeCell[`name == 'sample'`]/XCUIElementTypeOther[1]")

// Direct child (single slash)
AppiumBy.iOSClassChain("XCUIElementTypeWindow/XCUIElementTypeOther[1]")
```

> **Class Chain gotcha:** inside `[...]` use **backticks** `` ` `` around the predicate,
> not single quotes.

---

## What to KEEP as XPath (acceptable exceptions)

Some XPaths are genuinely hard to replace. Keep them but **comment why**:

1. **Sibling / preceding-sibling relationships** (e.g. checkbox next to a label):
   ```java
   // No native alternative for "preceding-sibling" - keep XPath
   By.xpath("//android.widget.TextView[@text='Label']/preceding-sibling::android.widget.CheckBox")
   ```

2. **Web elements inside the app** (webviews / hybrid apps / websites):
   ```java
   // Web app (e.g. Angular/React) - CSS/XPath is appropriate here
   By.cssSelector("#username")
   By.xpath("//mat-option[normalize-space(.)='mso_mdoc']")
   ```

3. **Deep static hierarchies with no identifiers** (rare, only when nothing else works):
   ```java
   By.xpath("//android.view.ViewGroup/android.view.View[2]/...")
   ```

---

## WEB APP vs NATIVE APP: Know the difference BEFORE converting

> **This is a big one. Converting web-app locators to native predicates will break your tests.**

### How to tell if a screen is a WEB app or NATIVE app
- **Native app** (e.g. the Wallet): elements have `resource-id`, `content-desc`, `android.widget.*` (Android) or `XCUIElementType*` with stable `name`/`label` (iOS). Driven by Appium's native driver.
- **Web app** (e.g. an issuer/verifier website opened in a browser/webview): elements are HTML (`input`, `button`, `mat-option`, `div`, `h4`). On iOS they may *appear* as `XCUIElementType*` via the accessibility bridge, but they are still **web elements** and should keep **CSS/XPath** locators.

### The rule
| File / screen type | Use |
|---|---|
| **Native app screens** (Wallet) | Native locators (accessibilityId / By.id / predicate / UiSelector) |
| **Web app screens** (Issuer, Verifier, login pages, webviews) | **KEEP CSS / XPath** — do NOT convert |

### How to spot a web-app locator file
Look for these signals:
- Contains `By.cssSelector(...)`
- Contains web-specific XPath: `//mat-`, `//h4[`, `//input`, `//button[contains(@class,`, `//mat-option`, `normalize-space(`
- References a URL-based flow (issuer.eudiw.dev, verifier.eudiw.dev, login, sign in)

### What happened when I got this wrong (real example)
I converted the **iOS Issuer** (a web app) locator:
```java
// ORIGINAL (web element - worked)
By.xpath("//XCUIElementTypeOther[@name=\"Request Credentials for your EUDI Wallet\" and @value=\"1\"]")
```
to:
```java
// CONVERTED to native predicate - FAILED (timeout after 110s)
AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeOther' AND name == 'Request Credentials for your EUDI Wallet'")
```
The `@value="1"` filter was dropped, and the web element exposed via the accessibility bridge did not respond to native predicates. **The fix: revert web-app files to their original CSS/XPath locators.**

### Actionable checklist
- [ ] Before converting a file, ask: **"Is this screen native or web?"**
- [ ] If the file has ANY `cssSelector` or web XPath → it's a **web-app file** → **SKIP it** (keep XPath/CSS)
- [ ] Only convert files that are **purely native** (like the Wallet app)

---

## Verification Checklist (before committing)

- [ ] **Compiles** — `mvn -q test-compile -DskipTests` (or your build tool) → no errors
- [ ] **Field/method names unchanged** — callers still reference the same names
- [ ] **Imports correct:**
  ```java
  import io.appium.java_client.AppiumBy;   // accessibilityId / androidUIAutomator / iOSNsPredicateString / iOSClassChain
  import org.openqa.selenium.By;           // By.id / By.xpath / By.cssSelector
  ```
- [ ] **No `By.accessibilityId`** — it's `AppiumBy.accessibilityId` (Selenium's `By` has no such method)
- [ ] **Live-verified on a real device / BrowserStack** (recommended for new/changed locators)

---

## Target

> **Goal:** Get XPath usage in your **locator files** below **10%**.
> The remaining XPaths should be ONLY web elements and complex sibling relationships.
> Every new locator you add should be native-first.

---

## Bonus: Quick "lint" one-liner to flag new XPaths in CI

```bash
# Fail if any new By.xpath( appears in native locator files
if grep -rn "By\.xpath(" src/test/java --include="*Elements*.java"; then
  echo "Found XPath locators - convert to native (see skill guide)"
  exit 1
fi
```
