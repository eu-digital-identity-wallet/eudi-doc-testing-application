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

  @US_PACSD_TC_02
  Scenario: User continues with credential issuance
    Given the transaction code has been generated
    When the user chooses to register through the EUDI wallet app
    Then the user is navigated to the EUDI wallet application
    And the user provides the PIN

  @US_PACSD_TC_03
  Scenario: User handles the issuer request in the wallet
    Given the user has provided the PIN
    When the issuer request is shown in the wallet app
    Then the user selects the ISSUE button

  @US_PACSD_TC_04
  Scenario: User provides the transaction code
    Given the user has selected the ISSUE button
    When the Wallet app prompts for the transaction code
    Then the user enters the transaction code received from the Issuer

  @US_PACSD_TC_05
  Scenario: User views the issued document
    Given the user has entered the transaction code received from the Issuer
    When the Wallet application shows a successful issuance message
    Then the user selects the CONTINUE button
    And the document appears on the documents screen