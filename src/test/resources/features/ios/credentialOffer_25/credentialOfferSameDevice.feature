@IOS @US_COSD @automated
Feature: Credential issuance process for the same device
  As a user of the issuer service
  I want to be able to issue a credential
  So that it can be securely added to my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-doc-reference-implementation-epics/issues/25

  @US_COSD_TC_01
  Scenario: User selects to issue a credential
    Given the user is on the issuer service
    When the user selects to issue a credential
    Then the user is redirected to the EUDI Wallet
    And the details of the credential to be issued are presented

  @US_COSD_TC_02
  Scenario: User proceeds with the credential issuance
    Given the user is presented with the credential details on the EUDI Wallet
    When the user presses the ISSUE button
    Then the user is redirected back to the issuer service
    And the user is prompted to authenticate and consent to the issuance

  @US_COSD_TC_03
  Scenario: User authenticates and consents to the issuance
    Given the user is asked to authenticate and consent on the issuer service
    When the user authenticates and consents to the issuance
    And inserts the required credential details
    Then the user is redirected to the EUDI Wallet app
    And a success message is displayed on the EUDI Wallet app

  @US_COSD_TC_04
  Scenario: User views the issued credential in the EUDI Wallet
    Given the user sees a success message in the EUDI Wallet app
    When the user presses the CONTINUE button
    Then the new document is presented in the EUDI Wallet dashboard screen

