@IOS @automated @US_VWCI
Feature: View Wallet Solution certification information
  As a EUDI Wallet User
  I want to view information about the certification of the Wallet Solution,
  so that I understand what wallet certification means and how the wallet solution is certified within the European Digital Identity framework.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/352

  @US_VWCI_TC_01 @GERMAN_LANGUAGE
  Scenario: Display Trust Mark when opening the Wallet for the first time
    Given the user opens the Wallet for first time
    And the Wallet displays Trust Mark view
    When the user selects the Continue button
    And the Wallet displays Login screen
    And the authenticated user is on Wallet home screen
    When user selects the burger menu and chooses About EUDI Wallet
    And Trust Mark content is displayed
    Then Wallet displays general informational text explaining the certification of Wallet Solutions within the EUDI Wallet ecosystem
    And Wallet displays informational text according to the device language settings
