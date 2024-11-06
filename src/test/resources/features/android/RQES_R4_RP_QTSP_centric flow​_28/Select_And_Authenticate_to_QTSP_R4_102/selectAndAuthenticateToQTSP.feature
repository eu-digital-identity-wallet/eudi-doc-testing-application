@ANDROID @manual @US_SAATQ
Feature: EUDI Wallet User Authenticates to QTSP for Document Signing
  As a EUDI Wallet User,
  I want to use my EUDI Wallet to authenticate to QTSPs
  so that I can be securely authenticated and authorized by the QTSP which provides the signing service.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/102

  Background:
    Given the user has a valid EUDI Wallet on their mobile device
    And the user has issued a PID attestation to the EUDI Wallet
    And the user has internet connectivity
    And the user is authenticated in the Relying Party service
    And the user is viewing the document to be signed in the Relying Party service

  @US_SAATQ_TC_01
  Scenario: User initiates document signing
    Given the user is viewing the document in the Relying Party service
    When the user selects the option to Sign Document
    Then the Relying Party should prompt the user to select a QTSP from a list of QTSPs

  @US_SAATQ_TC_02
  Scenario: User selects a QTSP for signing
    Given the user is prompted to select a QTSP
    When the user selects a preferred QTSP
    Then the Relying Party should redirect the user to the selected QTSP login page requesting access

  @US_SAATQ_TC_03
  Scenario: QTSP requests access on behalf of Relying Party
    Given the user is on the QTSP login page
    When the QTSP requests access on behalf of the Relying Party
    Then the QTSP should inform the user that the Relying Party requests access to the QTSP service

  @US_SAATQ_TC_04
  Scenario: QTSP renders QR code for EUDI Wallet scan
    Given the user is on the QTSP login page with a request for access
    When the QTSP displays a QR code for authentication
    Then the user should be able to scan the QR code with their EUDI Wallet

  @US_SAATQ_TC_05
  Scenario: User scans QR code with EUDI Wallet
    Given the QTSP has displayed a QR code
    When the user scans the QR code with their EUDI Wallet
    Then the EUDI Wallet should inform the user that the QTSP requests to release the matching attestation (PID)

  @US_SAATQ_TC_06
  Scenario: EUDI Wallet checks for matching attestations
    Given the user is informed of the QTSP’s request for attestation release
    When the EUDI Wallet checks for available attestations to match the request
    Then the EUDI Wallet should display available matching attestations, if any

  @US_SAATQ_TC_07
  Scenario: No matching attestations available
    Given the EUDI Wallet checks for available attestations
    When there are no matching attestations available
    Then the EUDI Wallet should inform the user
    And the user should be able to return to the main page of the EUDI Wallet

  @US_SAATQ_TC_08
  Scenario: User consents to attestation release
    Given the user is informed of the QTSP request
    When the user chooses to release the requested attestation by entering their 6-digit PIN
    Then the EUDI Wallet should authenticate the user and proceed with attestation release

  @US_SAATQ_TC_09
  Scenario: User fails to authenticate in EUDI Wallet
    Given the user attempts to authenticate in the EUDI Wallet for attestation release
    When the authentication is unsuccessful
    Then the EUDI Wallet should present an error message
    And the user should have the option to retry authentication

  @US_SAATQ_TC_10
  Scenario: User declines to proceed with attestation release
    Given the user is informed of the QTSP request for attestation
    When the user chooses not to proceed with attestation release
    Then the user should be able to return to the main page of the EUDI Wallet

  @US_SAATQ_TC_11
  Scenario: EUDI Wallet presents attestation to QTSP
    Given the user has successfully authenticated and consented to attestation release
    When the EUDI Wallet presents the requested attestation to the QTSP
    Then the QTSP should receive andverify the attestation

  @US_SAATQ_TC_12
  Scenario: QTSP successfully verifies attestation
    Given the QTSP has received the attestation from the EUDI Wallet
    When the QTSP verifies the attestation successfully
    Then the QTSP should inform the user of the successful verification outcome
    And the EUDI Wallet should display confirmation message indicating the presentation outcome

  @US_SAATQ_TC_13
  Scenario: QTSP fails to verify attestation
    Given the QTSP has received the attestation from the EUDI Wallet
    When the QTSP fails to verify the attestation
    Then an error message should inform the user of the verification failure


