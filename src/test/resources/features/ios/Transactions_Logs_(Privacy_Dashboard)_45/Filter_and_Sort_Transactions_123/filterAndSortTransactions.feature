@IOS @US_FASD @Q1_2025
Feature: Filter and Sort Transactions in EUDI Wallet

#https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/123

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user is authenticated in the EUDI Wallet

  @US_FASD_TC_01 @manual:Ignored
  Scenario: Initial state of filter/sort option
    Given the user enters the Transactions screen
    Then no filter is applied (default option)
    And the transactions are sorted by transaction date descending (default sort option)
    And the filter/sort button has no visual indication that a user filter/sort option is applied

  @US_FASD_TC_02
  Scenario: Display filter and sort bottom sheet
    Given the user is on the Transactions screen
    When the user taps the filter/sort button
    Then the filter and sort bottom sheet options slide up from the bottom screen
    And the "Sort by" and "Filter by" sections are displayed in collapsed view
    And each section under the title displays the applied filter and sort options respectively

  @US_FASD_TC_03
  Scenario: Sort transactions
    Given the user is on the filter and sort bottom sheet
    When the user taps the "Sort by" section
    Then the accordion section switches between collapse and expand view
    When the section is expanded
    Then the user can select the sort order between Ascending and Descending
    And the user can select the sorting attribute (transaction date) using a radio button
    And the transactions are grouped by period as per user story #124

  @US_FASD_TC_04
  Scenario: Filter transactions by date period
    Given the user is on the filter and sort bottom sheet
    When the user taps the "Filter by Transaction Date Period" section
    Then the accordion section switches between collapse and expand view
    When the section is expanded
    Then the user can select the date range (date from and date to) using a calendar

  @US_FASD_TC_05
  Scenario: Filter transactions by status
    Given the user is on the filter and sort bottom sheet
    When the user taps the "Filter by Status" section
    Then the accordion section switches between collapse and expand view
    When the section is expanded
    Then the user can select to filter the transactions by operation status (Completed, Failed) using checkboxes

  @US_FASD_TC_06
  Scenario: Filter transactions by relying party
    Given the user is on the filter and sort bottom sheet
    When the user taps the "Filter by Relying Party" section
    Then the accordion section switches between collapse and expand view
    When the section is expanded
    Then the user can select to filter the transactions by relying party name using checkboxes
    And the list of relying parties includes unique relying parties recorded in the presentation transactions in the EUDI Wallet

  @US_FASD_TC_07
  Scenario: Filter transactions by transaction type
    Given the user is on the filter and sort bottom sheet
    When the user taps the "Filter by Transaction Type" section
    Then the accordion section switches between collapse and expand view
    When the section is expanded
    Then the user can select to filter the transactions by transaction types (Presentation, Issuance, Signing) using checkboxes

  @US_FASD_TC_08
  Scenario: Reset filter and sort options
    Given the user is on the filter and sort bottom sheet
    When the user taps the "Reset all" button
    Then the sort and filters are reset to the default values (no filter and sort by transaction date ascending)
    And the filter/sort button has no visual indication that a user filter/sort option is applied

  @US_FASD_TC_09
  Scenario: Apply filter and sort options
    Given the user is on the filter and sort bottom sheet
    When the user taps the "Apply" button
    Then the bottom sheet slides down and disappears
    And the EUDI Wallet applies the filter and sorting options to the transactions list
    And the filter/sort button has visual indication that user filter(s) and/or sorting options have been applied (only when different than the default options)

  @US_FASD_TC_10
  Scenario: No matching transactions found
    Given the user applies a filter and sort option
    When no matching transactions are found
    Then the EUDI Wallet displays an informative message in the list section
    And if no results are found within a period group, then the period group is not displayed
