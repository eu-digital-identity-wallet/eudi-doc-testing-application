# Functional E2E tests for EUDI Wallet application

## Table of contents

* [Overview](#overview)
* [Requirements](#requirements)
* [Local Environment Setup](#local-environment-setup)
* [Test Case Overview](#test-case-overview)
* [Running Tests Locally](#running-tests-locally)
* [Running Tests via GitHub Actions and Device Farm (Browserstack)](#running-tests-via-github-actions-and-device-farm-browserstack)
* [Troubleshooting](#troubleshooting)
* [How to contribute](#how-to-contribute)
* [License](#license)
   + [License details](#license-details)


## Overview
This repository is dedicated to managing the test procedures for the EUDI Wallet project. Both manual and automated functional (UI) tests are conducted, following the agreed specifications in each Release. Any additional testing requests are evaluated on a case-by-case basis, and relevant tests are conducted accordingly. Following the instructions below, you can execute these tests locally or through GitHub Actions and generate test results for further analysis and debugging.

## Requirements
Ensure your system meets the following requirements to run the automated tests:
- [Maven 3.9.6](https://maven.apache.org/download.cgi) or newer: Apache Maven is a software project management tool. It manages the project's build, reporting, and documentation.
- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html): The programming language used for the automation scripts.
- [Appium 2.4.1](https://github.com/appium/appium/releases/tag/v2.4.1) or newer: An open-source tool for automating mobile applications. Required for Android testing (emulators).
- [Android Studio 2023.1.1](https://developer.android.com/studio) or newer: An integrated development environment (IDE) for Android app development.
- [Xcode 15](https://developer.apple.com/xcode/) or newer: An IDE for developing software on Mac OS, including iOS applications.
- [WebDriverAgent](https://github.com/facebookarchive/WebDriverAgent) (for real iPhone devices): WebDriverAgent is a WebDriver server implementation for iOS that can be used to remote control iOS devices. It allows you to launch & kill applications, tap & scroll views or confirm view presence on a screen. This is needed for running tests on real iOS devices.

**Note: To run the manual tests you need only maven 3.9.6 and java 17**

**Note: A physical device or an emulator will be needed to run the tests locally (for automation tests only).**

## Local Environment Setup

Before running tests locally, make sure to install and configure the required tools. If you want to run the tests through GitHub Actions, you do not need to pre-install anything. Simply proceed to the Running Tests via GitHub Actions and Device Farm (BrowserStack) section.
### 1. Install Java 17
<b>Windows</b>: Download from the [official Oracle website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) and follow the installation guide.

<b>Mac</b>: Use Homebrew
1. `brew install openjdk@17`
2. `java -version`
### 2. Install Maven
<b>Windows</b>: Download from the [official Apache Maven website](https://maven.apache.org/download.cgi) and follow the installation guide.

<b>Mac</b>: Use Homebrew
1. `brew install maven`
2. `mvn -v`

### 3. Install Appium and Drivers

This will allow you to run the automation scripts on the Android and iOS devices. Appium is an open-source tool for automating native, mobile web, and hybrid applications on both iOS and Android platforms. Here are the steps to download and install Appium and additional instructions for setting up ADB, UIAutomator and XCUITest.

**- Install Appium using Node.js**

1. Install Node.js
   The first step is to verify whether Node.js is already installed in the system. To do so, open the terminal and run the following command: `node --version`

   If Node.js is already installed in the system, it’ll return the Node version, else it throws an error stating – ‘node is not recognized’, which means Node.js is not installed in the system.
   To install Node.js, click [here](https://nodejs.org/en/download).

Once the installation is complete, restart the terminal and run again the command:
`node --version`

The Node.js installation also covers the installation of npm (node package manager).

2. Install Appium

To install Appium, run the following command:
1. `npm install -g appium`
2. `appium --version`

The command above will download and install Appium. Once done, verify the Appium installation on macOS or Windows by running the command below:
appium --version

This will return the latest Appium version that have installed in your system.

To start the Appium server from the terminal run the following command (keep this terminal open while tests run):
`appium`

3. Install Appium Drivers:

Install the necessary drivers for Android (UIAutomator2) and iOS (XCUITest).

- <b>Install Android Debug Bridge (ADB)</b> : ADB is a versatile command-line tool that allows you to communicate with an emulator or connected Android device. It facilitates a variety of device actions, such as installing and debugging apps, and it provides access to a Unix shell to run various commands on a device.
  Why is it necessary? ADB is essential for controlling your device over USB from a computer, copying files back and forth, installing and uninstalling apps, running shell commands, and more. It is particularly vital for automation testing as it allows the test scripts to communicate with the device.

- <b>Install UIAutomator</b>: UIAutomator is a UI testing framework that allows you to test your user interface (UI) efficiently by creating automated functional UI test cases that can be run against your app on one or more devices.
  Why is it necessary? UIAutomator can interact with all Android software and hardware across different devices and Android versions. It is necessary for E2E testing as it allows the scripts to simulate user interactions and verify the correct behavior of your application's user interface.

  `appium driver install uiautomator2`

- <b>Install XCUITest</b> : XCUITest is Apple's UI testing framework that allows you to write UI tests for iOS apps. It provides a robust way to simulate user interaction with your app and validate the results.
  Why is it necessary? XCUITest is used for automated testing of the app's user interface. It allows the tests to simulate user interactions and check that the application behaves correctly. This is extremely important for ensuring the quality of the app before it is released to the users.

  `appium driver install xcuitest`

### 4.Setup Android Environment
Install Android Studio from the [official Android website](https://developer.android.com/studio). Once installed, create a virtual device named "Pixel_6_API_33_1" using the AVD Manager.
If you want to execute the tests on a real device, you need to enable developer mode on an Android device. Follow these steps:

- Go to your device's settings.

- Scroll down and tap on 'About phone'.

- Scroll down to 'Build number' and tap it seven times. You'll see a message that says "You are now a developer!".

- After enabling Developer mode, you may want to enable USB Debugging for testing:

- Go back to the main settings page.

- Scroll down and tap on 'System'.

- Tap on 'Advanced'.

- Tap on 'Developer options'.

- Scroll down and enable 'USB Debugging', 'revoke USB debugging authorizations', 'install via USB' and 'USB debugging(Security settings)'

To complete the flows described below you need to build the android app into your device. You can build it from this repository [here](https://github.com/eu-digital-identity-wallet/eudi-app-android-wallet-ui) by following the instructions in the README file under the “How to build – Quick start guide” section.

### 5.Setup iOS Environment (macOS only)
Install Xcode from the [Mac App Store](https://apps.apple.com/app/xcode/id497799835). After installation, connect your iPhone to your Mac.

- Open Xcode.

- Go to 'Window' in the menu bar and click on 'Devices and Simulators'.

- In the opened window, choose your device.

- Check the 'Show as run destination' box to use this iPhone for running your apps.

Remember, to access the Developer options in iOS, your device must be connected to your Mac and recognized by Xcode.

To complete the flows described below you need to build the iOS app into your device. You can build it from this repository [here](https://github.com/eu-digital-identity-wallet/eudi-app-ios-wallet-ui) by following the instructions in the README file under the “How to build – Quick start guide” section.

### 6. AppiumWebDriverAgent (for real iPhone devices)
If you want to run tests on a real iPhone device, you will need to install WebDriverAgent on the device. WebDriverAgent is a WebDriver server implementation for iOS that allows you to control iOS devices remotely. Follow the steps below to install it:

- Clone the WebDriverAgent project from GitHub.

- Open the "WebDriverAgent.xcodeproj" project in Xcode.

- Make sure you have Carthage installed. If not, install it using Homebrew: brew install carthage.

- Run ./Scripts/bootstrap.sh in the project folder to download the necessary dependencies.

- Connect your iPhone to your Mac and select it as the target in Xcode.

- Press "Command + B" or click "Product -> Build" to build the project.

- If the build is successful, WebDriverAgent is installed on your iPhone.

## Test Case Overview
Test cases are written in Gherkin language for both manual and automated tests and can be found in the "
feature files
" folder [here](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/tree/main/src/test/resources/features).

There are two main categories: iOS and Android. Inside these folders, there are subfolders corresponding to the epics, named exactly as they appear on GitHub. Each epic folder contains subfolders named after the corresponding user stories. Finally, within the user story folders, there are the feature files that contain the relevant test cases.

## Running Tests Locally

To execute the tests, follow the steps below:

**<b>Note</b>: If you want to run only the manual tests start from the step 4.**

**To run both iOS and Android automated tests, you’ll need a Mac. However, Android tests can also be executed on Windows.**

1. Launch Appium: Open a command line terminal, type "appium", and press Enter. This will start the Appium server.

2. Connect a real device through cable to the machine and find the UDID of the device. 
   - For android devices execute the 'adb devices' command. 
   - For iphone devices execute the 'xcrun simctl list'.

   **For android devices are needed also:**
   - Step 1: Go to the settings menu and click on "about phone" icon.
   - Step 2: After clicking on the about phone icon you will be redirected to a page containing all the information related to your phone. Now click on build version for 7 times, yes you have to click 7 times on the build number. Once you will start clicking on the build version, you will get some pop-up messages stating “You are 4 steps away from being a developer“. After clicking it for 7 times, you will again get a pop-up message stating “You are now a developer“. This message means that you have enabled the developer options on your phone.
   - Step 3:  Click on additional settings after enabling developer options and there you can see the developer options:
   - Step 4: Now scroll down and search for the "USB debugging", "revoke USB debugging authorizations", "install via USB" and "USB debugging(Security settings)" option and enable them.(It's optional, it's depend on device)

3. Go to env.properties file and update the values based on your environment.

   Need to change this values:

   **<b>android</b>:**
   - appium.android.deviceName=POCO X5 Pro
   - appium.android.platformVersion=14.0
   - appium.android.platformName=Android
   - appium.android.udid=58d5b98

   **<b>ios</b>:**
   - appium.ios.deviceName=iPhone 14 Plus
   - appium.ios.platformVersion=18.0.1
   - appium.ios.platformName=iOS
   - appium.ios.udid=00008110-000470843429401E
 

4. Configure the Execution Script 

Find the local-execution.cmd (for Windows) or local-execution-mac.sh (for Mac and Android).

   Note: On tests there are some tags.

   - @ANDROID: will run all the android tests
   - @IOS: will run all the ios tests
   - @manual: will run all the manual tests
   - @automated: will run all the automated tests
   - Or you can run a specific test with a unique tag that each test has, for example @US_VD_TC_01.

   Tags: @before_01, @before_02, @before_03 are used for technical reasons on automation tests and don't use them for execution.

   Depending on which tests you want to run, you will include the corresponding tag in the local-execution.cmd or local-execution-mac.sh file.

5. Run the tests:

   1. First, ensure ReadmeManager.java is configured for your OS. Open the file at src/test/java/eu/europa/eudi/utils/factory/ReadmeManager.java and set the script name on line 6 to either ./local-execution-mac.sh for running iOS and android test or local-execution.cmd. for running android tests.
   2. Open a terminal, navigate to the correct directory, compile, and run the Java file:

      1. `cd ./src/test/java/eu/europa/eudi/utils/factory`
      2. `javac ReadmeManager.java`
      3. `java ReadmeManager`
                          

6. Export Test Report - Locally

After the test run completes, a Serenity report will be generated. Open the following file in your browser to view the results:
./target/site/serenity/index.html

**Note: Serenity Report is the same for both manual and automated tests, with clear distinctions among them if both types are present. The test results include comprehensive insights such as Passed/Failed test cases, specific test steps involved and any other critical information.**

7. Export Logs - Locally

After running the automated tests, a new file will be created with the logs of the app. For example, if you run test cases on the feature file located at ./src/test/resources/features/android/regressionTests/viewPID.feature file, then a new file will be created at ./src/test/resources/features/android/regressionTests/logs/viewPID.txt with the same name as the feature file. Double-click this file to view the logs.

## Running Tests via GitHub Actions and Device Farm (Browserstack)
You can also trigger test runs directly from the project's GitHub Actions [here](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/actions).
 
1. <b>Manual Tests</b>
   1. Step 1: Navigate to GitHub Actions - Go the project's GitHub Actions [here](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/actions).
   2. Step 2: Select and Run the Workflow

      1. From the workflow list on the left, select the test suite you wish to run (e.g., "Manual Tests Execution").
      2. On the right side, you will see the “Run workflow” tab. Click it, then select the desired branch or tag.
      3. Confirm by clicking the green "Run workflow" button in the dropdown. 

   3. Step 3: Download the Test Report

      1. Once the workflow run is complete, click on the completed run from the list.
      2. Under the "Artifacts" section, you will find the report. Click on it to download it as a .zip file.
      3. Unzip the file and open index.html to view the detailed Serenity report.

2. <b>Automated Tests</b>
   1. Step 1: Navigate to GitHub Actions - Go the project's GitHub Actions [here](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/actions).
   2. Step 2: Select and Run the Workflow

      1. From the workflow list on the left, select the test suite you wish to run (e.g., "Automation Tests Execution").
      2. On the right side, you will see the “Run workflow” tab. Click it, then select the desired workflow branch, branch to checkout and test cases.
      3. Confirm by clicking the green "Run workflow" button in the dropdown.

   3. Step 3: Download the Test Report

      1. Once the workflow run is complete, click on the completed run from the list.
      2. Under the "Artifacts" section, you will find the serenity-report. Click on it to download it as a .zip file.
      3. Unzip the file and open index.html to view the detailed Serenity report.    

   4. Step 4: Download the Logs

      1. Once the workflow run is complete, click on the completed run from the list.
      2. Under the "Artifacts" section, you will find the android-device-logs or ios-device-logs depending on which tests were run. Click on it to download it as a .zip file.
      3. Unzip the file and open the contents to view the logs for each feature file.

## Troubleshooting

If you encounter any issues while setting up or running the tests, consult the troubleshooting tips below:

- Long File Path Issues

On Windows, you might encounter issues with long file paths. To ensure Git can handle long paths, run the following command:

`git config --system core.longpaths true`

## How to contribute

We welcome contributions to this project. To ensure that the process is smooth for everyone
involved, follow the guidelines found in [CONTRIBUTING.md](CONTRIBUTING.md).


distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expres## License

## License
### License details

Copyright (c) 2024 European Commission

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, softwares or implied.
See the License for the specific language governing permissions and
limitations under the License.