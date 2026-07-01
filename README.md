# Functional E2E Tests for EUDI Wallet Application

This repository contains the test procedures for the EUDI Wallet project, encompassing both manual and automated functional (UI) tests based on the agreed specifications for each Release.

## Table of Contents

- [Overview](#overview)
- [Requirements](#requirements)
- [Test Case Overview](#test-case-overview)
- [Local Environment Setup For Real Device/Emulator](#local-environment-setup-for-real-deviceemulator)
- [Running Tests Locally with Real Device/Emulator](#running-tests-locally-with-real-deviceemulator)
- [Running Tests Locally with Real Device through BrowserStack Device Farm](#running-tests-locally-with-real-device-through-browserstack-device-farm)
- [Running Tests via GitHub Actions & BrowserStack Device Farm](#running-tests-via-github-actions--browserstack-device-farm)
- [Test Environment Scope](#test-environment-scope)
- [Execution Notes & Known Limitations](#execution-notes--known-limitations)
- [Troubleshooting](#troubleshooting)
- [Automation & App Version Compatibility](#automation--app-version-compatibility)
- [Contributing](#contributing)
- [License](#license)

## Overview

The test suite is designed to validate the EUDI Wallet's functionality. Depending on your needs, you can execute these tests locally on physical devices/emulators or through BrowserStack Device Farm. Tests can also be triggered through GitHub Actions integrated with BrowserStack for scalable device testing.

## Technical Stack
- **Language:** Java 17
- **Build Tool:** Maven
- **Core Framework:** [Serenity BDD](https://serenity-bdd.github.io/)
- **BDD Engine:** Cucumber
- **Web Automation:** [Selenium 4.40.0](https://www.selenium.dev/)
- **Mobile Automation:** [Appium 10.0.0](https://appium.io/)
- **Cloud Execution:** [BrowserStack Device Farm SDK](https://www.browserstack.com/)
- **Reporting:** Serenity HTML Reports
- **Utilities:** ZXing (QR/Barcode), SnakeYAML (Parsing), Logback (Logging)

## Requirements

### Prerequisites
- **Java 17**: Required for all tests.
- **Maven 3.9.6+**: Required for dependency management, build, and test execution.

### Automation - Specific Requirements
If you intend to run automated UI tests locally, the following additional tools are required:
- **Appium 2.x**: Mobile automation server.
- **Node.js**: Required to install Appium.
- **Android Studio 2023.1.1+**: For Android emulators/ADB.
- **Xcode 15+**: For iOS simulators/devices (macOS only).
- **WebDriverAgent**: Required for real iPhone devices.
- **BrowserStack Device Farm Account**: Required for executing tests on BrowserStack Device Farm.

> **Note:** To run **manual tests only**, Java 17 and Maven 3.9.6 are sufficient when executing manual test workflows.

## Test Case Overview

Tests are written in **Gherkin** and located in the `src/test/resources/features` directory.

**Structure:** `features` → `[iOS|Android]` → `[Epic]` → `[User Story]` → `.feature` files.

## Local Environment Setup For Real Device/Emulator

If you plan to use GitHub Actions for running our tests, you can skip this section.

### 1. Java & Maven Installation
- **Windows**: Download from [Oracle](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) and [Apache Maven](https://maven.apache.org/download.cgi).
- **macOS**: 
  ```bash
  brew install openjdk@17 maven
  ```

### 2. Appium & Drivers Setup
1. **Install Node.js**: Download from [nodejs.org](https://nodejs.org/en/download).
2. **Install Appium**:
   ```bash
   npm install -g appium
   appium --version
   ```
3. **Install Drivers**:
   ```bash
   appium driver install uiautomator2  # For Android
   appium driver install xcuitest     # For iOS
   ```
4. **Start Server**: Run `appium` in a dedicated terminal and keep it open.

### 3. Android Environment
1. Install **Android Studio** for running on an emulator and create a virtual device: `Pixel_6_API_33_1`.
2. **For Real Devices**: Enable **Developer Options** (Tap "Build Number" 7 times in Settings) and enable **USB Debugging**.
3. **App Installation**: Build the Android app from the [Android Wallet UI Repository](https://github.com/eu-digital-identity-wallet/eudi-app-android-wallet-ui).

### 4. iOS Environment (macOS only)
1. Install **Xcode** from the App Store.
2. Connect your iPhone → Window → Devices and Simulators → Check "Show as run destination".
3. **App Installation**: Build the iOS app from the [iOS Wallet UI Repository](https://github.com/eu-digital-identity-wallet/eudi-app-ios-wallet-ui).
4. **WebDriverAgent (Real Devices)**:
   - Clone the WebDriverAgent project.
   - Install Carthage: `brew install carthage`.
   - Run `./Scripts/bootstrap.sh`.
   - Build the project in Xcode (`Cmd + B`) targeting your device.

## Running Tests Locally with Real Device/Emulator

### Step 1: Device Preparation
- **Start Appium**: Run `appium` in your terminal.
- **Get Device UDID**:
  - Android: `adb devices`
  - iOS: `xcrun simctl list`

### Step 2: Environment Configuration
Update `env.properties` with your device details and choose environment:

**Environment Selector:**
```properties
execution.environment=real
ci.environment=locally
```

**Android:**
```properties
appium.android.deviceName=YourDeviceName
appium.android.platformVersion=YourPlatformVersion
appium.android.platformName=Android
appium.android.udid=YourUDID
```

**iOS:**
```properties
appium.ios.deviceName=YourDeviceName
appium.ios.platformVersion=YourPlatformVersion
appium.ios.platformName=iOS
appium.ios.udid=YourUDID
```

### Step 3: Upload Application

Place the application files in: `src/test/resources/app/`

Required filenames:
- Android: `androidApp.apk`
- iOS: `iosApp.ipa`

### Step 4: Select Tests (Tags)
Edit `local-execution.cmd` (Windows) or `local-execution-mac.sh` (Mac) to specify tags:
- `@ANDROID` / `@IOS`: Run all tests for the platform.
- `@manual` / `@automated`: Run by test type.
- `@US_VD_TC_01`: Run a specific test case.

### Step 5: Execution
1. Open `src/test/java/eu/europa/eudi/utils/factory/locallyTestExecution.java`.
2. Set the script name on line 6 to `./local-execution-mac.sh` (Mac) or `local-execution.cmd` (Windows).
3. Run the following:
   ```bash
   cd ./src/test/java/eu/europa/eudi/utils/factory
   javac locallyTestExecution.java
   java locallyTestExecution
   ```

### Step 6: Report & Logs
- **Serenity Report**: Open `./target/site/serenity/index.html` in a browser.
- **App Logs**: Found in `./src/test/resources/features/[platform]/.../logs/ui/[testCase]_[numberRow].log`.

## Running Tests Locally with Real Device through BrowserStack Device Farm

### Step 1: Add BrowserStack Device Farm Account Credentials
Create a `browserstack-local.properties` file with your BrowserStack Device Farm Account Credentials.

**Important**: Add `browserstack-local.properties` to `.gitignore`

**BrowserStack Device Farm Credentials:**
```properties
browserstack.username=your_browserstack_username
browserstack.accesskey=your_browserstack_access_key
```

### Step 2: Environment Configuration
Update `env.properties` with your device details and choose environment:

**Environment Selector:**
```properties
execution.environment=browserstack
ci.environment=locally
```
**Android Configuration:**
```properties
browserstack.android.appUrl=bs://************************************
appium.android.deviceName=YourDeviceName
appium.android.platformVersion=YourPlatformVersion
appium.android.platformName=Android
```

**iOS Configuration:**
```properties
browserstack.ios.appUrl=bs://************************************
appium.ios.deviceName=YourDeviceName
appium.ios.platformVersion=YourPlatformVersion
appium.ios.platformName=iOS
```

**Note**:

App URL - Get from BrowserStack Device Farm Dashboard:

1. Log in to BrowserStack Device Farm: https://app.browserstack.com/app-automate
2. Click the "Upload new app" button in the top right
3. Select your compiled .ipa file (for iOS) and .apk file (for Android) from your local machine
4. Wait for the upload to complete (you'll see a success message)
5. The app URL will be displayed in the format: bs://xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
6. Copy this entire URL (including the bs:// prefix)
7. Paste it as the value for browserstack.ios.appUrl and browserstack.android.appUrl below

### Step 3: Select Tests (Tags)
Edit `local-execution.cmd` (Windows) or `local-execution-mac.sh` (Mac) to specify tags:
- `@ANDROID` / `@IOS`: Run all tests for the platform.
- `@manual` / `@automated`: Run by test type.
- `@US_VD_TC_01`: Run a specific test case.

### Step 4: Execution
1. Open `src/test/java/eu/europa/eudi/utils/factory/locallyTestExecution.java`.
2. Set the script name on line 6 to `./local-execution-mac.sh` (Mac) or `local-execution.cmd` (Windows).
3. Run the following:
   ```bash
   cd ./src/test/java/eu/europa/eudi/utils/factory
   javac locallyTestExecution.java
   java locallyTestExecution
   ```

### Step 5: Report & Logs
- **Serenity Report**: Open `./target/site/serenity/index.html` in a browser.
- **App Logs**: Found in `./src/test/resources/features/[platform]/.../logs/ui/[testCase]_[numberRow].log`. - To enable logging for automated scenarios with BrowserStack Device Farm, set in env.properties file:
    ```browserstack.device.logs.enabled=true```
## Running Tests via GitHub Actions & BrowserStack Device Farm

Trigger tests via the [GitHub Actions Tab](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/actions).

### Environment Configuration
Update `env.properties` and choose environment:

**Environment Selector:**
```properties
execution.environment=browserstack
ci.environment=githubactions
```

### Manual Tests
1. Select **"Manual Tests Execution"** workflow.
2. Click **Run workflow**, select the branch, and confirm.
3. Download the **manual-serenity-report** (.zip) and open `index.html`.

### Automated Tests
1. Select **"Android Automation Tests Execution"** or **"iOS Automation Tests Execution"** workflow .
2. Click **Run workflow**, configure the branch and test cases.
3. Download the **serenity-report** and **android-device-logs** or **ios-device-logs** artifacts.

## Test Environment Scope

The automated tests were designed, developed, and validated using the following target environment:

* **Device:** Samsung Galaxy S22 Ultra
* **Platform:** Android
* **Platform Version:** 12.0

While the framework is intended to support cross-platform execution (Android and iOS), the test implementation and object identification strategies were created based on the UI behavior and element hierarchy of the Samsung Galaxy S22 Ultra running Android 12.0.

Execution on different devices, manufacturers, operating system versions, or custom vendor UI implementations may result in differences in UI rendering, element identification, navigation flows, timing, and synchronization behavior. Consequently, issues such as those described in the sections below may occur when running the tests outside the validated target environment.

For any device or OS version other than Samsung Galaxy S22 Ultra (Android 12.0), it is recommended to verify the application's UI hierarchy using Appium Inspector and, where necessary, adjust element locators and test synchronization logic accordingly.

## Execution Notes & Known Limitations

When executing mobile automation tests, please be aware of the following environment-specific behaviors:

### Mobile Automation & Platform Variability

#### **Known Challenges & Behavior**

##### **1. UI Component Mismatch (OS & Versioning)**
Mobile OS updates (e.g., moving from Android 12 to Android 14, or iOS 16 to iOS 17) often change how native widgets are rendered.
* **Example:** A date selection feature may appear as a `Spinner` (dropdown) on one Android version, but as a `DatePicker` (calendar popup) on another. On iOS, this might manifest as a `PickerWheel`.

##### **2. Device-Specific Rendering (Fragmentation)**
Different manufacturers (Samsung, Xiaomi, Google Pixel, Apple) implement "skins" or custom UI layers over the base OS.
* **Impact:** This can cause discrepancies in element visibility, screen resolution, and the specific XPath/Accessibility ID required to locate an element.
* **Recommendation:** Always use **Appium Inspector** to verify the UI hierarchy on the specific device/emulator you are targeting.

##### **3. Environment Stability**

* **Physical Devices:** While more accurate for final validation, physical devices may encounter network latency or hardware-level interruptions that can cause transient test failures.

* **Emulators/Simulators:**
    * **Stability Warning:** Testing on emulators is intended primarily for development and debugging purposes and is not recommended for formal validation activities.
    * **Reliability:** Due to synchronization issues and architectural differences between emulators and real hardware, tests on emulators may fail unpredictably and do not produce reliable reports.
    * **Authentication Workflow:**
        * To continue beyond the initial app view, you must skip the Google Account sign-in step.
        * **Alternative:** If skipping the sign-in is not possible in your environment, you must manually create a local application account to bypass the Google authentication flow and proceed with the test steps.
## Troubleshooting

**Windows Long File Paths:**
If you encounter path length errors in Git, run:
```bash
git config --system core.longpaths true
```

## Automation & App Version Compatibility

This section documents the compatibility between test automation versions and EUDI Wallet application versions.

| Branch Automation Testing                                                                                               | Android App Version | iOS App Version | Release Date | Validated Device           |
|-------------------------------------------------------------------------------------------------------------------------|---|---|--------------|-|-------------------|
| [milestone/2026.Q2](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/tree/milestone/2026.Q2) | 2026.06.144-Dev | 2026.6.187-Dev | 2026-06-19   | Samsung Galaxy S22 Ultra (Android 12.0) |


## Contributing

Please refer to [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on how to contribute to this project.

## License

Copyright (c) 2024 European Commission

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at:

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
