@ANDROID @manual @US_HS
Feature: EUDI Wallet Home Screen
  As a EUDI Wallet User
  I want to have a ‘Home’ screen as the main screen of the EUDI Wallet
  So that I can have quick access to the key functionalities of the EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/107

  @US_HS_TC_01 @manual:InProgress
  Scenario: Bottom navigation bar options
    Given the user is on the EUDI Wallet Home screen
    Then the bottom navigation bar includes the options Home, Documents, and Transactions
    When the user selects the Home option from the bottom navigation bar
    Then the Home button is highlighted

  @US_HS_TC_02 @manual:InProgress
  Scenario: Home screen displays three main sections
    Given the user is on the EUDI Wallet Home screen
    Then the Home screen is scrollable
    And the Home screen display Present a document, Add a document, and Sign a document sections

  @US_HS_TC_03 @manual:InProgress
  Scenario: 'Present a document' section options
    Given the user is on the EUDI Wallet Home screen
    When the user clicks on the Present a document section
    Then the Present a document section include In Person and Online options

  @US_HS_TC_04 @manual:InProgress
  Scenario: 'Add a document' section options
    Given the user is on the EUDI Wallet Home screen
    When the user clicks on the Add a document section
    Then the Add a document section include Choose from list and Scan QR to add options

  @US_HS_TC_05 @manual:InProgress
  Scenario: 'Sign a document' section options
    Given the user is on the EUDI Wallet Home screen
    When the user clicks on the Sign a document section
    Then the Sign a document section include From device and Scan QR to sign options

  @US_HS_TC_06 @manual:InProgress
  Scenario: 'In-Person' button in 'Present a document' section
    Given the user is on the EUDI Wallet Home screen
    When the user clicks the Present a document section
    Then the In-Person button is displayed

  @US_HS_TC_07 @manual:InProgress
  Scenario: 'Online' button in 'Present a document' section
    Given the user is on the EUDI Wallet Home screen
    When the user clicks the Present a document section
    Then the Online button is displayed

  @US_HS_TC_08 @manual:InProgress
  Scenario: 'Choose from list' button in 'Add a document' section
    Given the user is on the EUDI Wallet Home screen
    When the user clicks the Add a document section
    Then the Choose from list button is displayed

  @US_HS_TC_09 @manual:InProgress
  Scenario: 'Scan QR to add' button in 'Add a document' section
    Given the user is on the EUDI Wallet Home screen
    When the user clicks the Add a document section
    Then the Scan QR to add button is displayed

  @US_HS_TC_10 @manual:InProgress
  Scenario: 'From device' button in 'Sign a document' section
    Given the user is on the EUDI Wallet Home screen
    When the user clicks on the Sign a document section
    Then the From device button is displayed

  @US_HS_TC_11 @manual:InProgress
  Scenario: Scan QR to sign button in 'Sign a document' section
    Given the user is on the EUDI Wallet Home screen
    When the user clicks on the Sign a document section
    Then the Scan QR to sign button is displayed
