@IOS @automated @US_IMWFUEDTA
Feature: Improve My Wallet Flow UI
  As a user
  I want clearer navigation and more intuitive options within the My Wallet section
  So that I can easily access key information, manage my PIN, and understand available support resources

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/318

  @US_IMWFUEDTA_TC_01 @manual:Passed
  Scenario: Verify Change PIN screen and cancellation prompt
    Given the user opens the Settings option
    When the user accesses the Change PIN screen
    Then the screen title is displayed as "Change PIN"
    When the user taps the Back button
    Then a confirmation prompt is displayed with the text "Cancel PIN change?"
