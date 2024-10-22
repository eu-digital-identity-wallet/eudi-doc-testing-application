@ANDROID @manual @US_ETSS
Feature: Engage with trusted issuers for issuer-initiated attestation flows in EUDI Wallet
  As a EUDI Wallet User,
  I want to engage with trusted issuers offering the option to issue attestations,
  So that I can issue attestations from trusted issuers in my EUDI Wallet.

#https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/87

  @US_ETSS_TC_01
  Scenario: User Selects PID Authentication on QTSP Page
    Given the user is registered with a valid EUDI Wallet on a mobile device
    And the user is on the QTSP login page on a separate device
    When the user selects the PID authentication option on the QTSP page
    Then QTSP should render a QR-code requesting the user to scan it to proceed to attestation presentation

  @US_ETSS_TC_02
  Scenario: User Scans QR-Code for Attestation Presentation
    Given the user has the QR-code displayed on the QTSP page
    When the user opens the EUDI Wallet on their mobile device
    And the user successfully authenticates in the EUDI Wallet
    And the user scans the QR code
    Then the EUDI Wallet should display a screen informing the user that the QTSP requests the release of the matching attestation (i.e. PID)
    And name of the QTSP should be displayed

  @US_ETSS_TC_03
  Scenario: User Fails to Authenticate in EUDI Wallet
    Given the user opens the EUDI Wallet to scan the QR-code
    When the user fails to authenticate (e.g., incorrect PIN)
    Then the EUDI Wallet should display an error message
    And user should be given the option to retry the authentication

  @US_ETSS_TC_04
  Scenario: No Matching Attestations in the EUDI Wallet
    Given the user successfully scans the QR-code
    And the EUDI Wallet checks for matching attestations
    When no matching attestations are found
    Then the EUDI Wallet should inform the user that no matching attestations are available
    And the user should be able to return to the main page of the Wallet

  @US_ETSS_TC_05
  Scenario: User Consents to Release Attestation
    Given the EUDI Wallet has found a matching attestation for the QTSP request
    When the EUDI Wallet requests the user’s consent to release the attestation
    And the user authenticates by entering the six-digit PIN
    Then EUDI Wallet should release the attestation to the QTSP

  @US_ETSS_TC_06
  Scenario: Unsuccessful Authentication for Attestation Release
    Given the user is requested to authenticate for attestation release
    When the user fails to authenticate (e.g., incorrect PIN)
    Then the Wallet should ask the user to retry authentication
    And user should have the option to abort the operation and return to the main page of the Wallet

  @US_ETSS_TC_07
  Scenario: QTSP Verifies Attestation Successfully
    Given the EUDI Wallet has presented the requested attestation to the QTSP
    When the QTSP verifies the attestation successfully
    Then the QTSP should inform the user of the successful verification outcome
    And EUDI Wallet should display a confirmation message indicating the successful presentation

