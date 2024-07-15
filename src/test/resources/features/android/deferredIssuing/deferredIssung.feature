@ANDROID @manual @US_DI
Feature: Deferred Issuing

  @US_DI_TC_01
  Scenario: User issues a credential to the wallet app
    Given the user is on the issuer service page
    When the user chooses to issue a credential to the wallet app
    Then the user is redirected to the wallet app
    And the user sees the details regarding the issuance

  @US_DI_TC_02
  Scenario: User proceeds with credential issuance
    Given the user is on the wallet app with issuance details
    When the user clicks the 'ISSUE' button
    Then the user is redirected to the issuer for authentication and consent

  @US_DI_TC_03
  Scenario: User authenticates and consents to issuance
    Given the user is on the issuer page for authentication and consent
    When the user authenticates and consents to the issuance
    Then the user is redirected to the wallet app
    And a message appears stating that the request is in progress

  @US_DI_TC_04
  Scenario: User acknowledges issuance progress
    Given the user sees the issuance in progress message
    When the user clicks OK
    Then the dashboard appears with the document grayed out and in a pending state

  @US_DI_TC_05
  Scenario: Wallet app polls issuer and receives credential
    Given the wallet app is polling the issuer for the credential
    When the issuer sends the credential to the wallet app
    Then the user views a modal informing them that the document has been issued

  @US_DI_TC_06
  Scenario: User views and adds issued document
    Given the user views the issuance confirmation modal
    When the user clicks the view button
    Then the user views the document information
    And the user clicks the ADD button
    Then the document appears on the dashboard screen

