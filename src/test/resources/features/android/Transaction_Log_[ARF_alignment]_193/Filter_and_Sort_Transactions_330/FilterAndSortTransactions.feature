@ANDROID @US_FAST @Q3_2026
Feature: Filter and Sort Transactions in History tab
  As a EUDI Wallet User
  I want to filter the transactions in the EUDI Wallet
  So that I can find and view the transactions according to my needs

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/330

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user is authenticated in the EUDI Wallet

  @US_FAST_TC_01 @manual:
  Scenario: Initial state of filter option
    Given the user enters the History screen
    Then no filter is applied, the default option
    And the transactions are sorted by transaction date descending, the default sort option
    And the filter button has no visual indication that a user filter option is applied

  @US_FAST_TC_02 @manual:
  Scenario: Display filter bottom sheet
    Given the user is on the History screen
    When the user taps the filter button
    Then the filter bottom sheet options slide up from the bottom screen
    And the filter options are displayed in separate sections
    And each section under the title displays the available filter options

  @US_FAST_TC_03 @manual:
  Scenario: Filter transactions by Transaction Date Period
    Given the user is on the filter bottom sheet
    When the user selects to filter by Date
    Then the user can select the date range (date from and date to) using a calendar

  @US_FAST_TC_04 @manual:
  Scenario: Filter transactions by status
    Given the user is on the filter bottom sheet
    When the user selects to filter by Status
    Then the user can select an operation status option to filter the transactions

  @US_FAST_TC_05 @manual:
  Scenario: Filter transactions by relying party
    Given the user is on the filter bottom sheet
    When the user selects to filter by Relying Party
    Then the user can filter the transactions by selecting or deselecting the corresponding parties listed

  @US_FAST_TC_06 @manual:
  Scenario: Filter transactions by transaction type
    Given the user is on the filter bottom sheet
    When the user selects to filter by Transaction Type
    Then a list of all supported transaction types is displayed
    And the user can filter by selecting or deselecting the corresponding types listed

  @US_FAST_TC_07 @manual:
  Scenario: Reset all filter options
    Given the user is on the filter bottom sheet
    When the user taps the Reset all button
    Then the filters are reset to the default values
    And the filter button has no visual indication that a user filter option is applied

  @US_FAST_TC_08 @manual:
  Scenario: Apply filter options
    Given the user is on the filter bottom sheet
    When the user taps the Apply button
    Then the bottom sheet slides down and disappears
    And the EUDI Wallet applies a filter options to the transactions list
    And the filter button has visual indication that user filter options have been applied

  @US_FAST_TC_09 @manual:
  Scenario: No matching transactions found
    Given the user applies a filter option
    When no matching transactions are found
    Then the EUDI Wallet displays an informative message in the list section
    And if no results are found within a period group, then the period group is not displayed