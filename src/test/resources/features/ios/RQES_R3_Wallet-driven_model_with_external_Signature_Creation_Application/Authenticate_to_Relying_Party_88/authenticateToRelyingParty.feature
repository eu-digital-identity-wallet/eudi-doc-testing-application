@IOS @manual @US_ATRP
Feature: Log in to a Relying Party using EUDI Wallet
  As a EUDI Wallet User,
  I want to use my EUDI Wallet to log in to a Relying Party,
  So that I can access the services provided by the Relying Party.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/88

  @US_ATRP_TC_01 @manual:InProgress
  Scenario: User Selects PID Authentication on Relying Party Login Page
    Given the user is registered with a valid EUDI Wallet on a mobile device
    And the user is on the Relying Party login page on a different device
    When the user selects the PID authentication option
    Then Relying Party should render a QR-code requesting the user to scan it to proceed to attestation presentation (PID)

  @US_ATRP_TC_02 @manual:InProgress
  Scenario: User Scans QR-Code for Attestation Presentation
    Given the user has the QR-code displayed on the Relying Party login page
    When the user opens the EUDI Wallet on their mobile device
    And the user successfully authenticates in the EUDI Wallet
    And the user scans the QR code
    Then the EUDI Wallet should display a screen informing the user that the Relying Party requests the release of the matching attestation (i.e. PID)
    And name of the Relying Party should be displayed

  @US_ATRP_TC_03 @manual:InProgress
  Scenario: User Fails to Authenticate in EUDI Wallet
    Given the user opens the EUDI Wallet to scan the QR-code
    When the user fails to authenticate (e.g., incorrect PIN)
    Then the EUDI Wallet should display an error message
    And user should be given the option to retry the authentication

  @US_ATRP_TC_04 @manual:InProgress
  Scenario: No Matching Attestations in the EUDI Wallet
    Given the user successfully scans the QR-code
    And the EUDI Wallet checks for matching attestations
    When no matching attestations are found
    Then the EUDI Wallet should inform the user that no matching attestations are available
    And user can return to the main page of the Wallet

  @US_ATRP_TC_05 @manual:InProgress
  Scenario: User Consents to Release Attestation
    Given the EUDI Wallet has found a matching attestation for the Relying Party's request
    When the EUDI Wallet requests the user’s consent to release the attestation
    And the user authenticates by entering the six-digit PIN
    Then EUDI Wallet should release the attestation to the Relying Party

  @US_ATRP_TC_06 @manual:InProgress
  Scenario: Unsuccessful Authentication for Attestation Release
    Given the user is requested to authenticate for attestation release
    When the user fails to authenticate (e.g., incorrect PIN)
    Then the Wallet should ask the user to retry authentication
    And the user should have the option to return to the main page of the Wallet

  @US_ATRP_TC_07 @manual:InProgress
  Scenario: EUDI Wallet Presents Attestation to the Relying Party
    Given the user has successfully authenticated and provided consent
    When the EUDI Wallet presents the requested attestation to the Relying Party
    Then the Relying Party should receive and verify the attestation
    And user should be informed of the verification outcome

  @US_ATRP_TC_08 @manual:InProgress
  Scenario: Relying Party Verifies Attestation Successfully
    Given the EUDI Wallet has presented the requested attestation to the Relying Party
    When the Relying Party successfully verifies the attestation
    Then the Relying Party should inform the user of the successful verification
    And EUDI Wallet should display a confirmation message indicating the successful presentation

