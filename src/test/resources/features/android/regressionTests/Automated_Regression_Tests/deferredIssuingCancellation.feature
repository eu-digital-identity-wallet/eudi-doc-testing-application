@ANDROID @automated @US_DIC
Feature: Credential Issuance Cancellation
  As a user of the issuer service
  I want to be able to issue a credential in deferred mode
  So that it can be securely added to my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/217

  @US_DIC_TC_01
  Scenario Outline: User cancels the deferred credential issuance process with <issuer>
    Given the user is accessing <issuer> service
    When the user chooses to deliver a deferred credential to the wallet with <issuer>
    Then the EUDI Wallet application is opened
    And the issuance information is displayed to the user for <issuer>
    When the user selects the cancel button
#    Then the wallet displays a confirmation modal to cancel the issuance process
#    When the user selects the cancel button on the confirmation modal
#    Then the issuing process is canceled
    And the wallet displays the Add document screen

    Examples:
      | issuer |
      | Python |
