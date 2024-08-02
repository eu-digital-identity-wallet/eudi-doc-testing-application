@ANDROID @manual @US_DI
Feature: Deferred Issuing
  As a user of the issuer service
  I want to be able to issue a credential in deferred mode
  So that it can be securely added to my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-doc-reference-implementation-epics/issues/24

  @US_DI_TC_01 @manual:Passed
  Scenario: User issues a credential to the wallet app
    Given the user is on the issuer service page
    When the user chooses to issue a credential to the wallet app
    Then the user is redirected to the wallet app
    And the user sees the details regarding the issuance

  @US_DI_TC_02 @manual:Passed
  Scenario: User proceeds with credential issuance
    Given the user is on the wallet app with issuance details
    When the user clicks the Issue button
    Then the user is redirected to the issuer for authentication and consent

  @US_DI_TC_03 @manual:Passed
  Scenario: User authenticates and consents to issuance
    Given the user is on the issuer page for authentication and consent
    When the user authenticates and consents the issuance
    Then the user is redirected to the wallet app
    And a message appears stating that the request is in progress

  @US_DI_TC_04 @manual:Passed
  Scenario: User acknowledges issuance progress
    Given the user sees the issuance in progress message
    When the user clicks OK
    Then the dashboard appears with the document grayed out and in a pending state

  @US_DI_TC_05 @manual:Passed
  Scenario: Wallet app polls issuer and receives credential
    Given the wallet app is polling the issuer for the credential
    When the issuer sends the credential to the wallet app
    Then the user views a modal informing them that the document has been issued

  @US_DI_TC_06 @manual:Passed
  Scenario: User views and adds issued document
    Given the user views the issuance confirmation modal
    When the user clicks to view the document information
    Then the document is open
    And the user clicks on the X button
    Then the document should close
    And the document appears on the dashboard screen

