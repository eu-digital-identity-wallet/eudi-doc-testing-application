# Functional E2E tests for EUDI Wallet application

## Table of contents

* [Overview](#overview)
* [Requirements](#requirements)
* [Installation](#installation)
* [EUDI Wallet Application](#pplications)
* [Running the tests](#running-the-tests)
* [Implemented test cases](#implemented-test-cases)
* [How to contribute](#how-to-contribute)
* [License](#license)
    + [License details](#license-details)

    
## Overview
This repository is dedicated to managing the test procedures for the EUDI Wallet project. Both manual and automated functional (UI) tests will be conducted, following the User Stories created by the Analysis team. Any additional testing requests will be evaluated on a case-by-case basis, and relevant tests will be conducted accordingly. Following the instructions below, you can execute these tests locally and generate test results for further analysis and debugging.

## Requirements
To run the automated tests, ensure your system meets the following requirements:

- [Maven 3.9.6](https://maven.apache.org/download.cgi) or newer: Apache Maven is a software project management tool. It manages the project's build, reporting, and documentation.
- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html): The programming language used for writing the automation scripts.
- [Appium 2.4.1](https://github.com/appium/appium/releases/tag/v2.4.1) or newer: An open-source automation testing tool for mobile applications.
- [Android Studio 2023.1.1](https://developer.android.com/studio) or newer: An integrated development environment (IDE) for Android app development.
- [Xcode 15](https://developer.apple.com/xcode/) or newer: An IDE for developing software on Mac OS, including iOS applications.
- [WebDriverAgent](https://github.com/facebookarchive/WebDriverAgent) (for real iPhone devices): WebDriverAgent is a WebDriver server implementation for iOS that can be used to remote control iOS devices. It allows you to launch & kill applications, tap & scroll views or confirm view presence on a screen. This is needed for running tests on real iPhone devices.

**Note: If you want to run only the manual tests you need only maven 3.9.6 and java 17**

A physical device or an emulator will be needed to run the tests (for automation tests only).

## Installation

Before running the tests, install the required tools:

### 1. Java
Download the Java 17 SDK from the [official Oracle website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) and follow the installation instructions.
### 2. Maven
Download the Maven binaries from the [official Apache Maven website](https://maven.apache.org/download.cgi) and follow the installation guide.
### 3. Android Studio (setup emulator and/or physical device)
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


### 4. Xcode (setup physical device)
Install Xcode from the [Mac App Store](https://apps.apple.com/app/xcode/id497799835). After installation, connect your iPhone to your Mac.

- Open Xcode.

- Go to 'Window' in the menu bar and click on 'Devices and Simulators'.

- In the opened window, choose your device.

- Check the 'Show as run destination' box to use this iPhone for running your apps.

Remember, to access the Developer options in iOS, your device must be connected to your Mac and recognized by Xcode.

### 5. Appium
This will allow you to run the automation scripts on the Android and iOS devices. Appium is an open-source tool for automating native, mobile web, and hybrid applications on both iOS and Android platforms. Here are the steps to download and install Appium and additional instructions for setting up ADB, UIAutomator and XCUITest.

**-Install Appium using Node.js**

1. Install Node.js
The first step is to verify whether Node.js is already installed in the system. To do so, open the terminal and run the following command:
`node --version`
If Node.js is already installed in the system, it’ll return the Node version, else it throws an error stating – ‘node is not recognized’, which means Node.js is not installed in the system.
To install Node.js, click [here](https://nodejs.org/en/download).

Once the installation is complete, restart the terminal and run again the command:
`node --version`

The Node.js installation also covers the installation of npm (node package manager).

2. Install Appium

To install Appium, run the following command:
`npm install -g appium`

The command above will download and install Appium. Once done, verify the Appium installation on macOS or Windows by running the command below:
appium --version

This will return the latest Appium version that have installed in your system.

To start the Appium server from the terminal run the following command:
`appium`

- Install Android Debug Bridge (ADB) : ADB is a versatile command-line tool that allows you to communicate with an emulator or connected Android device. It facilitates a variety of device actions, such as installing and debugging apps, and it provides access to a Unix shell to run various commands on a device.
  Why is it necessary? ADB is essential for controlling your device over USB from a computer, copying files back and forth, installing and uninstalling apps, running shell commands, and more. It is particularly vital for automation testing as it allows the test scripts to communicate with the device.

- Install UIAutomator: UIAutomator is a UI testing framework that allows you to test your user interface (UI) efficiently by creating automated functional UI test cases that can be run against your app on one or more devices.
  Why is it necessary? UIAutomator can interact with all Android software and hardware across different devices and Android versions. It is necessary for E2E testing as it allows the scripts to simulate user interactions and verify the correct behavior of your application's user interface.

- Install XCUITest : XCUITest is Apple's UI testing framework that allows you to write UI tests for iOS apps. It provides a robust way to simulate user interaction with your app and validate the results.
  Why is it necessary? XCUITest is used for automated testing of the app's user interface. It allows the tests to simulate user interactions and check that the application behaves correctly. This is extremely important for ensuring the quality of the app before it is released to the users.

### 6. AppiumWebDriverAgent (for iPhone devices)
If you want to run tests on a real iPhone device, you will need to install WebDriverAgent on the device. WebDriverAgent is a WebDriver server implementation for iOS that allows you to control iOS devices remotely. Follow the steps below to install it:

- Clone the WebDriverAgent project from GitHub.

- Open the "WebDriverAgent.xcodeproj" project in Xcode.

- Make sure you have Carthage installed. If not, install it using Homebrew: brew install carthage.

- Run ./Scripts/bootstrap.sh in the project folder to download the necessary dependencies.

- Connect your iPhone to your Mac and select it as the target in Xcode.

- Press "Command + B" or click "Product -> Build" to build the project.

- If the build is successful, WebDriverAgent is installed on your iPhone.

## Application

Download and install the EUDIW app:

- iOS:
Minimum device requirements

    Any device that supports iOS 15.0

Prerequisites

To complete the flows described below you need to download the iOS app into your device. You can download it from App center download method (iOS app) [here](https://install.appcenter.ms/orgs/eu-digital-identity-wallet/apps/EUDI-Reference-iOS-Dev).

- Android:
Minimum device requirements

    API level 26.

To complete the flows described below you need to download the android app into your device. You can download it from App center download method (android app) [here](https://install.appcenter.ms/orgs/eu-digital-identity-wallet/apps/EUDI-Reference-Android-Dev).

After downloading you can drag and drop it inside the device that created before on android studio or let automated tests install them automatically.
Some of the tests need to install the app from scratch for this reason the executable files of the app should be also exist in \src\test\resources\app folder with the name androidApp.apk and iosApp.ipa

## Running the tests

To execute the tests, follow the steps below:

**Note: If you want to run only the manual tests start from the step 4.**

1. Launch Appium: Open a command line terminal, type "appium", and press Enter. This will start the Appium server.

2. Connect a real device through cable to the machine and find the UDID of the device. For android devices execute the 'adb devices' command. For iphone devices execute the 'xcrun simctl list'.
   
   **For android devices are needed also:**
   - Step 1: Go to the settings menu and click on "about phone" icon.
   - Step 2: After clicking on the about phone icon you will be redirected to a page containing all the information related to your phone. Now click on build version for 7 times, yes you have to click 7 times on the build number. Once you will start clicking on the build version, you will get some pop-up messages stating “You are 4 steps away from being a developer“. After clicking it for 7 times, you will again get a pop-up message stating “You are now a developer“. This message means that you have enabled the developer options on your phone.
   - Step 3:  Click on additional settings after enabling developer options and there you can see the developer options:
   - Step 4: Now scroll down and search for the "USB debugging", "revoke USB debugging authorizations", "install via USB" and "USB debugging(Security settings)" option and enable them.(It's optional, it's depend on device)

3. Go to env.properties file and update the values based on your environment.

   Need to change this values:

   **android:**
     - appium.android.deviceName=POCO X5 Pro
     - appium.android.platformVersion=14.0.8
     - appium.android.udid=emulator-5554
     - appium.android.udid=58d5b98

   **ios:**
     - appium.ios.deviceName=iPhone 14 Plus
     - appium.ios.platformVersion=17.3
     - appium.ios.platformName=iOS
     - appium.ios.udid=00008110-000470843429401E

4. Find the local-execution.cmd (for Windows) or local-execution-mac.sh (for Mac).

5. Run the tests: Depending on your system, execute the local-execution.cmd (for Windows) or local-execution-mac.sh (for Mac) to start the tests.
   In order to run them open the terminal and run `cd C:\Projects\wallet` (replace the path with the path that you have saved the project). After you navigate to the correct directory, run the .cmd file by typing its name and pressing Enter. So, you should type: `local-execution.cmd`

   Note: On tests there are some tags.
 
   - @ANDROID: will run all the android tests
   - @IOS: will run all the ios tests
   - @manual: will run all the manual tests
   - @automated: will run all the automated tests
   - Or you can run a specific test with a unique tag that each test has, for example @US_VD_TC_01.

   Tags: @before_01, @before_02, @before_03 are used for technical reasons on automation tests and don't use them for execution.

   Depending on which tests you want to run, you will include the corresponding tag in the local-execution.cmd or local-execution-mac.sh
6. After the execution the github actions are running and you can find them [here](https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application-internal/actions).

7. After the execution a report will be created and can be open by opening the index.html file in the path ./target/site/reports/index.html. Or you can find the report on github actions section, clicking the latest workflow and there you can find the report and download it.
 
   Serenity Report will be the same for both manual and automated tests, with clear distinctions among them if both types are present. The test results will include comprehensive insights such as Passed/Failed test cases, specific test steps involved and any other critical information.

## Implemented test cases
Test cases will be written in Gherkin language for both manual and automated tests and can be found in the "feature files" folder. Each feature file corresponds to a specific test case, outlining the steps to be followed, the expected outcome, and any prerequisites or assumptions.

The feature files will be named after the respective User Story to ensure better traceability.

In general, the test cases cover the following areas:

- Age Verification
- Credential Offer
- Pre-Authorization Code
- Regressions Tests

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