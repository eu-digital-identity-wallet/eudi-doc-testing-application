@IOS @US_SD @Q3_2025
Feature: Signing a document with EUDI Wallet
  As a EUDI Wallet User
  I want to provide my explicit consent on signing a document through my EUDI Wallet
  So that I can ensure I sign the document as the sole signer of the specific document

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/120

  @US_SD_TC_01 @manual:Passed
  Scenario: Single Credential ID Available
    Given the user has uploaded a document and selected a QTSP
    When the EUDI Wallet retrieves the Credential ID details from the QTSP
    Then the EUDI Wallet presents the Credential ID details to the user
    And user confirms to proceed with the signing operation

  @US_SD_TC_02 @manual:Passed
  Scenario: Multiple Credential IDs available
    Given the QTSP has multiple Credential IDs enrolled for the user
    When the EUDI Wallet requests available Credential IDs from the QTSP
    Then the EUDI Wallet asks the user to select a preferred Credential ID

  @US_SD_TC_03 @manual:Passed
  Scenario: Single Credential ID available
    Given the QTSP has one Credential ID enrolled for the user
    When the EUDI Wallet requests available Credential IDs from the QTSP
    Then the flow continues to retrieve Credential ID details and calculate the document hash

  @US_SD_TC_04 @manual:Passed
  Scenario: QTSP redirects user to attestation request
    Given the EUDI Wallet has redirected the user to the QTSP authentication page
    When the QTSP provides a deep link for attestation presentation
    Then the EUDI Wallet opens and displays the request for PID attestation and transaction data

  @US_SD_TC_05 @manual:Passed
  Scenario: No matching attestations available
    Given the QTSP requests a PID attestation from the EUDI Wallet
    When the EUDI Wallet checks available attestations
    Then the EUDI Wallet informs the user that no matching attestations are available and returns to the main page

  @US_SD_TC_06 @manual:Passed
  Scenario: User consents to release attestation
    Given the user is reviewing the Credential ID details in the EUDI Wallet
    When the EUDI Wallet requests the user to consent to the release of the requested attestation
    And the user authenticates successfully in the Wallet, e.x. Share and PIN
    Then the EUDI Wallet presents the requested attestation to the QTSP
    And a success screen is displayed with the signed document

  @US_SD_TC_07 @manual:Passed
  Scenario: User authentication fails
    Given the EUDI Wallet requests the user to authenticate with a six-digit PIN
    When the user enters an incorrect PIN
    Then the EUDI Wallet asks the user to retry authentication

  @US_SD_TC_08 @manual:Passed
  Scenario: User cancels attestation release
    Given the EUDI Wallet requests the user to authenticate with a six-digit PIN
    When the user chooses not to proceed
    Then the EUDI Wallet returns to the main page without releasing the attestation

  @US_SD_TC_09 @manual:Passed
  Scenario: QTSP signs the document successfully
    Given the QTSP has successfully verified the PID attestation
    When the QTSP signs the document
    Then the QTSP returns the signed document to the EUDI Wallet where the user can view it



