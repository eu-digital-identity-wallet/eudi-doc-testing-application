@ANDROID @US_VWCI @Q3_2026
Feature: View Wallet Solution certification information
  As a EUDI Wallet User
  I want to view information about the certification of the Wallet Solution,
  so that I understand what wallet certification means and how the wallet solution is certified within the European Digital Identity framework.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/352

  @US_VWCI_TC_01
  Scenario: Access Trust Mark during first-time login
    Given the user is logging in to the Wallet for the first time
    When the first-time login experience is displayed
    Then the Wallet displays the Trust Mark view

  @US_VWCI_TC_02
  Scenario: Access Trust Mark from the Wallet menu
    Given the authenticated user is on the Wallet home screen
    When the user selects the burger menu and chooses About EUDI Wallet
    Then the Wallet displays the Trust Mark view

  @US_VWCI_TC_03
  Scenario: Display Wallet certification information
    Given the user is viewing the Trust Mark screen
    When the Trust Mark content is displayed
    Then the Wallet displays general informational text explaining the certification of Wallet Solutions within the EUDI Wallet ecosystem
    And the informational text is displayed according to the device language settings
