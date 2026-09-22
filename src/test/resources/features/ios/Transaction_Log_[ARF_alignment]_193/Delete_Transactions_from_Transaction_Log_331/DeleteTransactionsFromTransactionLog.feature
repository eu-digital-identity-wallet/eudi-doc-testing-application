@IOS @US_DTFTL @Q3_2026
Feature: Delete a transaction from transaction log
  As an EUDI Wallet User
  I want to delete a transaction from my EUDI Wallet transaction log
  So that I can manage my transaction history

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/331

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user is authenticated in the EUDI Wallet


  @US_DTFTL_TC_01 @manual:
  Scenario: Delete button is displayed
    Given the user is on the History screen
    When the user selects a transaction entry from the list
    Then the transaction details screen opens
    And a delete button is visible in the top right corner

  @US_DTFTL_TC_02 @manual:
  Scenario: Warning dialog appears before deletion
    Given the user has opened the details screen of a transaction
    When the user clicks the delete button
    Then a confirmation dialog is displayed
    And no transaction is removed yet

  @US_DTFTL_TC_03 @manual:
  Scenario: Warning dialog content and options are displayed
    Given the confirmation dialog is displayed
    Then the dialog include a warning message indicating the consequences of deleting the transaction
    And the dialog provides a Confirm deletion button and a Cancel button

  @US_DTFTL_TC_04 @manual:
  Scenario: User cancels the deletion
    Given the confirmation dialog is displayed for a transaction
    When the user selects Cancel
    Then the dialog closes
    And the user remains on the details screen of the same transaction
    And the transaction log is unchanged

  @US_DTFTL_TC_05 @manual:
  Scenario: Cancelled transaction still appears in the list
    Given the user has cancelled the deletion of a transaction
    When the user navigates back to the History screen
    Then the transaction is still visible in the list

  @US_DTFTL_TC_06 @manual:
  Scenario: User confirms the deletion of a transaction
    Given the confirmation dialog is displayed for a transaction
    When the user selects Confirm deletion
    Then the dialog closes
    And the user is returned to the History screen
    And the transaction is no longer shown in the list

  @US_DTFTL_TC_07 @manual:
  Scenario: Deleted transaction is not retrievable via search, filter or sort
    Given the user has deleted a transaction and knows its relying party name, type, status and date
    When the user searches for it or applies matching filters
    Then the deleted transaction is not returned in any of the search or filter

  @US_DTFTL_TC_08 @manual:
  Scenario: Deleting the only remaining transaction empties the log
    Given the transaction log contains exactly one transaction
    When the user deletes that transaction and confirms
    Then the history screen shows the empty state message

  @US_DTFTL_TC_09 @manual:
  Scenario: Deleting a transaction keeps the active filter or search applied
    Given the user reached a transaction through an active search or filter
    When the user deletes that transaction and confirms
    Then the user returns to the same filtered or search view
    And the deleted transaction is missing from the results
    And the other results remain displayed

  @US_DTFTL_TC_10 @manual:
  Scenario: Deletion flow is uniform across all transaction categories
    Given the user has one transaction of each category available
    When the user deletes them one by one via the delete button and confirmation dialog
    Then the same warning dialog appear for every category
    And each transaction disappears from the list search and filters
