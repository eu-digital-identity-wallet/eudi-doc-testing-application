@IOS @automated @US_DI
Feature: Deferred Credential Issuance
  As a user interacting with the issuer service
  I want to receive a credential through deferred issuance
  So that the credential can be securely delivered to my EUDI Wallet

# https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/217

  @US_DI_TC_01
  Scenario: Completing the deferred credential delivery process
    Given the user is accessing the issuer service
    When the user chooses to deliver a deferred credential to the wallet
    Then the EUDI Wallet application is opened
    And the issuance information is displayed to the user
    Then the user selects the ISSUE button
    And the user is redirected to the issuer service for authentication and authorization
    When the user completes authentication and confirms the issuance
    Then the user is returned to the wallet application
    And a notification indicates that the credential request is being processed
    When the user dismisses the notification by pressing OK
    Then the wallet dashboard is displayed
    And the document is displayed as unavailable with a pending status
    When the issuer provides the requested credential to the wallet
    Then the user receives a confirmation message indicating that the document has been issued
    When the user chooses to inspect the document details
    Then the issued credential information is displayed
    And the user presses the X button to close the document
    Then the document details are no longer displayed
    And the issued document is shown on the wallet dashboard
