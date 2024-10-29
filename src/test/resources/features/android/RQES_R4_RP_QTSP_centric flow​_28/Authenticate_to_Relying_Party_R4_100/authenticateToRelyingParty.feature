@ANDROID @manual @US_ATRP
Feature: EUDI Wallet User Logs into a Relying Party

#https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/100




  Background:
    Given the user has a valid EUDI Wallet on their mobile device
    And the user has issued a PID attestation to the EUDI Wallet
    And the user has internet connectivity

  Scenario: User visits Relying Party login page
    When the user visits the Relying Party login page on a different device
    Then the Relying Party should render the PID authentication option

  Scenario: Relying Party renders QR code
    Given the user is on the Relying Party login page
    When the user selects the PID authentication option
    Then the Relying Party should render a QR code

  Scenario: User successfully authenticates in EUDI Wallet
    Given the user has scanned the QR code
    When the user authenticates successfully in the EUDI Wallet
    Then the EUDI Wallet should display the attestation release request

  Scenario: User fails to authenticate in EUDI Wallet
    Given the user has scanned the QR code
    When the user fails to authenticate in the EUDI Wallet
    Then the EUDI Wallet should present an error message
    And the user should have the option to retry authentication

  Scenario: No matching attestations available
    Given the user has scanned the QR code
    And the user authenticates successfully in the EUDI Wallet
    When the EUDI Wallet checks for available attestations
    And there are no matching attestations
    Then the EUDI Wallet should inform the user
    And the user should return to the main page of the EUDI Wallet

  Scenario: User consents to attestation release
    Given the user has scanned the QR code
    And the user authenticates successfully in the EUDI Wallet
    And there are matching attestations available
    When the user enters their 6-digit PIN
    Then the EUDI Wallet should present the requested attestation to the Relying Party

  Scenario: Relying Party cannot verify attestation
    Given the user has presented the requested attestation to the Relying Party
    When the Relying Party cannot verify the attestation
    Then an error message should inform the user

  Scenario: Successful attestation verification
    Given the user has presented the requested attestation to the Relying Party
    When the Relying Party verifies the attestation successfully
    Then the Relying Party should inform the user of the successful verification outcome
    And the EUDI Wallet should display a confirmation message indicating the presentation outcome from the Relying Party


