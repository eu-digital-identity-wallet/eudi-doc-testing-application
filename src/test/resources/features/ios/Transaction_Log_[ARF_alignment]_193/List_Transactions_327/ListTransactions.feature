@IOS @US_LT @Q3_2026
Feature: List Transactions in History tab
  As a EUDI Wallet User
  I want to access the EUDI Wallet screen having organized all my transactions
  So that I can view and search all transactions executed through my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/327

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user is authenticated in the EUDI Wallet

  @US_LT_TC_01 @manual:
  Scenario: History button in bottom navigation bar
    Given the user is on the EUDI Wallet main screen
    When the user selects the History option in the bottom navigation bar
    Then the History button is highlighted
    And the History screen is displayed

  @US_LT_TC_02 @manual:
  Scenario: History screen content
    Given the user is on the History screen
    Then the History screen includes a Search bar
    And the History screen includes a Filter button

  @US_LT_TC_03 @manual:
  Scenario Outline: History screen lists transactions grouped by period
    Given the user is on the History screen
    Then the transactions are listed grouped by period as follows by <Group> and <Description>
    And the transactions are sorted in chronological order within each group

    Examples:
      | Group     | Description                          |
      | Today     | Transactions that occurred today     |
      | This Week | Transactions that occurred this week |
      | Month     | Transactions grouped by month 	     |

  @US_LT_TC_04 @manual:
  Scenario Outline: User views transaction entry details
    Given the user is on the History screen
    When the user selects a transaction entry
    Then a card is displayed for each transaction entry including <Detail> and <Description>

    Examples:
      | Detail           | Description                                                            |
      | Transaction Type | presentation, issuance, signing, deletion, etc.                        |
      | Status           | The transaction result, Completed or Not Completed                     |
      | Relying Party    | The Relying Party name or document name as follows:                    |
      | Relying Party    | For presentation transactions: 'Service Provider name'                 |
      | Relying Party    | For issuance transactions: 'PID Provider or Attestation Provider name' |
      | Relying Party    | For signing transactions: 'Signing Service Provider name'              |
      | Datetime         | The datetime of the transaction as follows:                            |
      | Datetime         | X minutes ago for transactions in the last 60 minutes                  |
      | Datetime         | Time (e.g., 11:07 AM) for transactions within the day                  |
      | Datetime         | Datetime (e.g., 14 Feb 2024 11:07 AM) for transactions before today    |

  @US_LT_TC_05 @manual:
  Scenario Outline: Transaction types displayed on the History screen
    Given the user is on the History screen
    Then the transaction type <Type> is <Displayed> on the History screen

    Examples:
      | Type                                                                  | Displayed |
      | Credential (PID or attestation) issuance and re-issuance transactions | Y         |
      | PID or attestation presentation transactions                          | Y         |
      | Signature or seal creation transactions                               | Y         |
      | Data deletion requests sent to a Relying Party                        | N         |
      | Suspicious Transaction Reports sent to a Data Protection Authority    | N         |
      | Credential (PID or attestation) deletion by the User                  | Y         |
