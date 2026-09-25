@ANDROID @US_VWTM @Q3_2026
Feature: View Wallet Trust Mark
  As a EUDI Wallet User
  I want to view the Wallet Trust Mark within the wallet application,
  so that I can access information that helps me verify whether the wallet solution I am using is officially certified and trusted within the EUDI Wallet ecosystem.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/351

  @US_VWTM_TC_01
  Scenario: Display Trust Mark during first login
    Given the user is logging in to the Wallet for the first time
    When the first-time login experience is displayed
    Then the Wallet displays the Trust Mark view

  @US_VWTM_TC_02
  Scenario: Access Trust Mark from the Wallet menu
    Given the authenticated user is on the Wallet home screen
    When the user selects the burger menu and chooses About EUDI Wallet
    Then the Wallet displays the Trust Mark view

  @US_VWTM_TC_03
  Scenario: Display Wallet Trust Mark information
    Given the user is viewing the Trust Mark screen
    When the Trust Mark content is displayed
    Then the Wallet displays the official EU Digital Identity Wallet Trust Mark graphics or logo
    And the Wallet displays informational text according to the device language settings
    And the Wallet provides links to certification status information

  @US_VWTM_TC_04
  Scenario: Return to the Wallet home screen
    Given the user is viewing the Trust Mark screen
    When the user selects the back arrow
    Then the Wallet returns to the Wallet home screen

  @US_VWTM_TC_05
  Scenario: Handle certification link opening failure
    Given a certification status link cannot be opened due to device restrictions or network issues
    When the user selects the certification status link
    Then the Wallet displays an appropriate error message
    And the Wallet allows the user to retry the action
