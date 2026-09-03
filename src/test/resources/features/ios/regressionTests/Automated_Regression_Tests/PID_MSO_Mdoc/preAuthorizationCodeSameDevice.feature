@IOS @automated @US_PACSD
Feature: Pre-authorization code same device
  As a user of the issuer service
  I want to be able to issue a credential using a transaction code
  So that it can be securely added to my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/243

  @US_PACSD_TC_01
  Scenario: User initiates credential issuance
    Given the user is accessing the issuer service
    When the user selects to issue a document using pre-authorization
    Then the user provides their personal information
    And a transaction code is generated
    And the user chooses to register through the EUDI wallet app
    And the user is navigated to the EUDI wallet application
    And the user provides the PIN
    When the issuer request is shown in the wallet app
    Then the user selects the ISSUE button
    And the Wallet app prompts for the transaction code
    And the user enters the transaction code received from the Issuer
    And the Wallet application shows a successful issuance message
    And the user selects the CONTINUE button
    Then the document appears on the documents screen