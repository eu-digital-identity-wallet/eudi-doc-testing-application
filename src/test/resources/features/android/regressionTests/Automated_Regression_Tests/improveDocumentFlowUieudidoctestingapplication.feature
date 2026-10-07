@ANDROID @automated @US_IDFUEDTA
Feature: User document management experience
  As a user
  I want a clearer and more intuitive document management experience
  So that I can view, manage, and update my documents with minimal confusion and consistent UI patterns

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/316

  @US_IDFUEDTA_TC_01 @manual:Passed
  Scenario: Verify + button is removed in Android
    Given the user launches the app
    When Document List screen is displayed
    Then the + button is not visible anymore
    And the user is on Document List screen
    Then search field should display the label Search
    When user scrolls down the list
    Then Add document FAB should collapse to icon only
    When user scrolls up
    Then FAB should expand to show label and icon
    Given user opens a document details screen
    When user navigates at the bottom of the screen
    And the remaining instances section is displayed at the bottom of the screen page
    And Eye button should be placed next to Document Details text
    When user observes the top of the screen
    And Issuer details card is displayed on top
    And button should be labeled Remove from wallet
    And text Delete document should not be visible
    And user opens the Filter screen on Android
    And filter section is collapsed
    Then arrow icon should point down
    And user expands a filter section
    Then arrow icon should point up