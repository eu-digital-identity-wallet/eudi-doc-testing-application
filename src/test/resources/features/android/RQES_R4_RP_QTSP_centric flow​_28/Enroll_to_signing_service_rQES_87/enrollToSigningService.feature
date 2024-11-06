@ANDROID @manual @US_ETSS
Feature: EUDI Wallet User Engagement with Trusted Issuers
  As a EUDI Wallet User
  I want to be able to engage with trusted issuers offering the option to issue attestations (i.e. issuer-initiated issuance flows)
  So that I can issue attestations from trusted issuers in my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/87

  Background:
    Given the user has a valid EUDI Wallet on their mobile device
    And the user has issued a PID attestation to the EUDI Wallet
    And the user has internet connectivity

  @US_ETSS_TC_01
  Scenario: User visits QTSP login page
    When the user visits the QTSP login page on a different device
    Then the QTSP should render the PID authentication option

  @US_ETSS_TC_02
  Scenario: User scans QR code after PID selection
    Given the user is on the QTSP login page
    When the user selects the PID authentication option
    Then the QTSP should render a QR code

  @US_ETSS_TC_03
  Scenario: User successfully authenticates in EUDI Wallet
    Given the user has scanned the QR code
    When the user authenticates successfully in the EUDI Wallet
    Then the EUDI Wallet should display the attestation release request

  @US_ETSS_TC_04
  Scenario: User fails to authenticate in EUDI Wallet
    Given the user has scanned the QR code
    When the user fails to authenticate in the EUDI Wallet
    Then the EUDI Wallet should present an error message

  @US_ETSS_TC_05
  Scenario: No matching attestations available
    Given the user has scanned the QR code
    And the user authenticates successfully in the EUDI Wallet
    When the EUDI Wallet checks for available attestations
    And there are no matching attestations
    Then the EUDI Wallet should inform the user

  @US_ETSS_TC_06
  Scenario: User consents to attestation release
    Given the user has scanned the QR code
    And the user authenticates successfully in the EUDI Wallet
    And there are matching attestations available
    When the user enters their six digit PIN
    Then the EUDI Wallet should present the requested attestation to the QTSP

  @US_ETSS_TC_07
  Scenario: QTSP cannot verify attestation
    Given the user has presented the requested attestation to the QTSP
    When the QTSP cannot verify the attestation
    Then an error message should inform the user

  @US_ETSS_TC_08
  Scenario: Successful attestation verification
    Given the user has presented the requested attestation to the QTSP
    When the QTSP verifies the attestation successfully
    Then the QTSP should inform the user of the successful verification outcome