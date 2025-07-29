@IOS @US_VT @Q1_2025
Feature: View Transaction in EUDI Wallet

 #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/121

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user is authenticated in the EUDI Wallet

  @US_VT_TC_01 @manual:Passed
  Scenario: User navigates to the Transactions screen
    When the user opens the EUDI Wallet application
    And the user navigates to the Transactions screen
    Then the Transactions screen is displayed

  @US_VT_TC_02 @Ignored
  Scenario Outline: User views attestation presentation transaction details
    Given the user is on the Transactions screen
    When the user selects an attestation presentation transaction entry from the list
    Then the EUDI Wallet presents the following <Detail> and <Description>
    Examples:
      | Detail             | Description                                                            |
      | Relying Party name | The name of the Relying Party with a verification badge if trusted     |
      | DateTime           | The datetime the presentation operation was performed                  |
      | Status             | The presentation operation status (Completed or Failed)                |
      | Attestation(s)     | Each attestation presented as a card that can be expanded or collapsed |
      | Visual Indication  | A visual indication e.g. signature icon                                |
      | Transaction Data   | The details and Location for the signed document                       |

  @US_VT_TC_03 @Ignored
  Scenario: User requests to delete transaction data
    Given the user is viewing the details of a transaction entry
    When the user requests to delete the transaction data
    Then the transaction data is deleted from the EUDI Wallet

  @US_VT_TC_04 @Ignored
  Scenario: User reports suspicious operations
    Given the user is viewing the details of a transaction entry
    When the user reports a suspicious operation related to the presentation
    Then the suspicious operation is reported to DPAs

  @US_VT_TC_05 @manual:Passed
  Scenario: User returns to Transactions screen
    Given the user is viewing the details of a transaction entry
    When the user presses the back button on top of the screen
    Then the user is returned to the Transactions page