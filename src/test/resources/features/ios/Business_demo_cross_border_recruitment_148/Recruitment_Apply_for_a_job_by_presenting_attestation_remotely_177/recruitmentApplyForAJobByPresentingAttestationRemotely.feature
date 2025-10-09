@IOS @US_RAFAJBPAR
Feature: Apply for Cruise Ship Position Using EUDI Wallet
  As a EUDI Wallet User
  I want to apply for a cruise ship position by presenting attestations to the Employer Service
  So that I can be authenticated and considered for the job

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/177

  Background:
    Given the user has a registered EUDI Wallet with issued attestations
    And internet connectivity is available between Employer Service and EUDI Wallet
    And the user visits Employer Service on desktop PC

  @US_RAFAJBPAR_TC_01
  Scenario: Present PID attestation for job application
    Given the user selects cruise ship position and Apply with EUDI Wallet
    When the user scans QR code and authenticates with PIN
    And the user consents to release PID attestation
    Then the PID attestation is verified by Employer Service
    And the job application is pre-filled with PID data (name, nationality, email, etc.)

  @US_RAFAJBPAR_TC_02
  Scenario: Present additional attestations via QR code
    Given the user has completed PID presentation
    When the user selects Provide additional information
    And the user scans QR code for Seafarer Certificate and Diploma
    And the user selects specific fields to share and authenticates
    Then the additional attestations are verified
    And the user can finalize the job application

  @US_RAFAJBPAR_TC_03
  Scenario: Present additional attestations via DeepLink
    Given the user has completed PID presentation on same device
    When the user selects Provide additional information
    And the user clicks DeepLink button
    And the user selects specific fields and authenticates
    Then the additional attestations are verified
    And the user can finalize the job application

  @US_RAFAJBPAR_TC_04
  Scenario: Handle authentication failure during presentation
    Given the user attempts to present attestations
    When the user enters incorrect PIN
    Then an error message is displayed
    And the user can retry authentication

  @US_RAFAJBPAR_TC_05
  Scenario: Handle missing required attestations
    Given the Employer Service requests specific attestations
    When the wallet checks for matching attestations and finds none
    Then the wallet informs the user about missing attestations
    And the user can return to wallet main page

  @US_RAFAJBPAR_TC_06
  Scenario: Handle attestation verification failure
    Given the user has presented attestations to Employer Service
    When the Employer Service cannot verify the attestation
    Then an error message is displayed to the user
    And the wallet displays the verification failure outcome

  @US_RAFAJBPAR_TC_07
  Scenario: Handle user cancellation during consent
    Given the wallet requests user consent for attestation release
    When the user decides not to proceed
    Then the user can return to wallet main screen
    And the presentation process is cancelled