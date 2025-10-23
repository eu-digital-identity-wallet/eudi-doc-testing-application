@ANDROID @US_LT @Q1_2025
Feature: List Transactions in EUDI Wallet
  As a EUDI Wallet User
  I want to access the EUDI Wallet screen having organized all my transactions
  So that I can view and search the attestation presentations and document signing operations I performed with my EUDI Wallet

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/189

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user is authenticated in the EUDI Wallet

  @US_LT_TC_01 @manual:Passed
  Scenario: Transactions button in bottom navigation bar
    Given the user is on the EUDI Wallet main screen
    When the user selects the Transactions option in the bottom navigation bar
    Then the Transactions button is highlighted
    And the Transactions screen is displayed

  @US_LT_TC_02 @manual:Passed
  Scenario: Transactions screen includes a Search bar
    Given the user is on the Transactions screen
    Then the Transactions screen includes a Search bar

  @US_LT_TC_03 @Ignored
  Scenario: Transactions screen includes an Export button
    Given the user is on the Transactions screen
    Then the Transactions screen includes an Export button

  @US_LT_TC_04 @manual:Passed
  Scenario: Transactions screen includes a Filter/Sort button
    Given the user is on the Transactions screen
    Then the Transactions screen includes a Filter/Sort button

  @US_LT_TC_05 @manual:Passed
  Scenario Outline: Transactions screen lists transactions grouped by period
    Given the user is on the Transactions screen
    Then the transactions are listed grouped by period as follows by <Group> and <Description>
    And the transactions are sorted in chronological order within each group
     Examples:
      | Group         | Description                                     |
      | Today         | Transactions that occurred today                |
      | This Week     | Transactions that occurred this week            |
      | Month         | Transactions grouped by month (e.g., August 2024)|

  @US_LT_TC_06 @manual:Passed
  Scenario Outline: Transaction entry details
    Given the user is on the Transactions screen
    When the user views a transaction entry
    Then a card is displayed for each transaction entry including <Detail> and <Description>
    Examples:
      | Detail        | Description                                                         |
      | Status        | The presentation or hashing operations status (Completed or Failed) |
      | Relying Party | The relying party name                                              |
      | Datetime      | The datetime of the transaction as follows:                         |
      | Datetime      | X minutes ago for transactions in the last 60 minutes               |
      | Datetime      | Time (e.g., 11:07 AM) for transactions within the day               |
      | Datetime      | Datetime (e.g., 14 Feb 2024 11:07 AM) for transactions before today |

  @US_LT_TC_07 @manual:Passed
  Scenario: User views transaction entry details
    Given the user is on the Transactions screen
    When the user selects a transaction entry
    Then the details of the transaction entry are displayed
    And the user is able to view the transaction details

