@IOS @US_IHFUEDTA @Q2_2026
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
  Scenario: Verify filters grouping on iOS
    Given the user opens the Filter screen on iOS
    Then filters should be grouped by category

  @US_IHFUEDTA_TC_03 @manual:
  Scenario: Verify filter controls use switches on iOS
    Given the user is on the Filter screen on iOS
    Then all filter options should use switches instead of checkboxes

