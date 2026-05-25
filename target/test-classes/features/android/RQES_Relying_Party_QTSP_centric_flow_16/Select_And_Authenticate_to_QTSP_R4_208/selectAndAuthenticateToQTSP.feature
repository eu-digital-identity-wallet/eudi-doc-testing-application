@ANDROID @manual @US_SAATQ @Q4_2024
Feature: EUDI Wallet User Authenticates to QTSP for Document Signing
  As a EUDI Wallet User,
  I want to use my EUDI Wallet to authenticate to QTSPs
  so that I can be securely authenticated and authorized by the QTSP which provides the signing service.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/208

  Background:
    Given the user has a valid EUDI Wallet on their mobile device
    And the user has issued a PID attestation to the EUDI Wallet
    And the user has internet connectivity
    And the user is authenticated in the Relying Party service
    And the user is viewing the document to be signed in the Relying Party service

  @US_SAATQ_TC_01 @manual:Passed
  Scenario: User initiates document signing
    Given the user selects the option to Sign Document
    When the user selects the document in the Relying Party service
    Then the QTSP renders a QR code requesting the user to scan it

  @US_SAATQ_TC_02 @manual:Passed
  Scenario: User scans QR code with EUDI Wallet
    Given the QTSP has displayed a QR code
    When the user scans the QR code with their EUDI Wallet
    Then the EUDI Wallet informs the user that the QTSP requests to release the matching attestation (PID)

  @US_SAATQ_TC_3 @manual:Passed
  Scenario: User declines to proceed with attestation release
    Given the user is informed of the QTSP request for attestation
    When the user chooses not to proceed with attestation release
    Then the user should be able to return to the main page of the EUDI Wallet

  @US_SAATQ_TC_04 @manual:Passed
  Scenario: User consents to attestation release
    Given the EUDI Wallet user is informed of the QTSP request
    When the user chooses to release the requested attestation by entering their six-digit PIN
    Then the EUDI Wallet authenticates the user and proceed with attestation release

  @US_SAATQ_TC_05 @manual:Passed
  Scenario: Successful presentation of attestation
    Given the EUDI Wallet presents the requested attestation to the QTSP
    When the QTSP informs the user of the available credential(s) at the QTSP
    And the EUDI Wallet displays a confirmation message



