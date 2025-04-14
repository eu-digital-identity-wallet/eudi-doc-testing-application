@IOS @manual @US_LT @Q1_2025
Feature: List Transactions in EUDI Wallet

#https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/124

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user is authenticated in the EUDI Wallet

  @US_LT_TC_01
  Scenario: Transactions button in bottom navigation bar
    Given the user is on the EUDI Wallet main screen
    When the user selects the Transactions option in the bottom navigation bar
    Then the Transactions button is highlighted
    And the Transactions screen is displayed

  @US_LT_TC_02
  Scenario: Transactions screen includes a Search bar
    Given the user is on the Transactions screen
    Then the Transactions screen includes a Search bar

  @US_LT_TC_03
  Scenario: Transactions screen includes an Export button
    Given the user is on the Transactions screen
    Then the Transactions screen includes an Export button

  @US_LT_TC_04
  Scenario: Transactions screen includes a Filter/Sort button
    Given the user is on the Transactions screen
    Then the Transactions screen includes a Filter/Sort button

  @US_LT_TC_05
  Scenario: Transactions screen lists transactions grouped by period
    Given the user is on the Transactions screen
    Then the transactions are listed grouped by period as follows:
      | Group         | Description                                     |
      | Today         | Transactions that occurred today                |
      | This Week     | Transactions that occurred this week            |
      | Month         | Transactions grouped by month (e.g., August 2024)|
    And the transactions are sorted in chronological order within each group

  @US_LT_TC_06
  Scenario: Transaction entry details
    Given the user is on the Transactions screen
    When the user views a transaction entry
    Then a card is displayed for each transaction entry including:
      | Detail                          | Description                                                               |
      | Status                          | The presentation or hashing operations status (Completed or Failed)       |
      | Relying Party/Document Name     | The relying party name or document name                                   |
      | Datetime                        | The datetime of the transaction as follows:                               |
      |                                 | - X minutes ago for transactions in the last 60 minutes                   |
      |                                 | - Time (e.g., 11:07 AM) for transactions within the day                   |
      |                                 | - Datetime (e.g., 14 Feb 2024 11:07 AM) for transactions before today      |
      | Visual Indication               | A visual indication (e.g., signature icon) for document hash or PID presentation |

  @US_LT_TC_07
  Scenario: User views transaction entry details
    Given the user is on the Transactions screen
    When the user selects a transaction entry
    Then the details of the transaction entry are displayed
    And the user is able to view the transaction details as per user story #121

