@IOS @manual @US_ETSS
Feature: EUDI Wallet User Engagement with Trusted Issuers
  As a EUDI Wallet User
  I want to be able to engage with trusted issuers offering the option to issue attestations (i.e. issuer-initiated issuance flows)
  So that I can issue attestations from trusted issuers in my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/87

  Background:
    Given the user has a valid EUDI Wallet on their mobile device
    And the user has issued a PID attestation to the EUDI Wallet
    And the user has internet connectivity

  @US_ETSS_TC_01 @manual:Passed
  Scenario: User visits QTSP login page
    Given the user visits the QTSP login page on a differrent device
    When the user selects the PID authentication option
    Then the QTSP should render a QR code

  @US_ETSS_TC_02 @manual:Passed
  Scenario: User successfully authenticates in EUDI Wallet
    Given the QR code in the QRSP is displayed
    When the user authenticates successfully in the EUDI Wallet
    And the user scans the displayed QR code from the QTSP
    Then the EUDI Wallet should display the attestation release request

  @US_ETSS_TC_03 @manual:Passed
  Scenario: User fails to authenticate in EUDI Wallet
    Given the user has scanned the QR code
    When the user fails to authenticate in the EUDI Wallet
    Then the EUDI Wallet should present an error message

  @US_ETSS_TC_04 @manual:Passed
  Scenario: User consents to release attestation
    Given the EUDI Wallet has found a matching attestation for the QTSP request
    When the EUDI Wallet requests the user’s consent to release the attestation
    And the user authenticates by entering the six-digit PIN
    Then EUDI Wallet should release the attestation to the QTSP
    And EUDI Wallet should display a confirmation message indicating the successful presentation

  @US_ETSS_TC_05 @manual:Passed
  Scenario: Successful attestation verification
    Given the user has shared the requested attestation to the QTSP
    When the user selects the option to create a qualified certificate from the QTSP
    And specifies an alias
    Then the QTSP generates the qualified certificate