@ANDROID @US_IDFUEDTA @Q2_2026
Feature: User document management experience
  As a user
  I want a clearer and more intuitive document management experience
  So that I can view, manage, and update my documents with minimal confusion and consistent UI patterns

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/316

  @US_IDFUEDTA_TC_01 @manual:
  Scenario: Verify + button is removed in Android
    Given the user launches the app on Android
    When the Document List screen is displayed
    Then the + button is not visible

  @US_IDFUEDTA_TC_02 @manual:
  Scenario: Verify search field label
    Given the user is on the Document List screen
    Then all search fields should display the label Search

  @US_IDFUEDTA_TC_03 @manual:
  Scenario: Verify Android FAB behavior on scroll
    Given the user is on the Document List screen on Android
    When the user scrolls down the list
    Then the Add document FAB should collapse to icon only
    When the user scrolls up
    Then the FAB should expand to show label and icon

  @US_IDFUEDTA_TC_04 @manual:
  Scenario: Verify issuer details card presence
    Given the user opens a document details screen
    Then the Issuer details card should be displayed
    And the remaining instances section should not be displayed separately

  @US_IDFUEDTA_TC_05 @manual:
  Scenario: Verify Eye button placement and text
    Given the user is on the Document Details screen
    Then the Eye button should be placed next to Document Details text

  @US_IDFUEDTA_TC_06 @manual:
  Scenario: Verify issuer details section removal
    Given the user is on the Document Details screen
    Then the Issuer details section should not be displayed

  @US_IDFUEDTA_TC_07 @manual:
  Scenario: Verify rename of delete button in Android
    Given the user is on the Document Details screen on Android
    Then the button should be labeled Remove from wallet
    And the text Delete document should not be visible

  @US_IDFUEDTA_TC_08 @manual:
  Scenario: Verify arrow direction when collapsed
    Given the user opens the Filter screen on Android
    And a filter section is collapsed
    Then the arrow icon should point down

  @US_IDFUEDTA_TC_09 @manual:
  Scenario: Verify arrow direction when expanded
    Given the user opens the Filter screen on Android
    When the user expands a filter section
    Then the arrow icon should point up

  @US_IDFUEDTA_TC_10 @manual:
  Scenario: Verify updated filter list
    Given the user opens the Filter screen on Android
    Then the filter list should match the updated UI provided filters

