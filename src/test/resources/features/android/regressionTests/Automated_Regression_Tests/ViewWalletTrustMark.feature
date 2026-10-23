@ANDROID @automated @US_VWTM
Feature: View Wallet Trust Mark
  As a EUDI Wallet User
  I want to view the Wallet Trust Mark within the wallet application,
  so that I can access information that helps me verify whether the wallet solution I am using is officially certified and trusted within the EUDI Wallet ecosystem.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/351

  @US_VWTM_TC_01
  Scenario: Display Trust Mark when opening the Wallet for the first time
    Given the user opens the Wallet for first time
    And the Wallet displays Trust Mark view
    When the user selects the Continue button
    And the Wallet displays Login screen
    And the authenticated user is on Wallet home screen
    When user selects the burger menu and chooses About EUDI Wallet
    Then Wallet displays the Trust Mark view
    When Trust Mark content is displayed
    Then Wallet displays the official EU Digital Identity Wallet Trust Mark graphics or logo
    And Wallet displays informational text according to the device language settings
    And Wallet provides links to certification status information
    When the user selects back arrow
    Then the Wallet returns to Wallet home screen

