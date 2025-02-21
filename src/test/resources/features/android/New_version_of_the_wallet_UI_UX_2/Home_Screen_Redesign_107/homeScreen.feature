@ANDROID @manual @US_HS @Q4_2024
Feature: EUDI Wallet Home Screen
  As a EUDI Wallet User
  I want to have a ‘Home’ screen as the main screen of the EUDI Wallet
  So that I can have quick access to the key functionalities of the EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/107

  @US_HS_TC_01 @manual:Passed
  Scenario: Bottom navigation bar options
    Given the user is on the EUDI Wallet Home screen
    Then the bottom navigation bar includes the options Home, Documents, and Transactions
    When the user selects the Home option from the bottom navigation bar
    Then the Home button is highlighted

  @US_HS_TC_02 @manual:Passed
  Scenario: Home screen displays two main sections
    Given the user is on the EUDI Wallet Home screen
    Then the user observes the Home screen
    And the Home screen display Authenticate and Sign a document sections

  @US_HS_TC_03 @manual:Passed
  Scenario: 'Present a document' section options
    Given the user is on the EUDI Wallet Home screen
    When the user clicks on the Authenticate section
    Then the Authenticate section include In Person and Online options

  @US_HS_TC_04 @manual:Passed
  Scenario: 'Sign a document' section options
    Given the user is on the EUDI Wallet Home screen
    When the user clicks on the Sign a document section
    Then the Sign Document screen is displayed


