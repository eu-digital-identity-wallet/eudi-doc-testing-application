@IOS @US_RAPDCAPISD @Q1_2026
Feature: Present Digital Attestations via Browser using EUDI Wallet on Same Device
  As a EUDI Wallet User
  I want to present digital attestations to a requestor Relying Party residing on the same device as my EUDI Wallet, by utilising browser capabilities
  So that I can provide verified information through the browser seamlessly, without manual app switching.

  @US_RAPDCAPISD_TC_01
  Scenario: User clicks Verify with EUDI Wallet button
    Given the user is on the Relying Party service page on the same device as the Wallet
    When the user clicks the Verify with EUDI Wallet button
    Then a presentation request is initiated by the Relying Party

  @US_RAPDCAPISD_TC_02
  Scenario: Browser prompts for Digital Credentials API permission
    Given a presentation request has been initiated
    When the browser processes the Digital Credentials API invocation
    Then the user is prompted to grant permission

  @US_RAPDCAPISD_TC_03
  Scenario: User grants Digital Credentials API permission
    Given the browser displays a permission prompt
    When the user grants permission
    Then the Operating System starts searching for suitable attestations

  @US_RAPDCAPISD_TC_04
  Scenario: User denies Digital Credentials API permission
    Given the browser displays a permission prompt
    When the user denies permission
    Then the presentation process is terminated

  @US_RAPDCAPISD_TC_05
  Scenario: Matching attestations are found on the device
    Given the Operating System searches installed Wallet Units
    When suitable attestations matching the request exist
    Then the browser displays a selector interface with the matching attestations

  @US_RAPDCAPISD_TC_06
  Scenario: No matching attestations found
    Given the Operating System searches installed Wallet Units
    When no suitable attestations match the request
    Then the user is notified and the process is terminated

  @US_RAPDCAPISD_TC_07
  Scenario: Attestation details are displayed in selector
    Given matching attestations are displayed in the selector interface
    When the user views the attestation list
    Then each attestation shows issuer, credential type, and wallet name

  @US_RAPDCAPISD_TC_08
  Scenario: Selector indicates wallet unit for each attestation
    Given multiple Wallet Units contain suitable attestations
    When the selector interface is displayed
    Then each attestation indicates which Wallet Unit holds it

  @US_RAPDCAPISD_TC_09
  Scenario: User selects an attestation from selector
    Given the selector interface displays suitable attestations
    When the user selects one attestation
    Then the corresponding Wallet Unit is invoked by the Operating System

  @US_RAPDCAPISD_TC_10
  Scenario: Wallet verifies Relying Party identity
    Given the Wallet Unit is opened after attestation selection
    When the Wallet validates the request origin and RP identity
    Then the verified identity of the Relying Party is displayed to the user

  @US_RAPDCAPISD_TC_11
  Scenario: Wallet displays requested attestations and attributes
    Given the Wallet has verified the Relying Party
    When the presentation request screen is shown
    Then the requested attestations and related attributes are displayed

  @US_RAPDCAPISD_TC_12
  Scenario: User consents to attribute sharing
    Given the presentation request screen is displayed in the Wallet
    When the user provides consent to share selected attributes
    Then the Wallet requests PIN authentication

  @US_RAPDCAPISD_TC_13
  Scenario: User enters valid PIN
    Given the Wallet requests PIN authentication
    When the user enters a valid six-digit PIN
    Then the attestation is presented successfully

  @US_RAPDCAPISD_TC_14
  Scenario: User enters invalid PIN
    Given the Wallet requests PIN authentication
    When the user enters an incorrect six-digit PIN
    Then an authentication error is displayed

  @US_RAPDCAPISD_TC_15
  Scenario: Relying Party verifies attestation successfully
    Given the attestation has been presented to the Relying Party
    When the Relying Party validates the attestation successfully
    Then a verification confirmation is displayed in the browser

  @US_RAPDCAPISD_TC_16
  Scenario: Relying Party verification fails
    Given the attestation has been presented to the Relying Party
    When the Relying Party fails to validate the attestation
    Then an error message is displayed and retry is allowed
