@IOS @US_WPACCFAQ @Q2_2026
Feature: Wallet Presents Alternative Credential Combinations from a DCQL Query
  As a Wallet User
  I want the Wallet to show me all valid credential combinations defined in the RP’s DCQL query
  So that I can choose which combination of credentials I prefer to share.

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/350

  @US_WPACCFAQ_TC_01 @manual:Passed
  Scenario: Display all valid combinations
    Given the user opens the RP request
    When the Wallet loads the request
    Then it displays all valid credential combinations that satisfy the request

  @US_WPACCFAQ_TC_02 @manual:Passed
  Scenario: Display only valid combinations
    Given the Wallet contains stored credentials
    When the Wallet evaluates possible combinations
    Then it displays only valid combinations based on the stored credentials

  @US_WPACCFAQ_TC_03 @manual:Passed
  Scenario: User must select one combination when multiple exist
    Given more than one valid credential combination exists
    When the Wallet displays the combinations
    Then the user must select exactly one before continuing

  @US_WPACCFAQ_TC_04 @manual:Passed
  Scenario: Each option shows which credentials will be shared
    Given the Wallet displays available combinations
    When the user views the options
    Then each option clearly indicates which credentials will be shared

  @US_WPACCFAQ_TC_05 @manual:Passed
  Scenario: Only one valid combination exists
    Given only one valid credential combination exists
    When the Wallet displays it
    Then the user can proceed without making a selection

  @US_WPACCFAQ_TC_06 @manual:Passed
  Scenario: No valid combinations available
    Given the Wallet does not contain the required credentials
    When the Wallet evaluates the request
    Then it displays an error message explaining the request cannot be fulfilled

  @US_WPACCFAQ_TC_07 @manual:Passed
  Scenario: Show information that will be shared
    Given the user has selected a valid combination
    When the user proceeds
    Then the Wallet shows exactly which information will be shared with the RP

  @US_WPACCFAQ_TC_08 @manual:Passed
  Scenario: Do not show or share unrequested information
    Given the user proceeds with a valid combination
    When the Wallet prepares the data
    Then it does not show or share any information not requested by the RP

  @US_WPACCFAQ_TC_09 @manual:Passed
  Scenario: Canceling the flow
    Given the user is in the sharing flow
    When the user cancels
    Then the Wallet returns to the previous screen without sharing any information