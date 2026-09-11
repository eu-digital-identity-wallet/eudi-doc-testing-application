@ANDROID @manual @US_HS @Q4_2024
Feature: EUDI Wallet Home Screen
  As a EUDI Wallet User
  I want to have a ‘Home’ screen as the main screen of the EUDI Wallet
  So that I can have quick access to the key functionalities of the EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/205

  @US_HS_TC_01 @manual:Passed
  Scenario: Bottom navigation bar options
    Given the user is viewing the EUDI Wallet Home screen
    Then the bottom navigation bar displays Home, Documents, and Transactions
    When the user chooses the Home option from the bottom navigation bar
    Then the Home option appears highlighted
    And the Home screen presents the Authenticate and Sign a document sections
    When the user taps the Authenticate section
    Then the Authenticate section provides In Person and Online options
    When the user selects the Sign a document section
    Then the Sign Document screen appears


