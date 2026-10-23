@IOS @automated @US_VCSOTWS
Feature: Access Wallet Solution certification status information
  As a EUDI Wallet User
  I want to access the certification status information of the Wallet Solution,
  so that I can verify that the wallet I am using is officially certified and trusted within the EUDI Wallet ecosystem.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/353

  @US_VCSOTWS_TC_01
  Scenario: Display Trust Mark when opening the Wallet for the first time
    Given the user opens the Wallet for first time
    And the Wallet displays Trust Mark view
    When the user selects the Continue button
    And the Wallet displays Login screen
    And the authenticated user is on Wallet home screen
    When user selects the burger menu and chooses About EUDI Wallet
    Then Wallet displays the Trust Mark view
    And Wallet displays a link to the Wallet Solution certification information page
    When user selects the certification information link
    And Wallet opens the European Commission-hosted certification information page in an external browser or supported web view
    Then page allows the user to verify the certification status of the Wallet Solution

