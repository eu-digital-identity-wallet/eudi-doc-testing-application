@IOS @automated @US_SD @REG_GENERAL
Feature: Sign Document through EUDI Wallet
  As a EUDI Wallet User,
  I want to provide my explicit consent on signing a document through my EUDI Wallet and sing the document
  So that I can ensure that I am the sole signer of the specific document.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/212

  @US_SD_TC_01
  Scenario Outline: Sign a document through the EUDI Wallet
    Given the user uploads a document and an attestation using <issuer> and selects a QTSP
    When the EUDI Wallet fetches the Credential ID details from the QTSP
    Then the EUDI Wallet displays the Credential ID details to the user
    And the user agrees to proceed with the signing operation
    When the user opts not to proceed
    Then the user selects the Abort operation option
    And the EUDI Wallet redirects the user to the main page
    When the EUDI Wallet asks the user to consent to the release of the requested attestation
    And the user successfully authenticates in the Wallet
    Then the EUDI Wallet shares the requested attestation with the QTSP
    And a success screen appears with the signed document
    When the EUDI Wallet obtains the signed document
    Then the EUDI Wallet allows the user to share the document or close the process
    Examples:
      | issuer |
      | Python |
      | Kotlin |
