@IOS @US_IDFUEDTA @Q2_2026
Feature: User document management experience
  As a user
  I want a clearer and more intuitive document management experience
  So that I can view, manage, and update my documents with minimal confusion and consistent UI patterns

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/316

  @US_IDFUEDTA_TC_01 @manual:
  Scenario: Verify + button color in iOS
    Given the user launches the app on iOS
    When the Document List screen is displayed
    Then the + button should be visible
    And the + button color should be blue

  @US_IDFUEDTA_TC_02 @manual:
  Scenario: Verify search field label
    Given the user is on the Document List screen
    Then all search fields should display the label Search

  @US_IDFUEDTA_TC_03 @manual:
  Scenario: Verify issuer details card presence
    Given the user opens a document details screen
    Then the Issuer details card should be displayed
    And the remaining instances section should not be displayed separately

  @US_IDFUEDTA_TC_04 @manual:
  Scenario: Verify Eye button placement and text
    Given the user is on the Document Details screen
    Then the Eye button should be placed next to Document Details text

  @US_IDFUEDTA_TC_05 @manual:
  Scenario: Verify issuer details section removal
    Given the user is on the Document Details screen
    Then the Issuer details section should not be displayed

  @US_IDFUEDTA_TC_06 @manual:
  Scenario: Verify filters grouping
    Given the user opens the Filter screen on iOS
    Then filters should be grouped by category

  @US_IDFUEDTA_TC_07 @manual:
  Scenario: Verify filter controls use switches
    Given the user is on the Filter screen on iOS
    Then all filter options should use switches instead of checkboxes

  @US_IDFUEDTA_TC_08 @manual:
  Scenario: Verify check button in header
    Given the user opens the Filter screen on iOS
    Then a check button should be visible in the upper right corner
    And the Apply button should not be visible

  @US_IDFUEDTA_TC_09 @manual:
  Scenario: Verify updated filter list on iOS
    Given the user opens the Filter screen on iOS
    Then the filter list should match the updated UI provided filters
