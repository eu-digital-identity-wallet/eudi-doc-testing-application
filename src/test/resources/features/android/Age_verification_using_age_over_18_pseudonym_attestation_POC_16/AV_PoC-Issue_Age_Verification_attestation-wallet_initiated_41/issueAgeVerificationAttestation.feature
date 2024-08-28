@ANDROID @manual @US_IAVA
Feature: Issue age verification attestation
  As a user of the EUDI Wallet
  I want to request and store age verification attestations in my EUDI Wallet
  So that I can prove that I am over 18 years old without revealing my age or any other personal information

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/41

  @US_IAVA_TC_01 @manual:Passed
  Scenario: Log in successfully
    Given the user is on Login screen
    When the user enters the PIN
    Then the user see the dashboard screen

  @US_IAVA_TC_02 @manual:Passed
  Scenario: Add document via Age Verification
    Given the dashboard page is displayed on screen
    When the user clicks add doc button
    And the add document page is displayed on screen
    And the user clicks the Age Verification button
    Then on screen is displayed the authentication method selection

  @US_IAVA_TC_03 @manual:Passed
  Scenario: Select authentication method and enter data
    Given on screen is displayed the authentication method selection
    When the user clicks country selection
    And the user clicks FormEU
    Then the data page is displayed

  @US_IAVA_TC_04 @manual:Passed
  Scenario: Register personal data and view age verification
    Given a form is displayed
    When the user fills the form
    Then a success message is displayed on screen
    And the age verification is displayed in the dashboard