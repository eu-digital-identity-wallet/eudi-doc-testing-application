@ANDROID @automated@US_IHFUEDTA
Feature: Validate History Flow UI Improvements
  As a user
  I want the History interface to be clear and consistent
  So that I can easily navigate and understand my previous activities

# https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/319

  @US_IHFUEDTA_TC_01 @manual:Passed
  Scenario: Verify History screen UI updates
    Given the History section is displayed
    And the corresponding icon should be updated to History
    Then all references to Transactions should be displayed as History
    And the search field should be labelled Search
    And the filter section is collapsed
    And collapsed filter sections should display a downward arrow
    Then expanded filter sections should display an upward arrow