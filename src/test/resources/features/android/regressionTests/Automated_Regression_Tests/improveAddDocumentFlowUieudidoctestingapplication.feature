@ANDROID @automated @US_IOFUEDTA
Feature: User onboarding experience
  As a new user
  I want a clear onboarding flow
  So that I can set up my wallet smoothly

  @US_IOFUEDTA_TC_01 @manual:Passed
  Scenario: Verify onboarding screens and wallet setup information
    Given the user launches the application for the first time or opens it again
    When the user proceeds through the onboarding flow
    Then the updated wallet logo is shown consistently across the onboarding screens
    And the message Secure your wallet with a PIN code and connect to your National System is displayed on the screen
    And the Type a PIN label is displayed above the PIN input fields
    And the Next button is not displayed after a PIN is entered
    When the user completes the PIN entry
    And the Confirm PIN label is displayed above the input fields
    And the user re-enters the PIN
    When the user opens the application again after successfully issuing a PID
    And navigates to the Home tab
    Then the greeting displays Welcome, [Name]