@ANDROID @US_IMWFUEDTA @Q2_2026
Feature: Improve My Wallet Flow UI
  As a user
  I want clearer navigation and more intuitive options within the My Wallet section
  So that I can easily access key information, manage my PIN, and understand available support resources

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/318

  @US_IMWFUEDTA_TC_01 @manual:
  Scenario: Verify Change PIN screen title
    Given the user navigates to the Settings
    When the user navigates to the Change PIN screen
    Then the screen title is displayed as Change PIN

  @US_IMWFUEDTA_TC_02 @manual:
  Scenario: Verify prompt appears when tapping Back
    Given the user is on the Change PIN screen
    When the user taps the Back button
    Then An informational prompt appears with the text: Cancel PIN change?
