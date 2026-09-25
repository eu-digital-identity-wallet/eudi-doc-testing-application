@ANDROID @US_ALOCWS @Q3_2026
Feature: Access the official list of certified Wallet Solutions
  As a EUDI Wallet User
  I want to access the official list of certified Wallet Solutions,
  so that I can verify which Wallet Solutions are officially certified within the EUDI Wallet ecosystem.

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/354

  @US_ALOCWS_TC_01
  Scenario: Access Trust Mark during first-time login
    Given the user is logging in to the Wallet for the first time
    When the first-time login experience is displayed
    Then the Wallet displays the Trust Mark view

  @US_ALOCWS_TC_02
  Scenario: Access Trust Mark from the Wallet menu
    Given the authenticated user is on the Wallet home screen
    When the user selects the burger menu and chooses About EUDI Wallet
    Then the Wallet displays the Trust Mark view

  @US_ALOCWS_TC_03
  Scenario: Display EUDI Wallet Provider Trusted List link
    Given the user is viewing the Trust Mark screen
    When the Trust Mark content is displayed
    Then the Wallet displays a link to the EUDI Wallet Provider Trusted List

  @US_ALOCWS_TC_04
  Scenario: Open the certified Wallet Solutions list
    Given the EUDI Wallet Provider Trusted List link is displayed
    When the user selects the link
    Then the Wallet opens the European Commission-hosted webpage in an external browser or supported web view
    And the webpage allows the user to view the officially certified Wallet Solutions

  @US_ALOCWS_TC_05
  Scenario: Handle certified Wallet Solutions list opening failure
    Given the certified Wallet Solutions webpage cannot be opened due to network or device limitations
    When the user selects the EUDI Wallet Provider Trusted List link
    Then the Wallet displays an appropriate error message
    And the Wallet allows the user to retry the action

  @US_ALOCWS_TC_06
  Scenario: Handle temporary unavailability of the official trusted list
    Given the official source providing the certified Wallet Solutions list is temporarily unavailable
    When the user attempts to access the EUDI Wallet Provider Trusted List
    Then the Wallet informs the user that the list cannot currently be retrieved


