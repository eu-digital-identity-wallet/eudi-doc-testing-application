@ANDROID @US_IHFUEDTA @Q2_2026
Feature:  Improve History Flow UI
  As a user
  I want a clearer and more informative view of my past activities
  So that I can easily understand what actions were taken and access relevant details when needed

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/319

  @US_IHFUEDTA_TC_01 @manual:
  Scenario: Verify Transactions label is updated
    Given the user navigates through the application
    Then all instances of Transactions should be renamed to History
    And the corresponding icon should be updated

  @US_IHFUEDTA_TC_02 @manual:
  Scenario: Verify arrow direction when collapsed on Android
    Given the user opens the Filter screen on Android
    And a filter section is collapsed
    Then the arrow icon should point down

  @US_IHFUEDTA_TC_03 @manual:
  Scenario: Verify arrow direction when expanded on Android
    Given the user opens the Filter screen on Android
    When the user expands a filter section
    Then the arrow icon should point up
