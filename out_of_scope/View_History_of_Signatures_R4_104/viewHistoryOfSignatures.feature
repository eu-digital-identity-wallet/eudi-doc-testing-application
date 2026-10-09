Feature: EUDI Wallet User Views PID Presentation History for "Sign Document" Operations
  As a EUDI Wallet User,
  I want to view the history of the PID presentations associated with the “sign document” operation
  so that I am informed on when and which documents I have signed using my EUDI Wallet

    #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/104

  Background:
    Given the user has a valid EUDI Wallet on their mobile device
    And the user is authenticated in the EUDI Wallet
    And the history of PID presentations is available in the EUDI Wallet

  @US_VHOS_TC_01 @manual:InProgress
  Scenario: User views transaction history in the EUDI Wallet
    Given the user is on the main page of the EUDI Wallet
    When the user selects the option “View Transactions”
    Then the EUDI Wallet should display a page with all transactions associated with attestation presentations

  @US_VHOS_TC_02 @manual:InProgress
  Scenario: User applies filter to view signed document transactions
    Given the user is on the transactions page of the EUDI Wallet
    When the user selects the predefined filter option “Signed Documents”
    Then the EUDI Wallet should display only the PID presentations associated with “sign document” operations

  @US_VHOS_TC_03 @manual:InProgress
  Scenario: User expands a transaction history entry to view details
    Given the user is viewing the filtered list of “Signed Documents” transactions
    When the user clicks on the expand button of a transaction history entry
    Then the EUDI Wallet should expand the selected transaction entry
    And display the details of the transaction, including the “transaction data”

  @US_VHOS_TC_04 @manual:InProgress
  Scenario: No signed document transactions available in transaction history
    Given the user has filtered the transactions list by “Signed Documents”
    When there are no PID presentations associated with “sign document” operations
    Then the EUDI Wallet should display a message indicating no transactions are available for signed documents

  @US_VHOS_TC_05 @manual:InProgress
  Scenario: User collapses transaction details after viewing
    Given the user has expanded a transaction history entry to view details
    When the user clicks the collapse button on the expanded transaction entry
    Then the EUDI Wallet should collapse the entry and return it to the list view