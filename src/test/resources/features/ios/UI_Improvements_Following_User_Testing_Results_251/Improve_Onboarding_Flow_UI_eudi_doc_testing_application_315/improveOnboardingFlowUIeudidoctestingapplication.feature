@IOS @US_IOFUEDTA @Q2_2026
Feature: User onboarding experience
  As a new user
  I want a clear onboarding flow
  So that I can set up my wallet smoothly

#https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/315

  @US_IOFUEDTA_TC_01 @manual:Passed
  Scenario: Logo consistency across onboarding
    Given the user opens the app either for the first time, or for next time
    When the user navigates through onboarding screens
    Then the updated logo is displayed on all screens

  @US_IOFUEDTA_TC_02 @manual:Passed
  Scenario: Type PIN screen informational text
    Given the user opens the app for the first time
    When the user navigates to the Welcome to your wallet screen
    Then the following text is displayed: Secure your wallet with a PIN code and connect to your National System below the Welcome to your Wallet

  @US_IOFUEDTA_TC_03 @manual:Passed
  Scenario: Type PIN screen label
    Given the user opens the app for the first time
    When the user navigates to the Welcome to your wallet screen
    Then the Label Type a PIN is displayed above the PIN input fields

  @US_IOFUEDTA_TC_04 @manual:Passed
  Scenario: Confirm PIN screen informational text
    Given the user types a PIN for a first time
    When confirm PIN screen is displayed
    Then the following text is displayed: Secure your wallet with a PIN code and connect to your National System
    And Label Confirm PIN is displayed above the input fields

  @US_IOFUEDTA_TC_05 @manual:Passed
  Scenario: Home tab greeting updated
    Given the user opens the app again after issuing a PID
    When user navigates to the Home tab
    Then the greeting is updated to Welcome, [Name]

  @US_IOFUEDTA_TC_06 @manual:Passed
  Scenario: Home tab greeting updated
    Given the user opens the app
    When user navigates to the Home tab
    Then the hyperlink Learn more is a button