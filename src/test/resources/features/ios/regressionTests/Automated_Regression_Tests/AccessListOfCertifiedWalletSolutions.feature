@IOS @automated @US_ALOCWS
Feature: Access the official list of certified Wallet Solutions
  As a EUDI Wallet User
  I want to access the official list of certified Wallet Solutions,
  so that I can verify which Wallet Solutions are officially certified within the EUDI Wallet ecosystem.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/354

  @US_ALOCWS_TC_01
  Scenario: Display Trust Mark when opening the Wallet for the first time
    Given the user opens the Wallet for first time
    And the Wallet displays Trust Mark view
    When the user selects the Continue button
    Then the Wallet displays Login screen
    And the authenticated user is on Wallet home screen
    When user selects the burger menu and chooses About EUDI Wallet
    Then Wallet displays the Trust Mark view
    And Trust Mark content is displayed
    And Wallet displays a link to the EUDI Wallet Provider Trusted List
    And user selects the link
    When Wallet opens the European Commission-hosted webpage in an external browser or supported web view
    Then webpage allows the user to view the officially certified Wallet Solutions



