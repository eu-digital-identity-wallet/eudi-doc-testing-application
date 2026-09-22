@ANDROID @US_ST @Q3_2026
Feature: Transaction Search in History tab
  As a EUDI Wallet User
  I want to search for transactions in the EUDI Wallet
  So that I can quickly locate transactions in my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/329

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user is authenticated in the EUDI Wallet

  @US_ST_TC_01 @manual:
  Scenario: Initial state of the History screen
    Given the user is on the Home screen
    When the user clicks the History button
    Then the History screen is displayed
    And all transactions are displayed
    And no search or filters are applied

  @US_ST_TC_02 @manual:
  Scenario Outline: History screen display features
    Given the user is on the History screen
    When the user observes the transactions screen
    Then the following are displayed <options>:
    And if there are no transactions for a period group, then this group is not displayed

    Examples:
      | options                                                  |
      | the transactions are grouped by period                   |
      | the search field is empty and enabled                    |
      | the search field contains a search icon on the left side |
      | the search field displays a hinted search text "Search"  |

  @US_ST_TC_03 @manual:
  Scenario: Edit previous search text
    Given the user previously searched for a text
    When the user taps on the search field
    Then the user can edit the text
    And a Clear icon button (e.g. X) is displayed
    And the search field contains the edited text
    And returns the new results

  @US_ST_TC_04 @manual:
  Scenario: Search field "Clear" icon button
    Given the user types on the search field
    When the user taps on the Clear icon button
    Then any existing text in the search field is cleared
    And the EUDI Wallet resets any previously applied search
    And the screen lists all transactions that respects the filtering and sorting already in force

  @US_ST_TC_05 @manual:
  Scenario: Search with empty search field
    Given the user previously searched for a text and results are displayed
    When the user search with an empty search field
    Then the EUDI Wallet resets any previously applied search
    And the screen lists all transactions that respects the filtering and sorting already in force

  @US_ST_TC_06 @manual:
  Scenario Outline: Search using the search field
    Given the user taps on the search field
    When the user types a text
    Then the EUDI Wallet applies the search text to the attributes of <options>:
    And the transactions matching the search text are displayed
    And if no matching transactions are found, then the EUDI Wallet displays an informative message in the list section

    Examples:
      | options                                                                                           |
      | Relying Party name (attestation presentation)                                                     |
      | Intermediary name (attestation presentation)                                                      |
      | Document name (signing transactions)                                                              |
      | PID Provider or Attestation Provider name (PID/attestation issuance and re-issuance transactions) |
      | Attestation type (PID/attestation deletion)                                                       |
      | Provider name (PID/attestation deletion)                                                          |
