@IOS @manual @US_RAPDCAPICD @Q1_2026
Feature: Cross-Device Presentation of Digital Attestations via EUDI Wallet
  As a EUDI Wallet User
  I want to present digital attestations to a requestor Relying Party residing on a different device (e.g., Laptop/Desktop) than my EUDI Wallet, by utilising browser capabilities
  So that I can provide verified information through the browser seamlessly, without manual app switching.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/323

  @US_RAPDCAPICD_TC_01 @manual:Passed
  Scenario: User initiates verification from Desktop browser
    Given the user is on the Relying Party service page
    When the user initiates the wallet verification flow
    Then a presentation request flow is initiated

  @US_RAPDCAPICD_TC_02 @manual:Passed
  Scenario: Desktop browser requests permission for Digital Credentials API
    Given a presentation request flow has been initiated
    When the Desktop browser invokes the Digital Credentials API
    Then the user is prompted to grant permission

  @US_RAPDCAPICD_TC_03 @manual:Passed
  Scenario: User grants permission on Desktop browser
    Given the browser displays a permission prompt
    When the user grants permission
    Then the Relying Party generates a valid presentation request

  @US_RAPDCAPICD_TC_04 @manual:Passed
  Scenario: QR code is displayed for cross-device flow
    Given a valid presentation request has been created
    When the browser processes the request
    Then a QR code is displayed to the user

  @US_RAPDCAPICD_TC_05 @manual:Passed
  Scenario: User scans QR code using mobile device
    Given a QR code is displayed on the Desktop browser
    When the user scans the QR code with the mobile device camera
    Then the presentation request is transferred to the mobile device

  @US_RAPDCAPICD_TC_06 @manual:Passed
  Scenario: No matching attestations on mobile device
    Given the mobile device has received a presentation request
    When no installed Wallet Unit contains suitable attestations
    Then the user is notified and the process is terminated

  @US_RAPDCAPICD_TC_07 @manual:Passed
  Scenario: Attestation identifying information is displayed
    Given the mobile device has received a presentation request
    When the system identifies one or more suitable attestations
    Then the user sees an extension of the Wallet open automatically

  @US_RAPDCAPICD_TC_08 @manual:Passed
  Scenario: Wallet displays requested attestations and attributes
    Given the extension of the Wallet is open
    When the presentation request screen is shown
    Then the requesting Relying Party and the requested attestations and related attributes are displayed

  @US_RAPDCAPICD_TC_09 @manual:Passed
  Scenario: Successful consent and authentication
    Given the presentation request screen is displayed in the Wallet
    When the user consents to share attributes and enters a valid six-digit PIN
    Then the attestation is presented successfully

  @US_RAPDCAPICD_TC_10 @manual:Passed
  Scenario: Invalid PIN during cross-device presentation
    Given the Wallet requests PIN authentication
    When the user enters an incorrect six-digit PIN
    Then an authentication error is displayed

  @US_RAPDCAPICD_TC_11 @manual:Passed
  Scenario: Relying Party verifies attestation successfully on Desktop
    Given the attestation has been presented from the mobile Wallet
    When the Relying Party validates the attestation successfully
    Then a verification confirmation is displayed in the Desktop browser

  @US_RAPDCAPICD_TC_12 @manual:Passed
  Scenario: Relying Party verification fails on Desktop
    Given the attestation has been presented from the mobile Wallet
    When the Relying Party fails to validate the attestation
    Then an error message is displayed and retry is allowed