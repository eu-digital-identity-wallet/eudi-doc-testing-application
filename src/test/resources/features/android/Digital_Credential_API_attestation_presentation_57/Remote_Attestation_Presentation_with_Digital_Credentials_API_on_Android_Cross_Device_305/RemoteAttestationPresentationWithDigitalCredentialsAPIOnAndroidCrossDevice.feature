@ANDROID @US_RAPDCAPICD @Q1_2026
Feature: Cross-Device Presentation of Digital Attestations via EUDI Wallet
  As a EUDI Wallet User
  I want to present digital attestations to a requestor Relying Party residing on a different device (e.g., Laptop/Desktop) than my EUDI Wallet, by utilising browser capabilities
  So that I can provide verified information through the browser seamlessly, without manual app switching.

  @US_RAPDCAPICD_TC_01
  Scenario: User initiates verification from Desktop browser
    Given the user is on the Relying Party service page
    When the user initiates the wallet verification flow
    Then a presentation request flow is initiated

  @US_RAPDCAPICD_TC_02
  Scenario: Desktop browser requests permission for Digital Credentials API
    Given a presentation request flow has been initiated
    When the Desktop browser invokes the Digital Credentials API
    Then the user is prompted to grant permission

  @US_RAPDCAPICD_TC_03
  Scenario: User grants permission on Desktop browser
    Given the browser displays a permission prompt
    When the user grants permission
    Then the Relying Party generates a valid presentation request

  @US_RAPDCAPICD_TC_04
  Scenario: QR code is displayed for cross-device flow
    Given a valid presentation request has been created
    When the browser processes the request
    Then a QR code is displayed to the user

  @US_RAPDCAPICD_TC_05
  Scenario: User scans QR code using mobile device
    Given a QR code is displayed on the Desktop browser
    When the user scans the QR code with the mobile device camera
    Then the presentation request is transferred to the mobile device

  @US_RAPDCAPICD_TC_06
  Scenario: Mobile OS searches for matching attestations
    Given the mobile device has received a presentation request
    When the Mobile Operating System evaluates installed Wallet Units
    Then suitable attestations matching the request are identified

  @US_RAPDCAPICD_TC_07
  Scenario: No matching attestations on mobile device
    Given the mobile device has received a presentation request
    When no installed Wallet Unit contains suitable attestations
    Then the user is notified and the process is terminated

  @US_RAPDCAPICD_TC_08
  Scenario: Mobile OS displays selector with matching attestations
    Given suitable attestations exist on the mobile device
    When the Mobile Operating System prepares the selection interface
    Then a list of matching attestations is displayed

  @US_RAPDCAPICD_TC_09
  Scenario: Attestation identifying information is displayed
    Given the selector interface is displayed
    When the user views the list of attestations
    Then each attestation displays credential type, and wallet name

  @US_RAPDCAPICD_TC_10
  Scenario: Selector indicates Wallet Unit ownership
    Given multiple Wallet Units contain suitable attestations
    When the selector interface is shown
    Then each attestation indicates the Wallet Unit that holds it

  @US_RAPDCAPICD_TC_11
  Scenario: User selects an attestation and Wallet opens
    Given the selector interface displays matching attestations
    When the user selects one attestation
    Then the corresponding Wallet Unit is invoked

  @US_RAPDCAPICD_TC_12
  Scenario: Wallet displays requested attestations and attributes
    Given the Wallet has verified the Relying Party
    When the presentation request screen is shown
    Then the requested attestations and related attributes are displayed

  @US_RAPDCAPICD_TC_13
  Scenario: Successful consent and authentication
    Given the presentation request screen is displayed in the Wallet
    When the user consents to share attributes and enters a valid six-digit PIN
    Then the attestation is presented successfully

  @US_RAPDCAPICD_TC_14
  Scenario: Relying Party verifies attestation successfully
    Given the attestation has been presented from the mobile Wallet
    When the Relying Party validates the attestation successfully
    Then a verification confirmation is displayed in the Desktop browser

  @US_RAPDCAPICD_TC_15
  Scenario: Relying Party verification fails
    Given the attestation has been presented from the mobile Wallet
    When the Relying Party fails to validate the attestation
    Then an error message is displayed and retry is allowed