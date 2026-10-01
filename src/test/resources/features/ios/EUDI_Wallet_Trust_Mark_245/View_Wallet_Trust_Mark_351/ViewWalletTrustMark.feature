@IOS @US_VWTM @Q3_2026
Feature: View Wallet Trust Mark
  As a EUDI Wallet User
  I want to view the Wallet Trust Mark within the wallet application,
  so that I can access information that helps me verify whether the wallet solution I am using is officially certified and trusted within the EUDI Wallet ecosystem.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/351

  @US_VWTM_TC_01 @manual:Passed
  Scenario: Display Trust Mark when opening the Wallet for the first time
    Given the user opens the Wallet for the first time
    Then the Wallet displays the Trust Mark view
    When the user selects Continue
    Then the Wallet displays the Login screen

  @US_VWTM_TC_02 @manual:Passed
  Scenario: Access Trust Mark from the Wallet menu
    Given the authenticated user is on the Wallet home screen
    When the user selects the burger menu and chooses About EUDI Wallet
    Then the Wallet displays the Trust Mark view

  @US_VWTM_TC_03 @manual:Passed
  Scenario: Display Wallet Trust Mark information
    Given the user is viewing the Trust Mark screen
    When the Trust Mark content is displayed
    Then the Wallet displays the official EU Digital Identity Wallet Trust Mark graphics or logo
    And the Wallet displays informational text according to the device language settings
    And the Wallet provides links to certification status information

  @US_VWTM_TC_04 @manual:Passed
  Scenario: Return to the Wallet home screen
    Given the user is viewing the Trust Mark screen
    When the user selects the back arrow
    Then the Wallet returns to the Wallet home screen

