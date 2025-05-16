@ANDROID @manual @US_SD @Q4_2024
Feature: Sign Document through EUDI Wallet
  As a EUDI Wallet User,
  I want to provide my explicit consent on signing a document through my EUDI Wallet and sing the document
  So that I can ensure that I am the sole signer of the specific document.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/92

  @US_SD_TC_01 @manual:Passed
  Scenario: Single Credential ID Available
    Given the user has uploaded a document and selected a QTSP
    When the EUDI Wallet retrieves the Credential ID details from the QTSP
    Then the EUDI Wallet presents the Credential ID details to the user
    And user confirms to proceed with the signing operation

  @US_SD_TC_02 @manual:Passed
  Scenario: User Aborts the Signing Operation
    Given the user is reviewing the Credential ID details in the EUDI Wallet
    When the user decides not to proceed
    Then the user can select the Abort operation option
    And EUDI Wallet should return the user to the main page

  @US_SD_TC_03 @manual:Passed
  Scenario: User consents to release attestation
    Given the user is reviewing the Credential ID details in the EUDI Wallet
    When the EUDI Wallet requests the user to consent to the release of the requested attestation
    And the user authenticates successfully in the Wallet, e.x. Share and PIN
    Then the EUDI Wallet presents the requested attestation to the QTSP
    And a success screen is displayed with the signed document

  @US_SD_TC_04 @manual:Passed
  Scenario: EUDI Wallet Stores the Signed Document
    Given the QTSP has signed the document and returned it
    When the EUDI Wallet receives the signed document
    Then the EUDI Wallet enables the user to share the document or close the process


