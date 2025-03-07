@ANDROID @manual @US_FASD @Q4_2024
Feature: Filtering and sorting documents in the EUDI Wallet

  As a EUDI Wallet User
  I want to filter and sort the documents in EUDI Wallet
  So that I can find and view the documents according to my needs

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/116

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user is on the Documents screen

  @US_FASD_TC_01 @manual:Failed
  Scenario: Initial state of the filter/sort option
    Given the user is viewing the Documents screen
    Then no filter should be applied (default state)
    And documents should be sorted by the default option (based on category)
    And the documents should be ordered by attestation name ascending within each category
    And the filter or sort button should have no visual indication that a filter or sort option is applied

  @US_FASD_TC_02 @manual:Passed
  Scenario: Opening the filter/sort bottom sheet
    Given the user is on the Documents screen
    When the user taps the filter or sort button
    Then the filter and sort bottom sheet should slide up from the bottom of the screen
    And the “Sort by” and “Filter by ..” sections should be displayed in collapsed view
    And each section should display the applied filter and sort options respectively

  @US_FASD_TC_03 @manual:Passed
  Scenario: Expanding and collapsing the "Sort by" section
    Given the user has opened the filter or sort bottom sheet
    When the user taps the Sort by section
    Then the section should switch between collapsed and expanded views

  @US_FASD_TC_04 @manual:Passed
  Scenario: Sorting documents by attribute
    Given the user has expanded the Sort by section
    When the user selects to sort by Date Issued
    And the user selects Descending order
    And the user taps the Apply button
    Then the bottom sheet should slide down
    And the EUDI Wallet should apply the sorting by Date Issued in descending order
    And the documents should be listed according to the selected sorting order

  @US_FASD_TC_05 @manual:Failed
  Scenario: Sorting documents by default option with grouping
    Given the user has opened the filter or sort bottom sheet
    When the user selects to sort by Default or Category
    And the user taps the Apply button
    Then the documents list should be grouped by category
    And within each category, the documents should be ordered by attestation name in ascending order

  @US_FASD_TC_06 @manual:Failed
  Scenario: Expanding and collapsing the "Filter by Category" section
    Given the user has opened the filter or sort bottom sheet
    When the user taps the Filter by Category section
    Then the section should switch between collapsed and expanded views

  @US_FASD_TC_07 @manual:Failed
  Scenario: Filtering documents by category
    Given the user has expanded the Filter by Category section
    When the user selects one or more categories
    And the user taps the Apply button
    Then the bottom sheet should slide down
    And the EUDI Wallet should apply the filter by the selected categories
    And only documents within those categories should be displayed

  @US_FASD_TC_08 @manual:Failed
  Scenario: Filtering documents by issuer
    Given the user has opened the filter or sort bottom sheet
    When the user taps the Filter by Issuer section
    And the user selects one or more issuers from the list
    And the user taps the Apply button
    Then the bottom sheet should slide down
    And the EUDI Wallet should apply the filter by the selected issuers
    And only documents issued by the selected issuers should be displayed

  @US_FASD_TC_09 @manual:Passed
  Scenario: Filtering documents by expiry period
    Given the user has opened the filter or sort bottom sheet
    When the user taps the Filter by Expiry Period section
    And the user selects an expiry period (e.g., Next thirty Days)
    And the user taps the Apply button
    Then the bottom sheet should slide down
    And the EUDI Wallet should apply the filter by the selected expiry period
    And only documents expiring within the selected period should be displayed

  @US_FASD_TC_10 @manual:Failed
  Scenario: Resetting filters and sorting
    Given the user has applied a filter or sort option
    When the user taps the Reset all button in the filter or sort bottom sheet
    Then the filters and sort options should be reset to the default values
    And the bottom sheet should slide down
    And the EUDI Wallet should display all non expired documents grouped by category in ascending order by attestation name

  @US_FASD_TC_11 @manual:Failed
  Scenario: No matching documents found after applying filters
    Given the user has applied a filter or sort option
    When no matching documents are found based on the applied filters
    Then the EUDI Wallet should display an informative message in the list section
    And if no results are found within a category, that category should not be displayed when sorting is set to Default or Category

  @US_FASD_TC_12 @manual:Passed
  Scenario: Visual indication of applied filters/sorting
    Given the user has applied a filter or sorting option that is different from the default
    When the user returns to the Documents screen
    Then the filter or sort button should have a visual indication that user filter(s) either and or or sorting options have been applied
