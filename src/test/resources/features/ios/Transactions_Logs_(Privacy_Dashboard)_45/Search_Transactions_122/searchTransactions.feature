@IOS @US_ST @Q1_2025
Feature: Transaction Search in EUDI Wallet
  As a EUDI Wallet User
  I want to search for transactions in the EUDI Wallet
  So that I can find the transactions to match my search request

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/122

  @US_ST_TC_01 @manual:Passed
  Scenario: Initial state of the Transactions screen
    Given the user is on the Home screen
    When the user clicks on the Transactions button
    Then the Transactions screen is displayed
    And all transactions are displayed (no search or filters are applied)

  @US_ST_TC_02 @manual:Passed
  Scenario Outline: Transaction screen display features
    Given the user is on the Transactions screen
    When the user observes the transactions screen
    And the following are displayed <options>:
    And if there are no transactions for a period group, then this group is not displayed
    Examples:
      | options                                                              |
      | the transactions are grouped by period                               |
      | the search field is empty and enabled                                |
      | the search field contains a search icon on the left side             |
      | the search field displays a hinted search text "Search Transactions" |

  @US_ST_TC_03 @manual:Passed
  Scenario: User types text in the search field
    Given the user taps on the search field
    When the user types a text
    Then the search field returns the result which contains this text
    And respects the applied filtering and sorting criteria

  @US_ST_TC_04 @manual:Passed
  Scenario: User edits previous search text
    Given the user previously searched for a text
    When the user taps on the search field
    And the user edits the text
    Then the search field contains the edited text
    And returns the new results

  @US_ST_TC_05 @manual:Passed
  Scenario: User taps on the "Clear" trailing icon button
    Given the user previously searched for a text
    When the user taps on the search field
    And the user taps on the Clear icon button
    Then any existing text in the search field is cleared
    And the EUDI Wallet resets any previously applied search
    And the screen lists all transactions that respects the filtering and sorting already in force

  @US_ST_TC_06 @manual:Failed
  Scenario Outline: User taps on the "Search" button with search text
    Given the user taps on the search field
    When the user types a text
    Then the EUDI Wallet applies the search text to the attributes of <options>:
    And the transactions matching the search text are displayed
    And if no matching transactions are found, then the EUDI Wallet displays an informative message in the list section
    Examples:
      | options                 |
      | Relying party name      |
      | Attestation name        |
      | Attestation issuer name |