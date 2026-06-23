@ANDROID @US_IDFUEDTA @Q2_2026
Feature: User document management experience
  As a user
  I want a clearer and more intuitive document management experience
  So that I can view, manage, and update my documents with minimal confusion and consistent UI patterns

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/316

  @US_IDFUEDTA_TC_01 @manual:Passed
  Scenario: Verify + button is removed in Android
    Given the user launches the app on Android
    When the Document List screen is displayed
    Then the + button is not visible

  @US_IDFUEDTA_TC_02 @manual:Passed
  Scenario: Verify search field label
    Given the user is on the Document List screen
    Then the search field should display the label Search

  @US_IDFUEDTA_TC_03 @manual:Passed
  Scenario: Verify Android FAB behavior on scroll
    Given the user is on the Document List screen on Android
    When the user scrolls down the list
    Then the Add document FAB should collapse to icon only
    When the user scrolls up
    Then the FAB should expand to show label and icon

  @US_IDFUEDTA_TC_04 @manual:Passed
  Scenario: Remaining instances section
    Given the user opens a document details screen
    When the user navigates at the bottom of the screen
    Then the remaining instances section is displayed at the bottom of the screen

  @US_IDFUEDTA_TC_05 @manual:Passed
  Scenario: Verify Eye button placement and text
    Given the user is on the Document Details screen
    Then the Eye button should be placed next to Document Details text

  @US_IDFUEDTA_TC_06 @manual:Passed
  Scenario: The Issuer details card is displayed on top
    Given the user is on the Document Details screen
    When the user observes the top of the screen
    Then the Issuer details card is displayed on top

  @US_IDFUEDTA_TC_07 @manual:Passed
  Scenario: Verify rename of delete button in Android
    Given the user is on the Document Details screen on Android
    Then the button should be labeled Remove from wallet
    And the text Delete document should not be visible

  @US_IDFUEDTA_TC_08 @manual:Passed
  Scenario: Verify arrow direction when collapsed
    Given the user opens the Filter screen on Android
    And a filter section is collapsed
    Then the arrow icon should point down

  @US_IDFUEDTA_TC_09 @manual:Passed
  Scenario: Verify arrow direction when expanded
    Given the user opens the Filter screen on Android
    When the user expands a filter section
    Then the arrow icon should point up


