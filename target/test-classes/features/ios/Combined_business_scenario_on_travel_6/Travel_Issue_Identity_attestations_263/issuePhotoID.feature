@IOS @manual @US_IPID
Feature: Issue photo ID attestation
  As a user of the EUDI Wallet
  I want to request and store photo ID attestations in my EUDI Wallet
  So that I can identify myself using my EUDI Wallet in my travel user journey by presenting the requested attestations

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/263

  @US_IPID_TC_01 @manual:Passed
  Scenario: Log in successfully
    Given the user is on Login screen
    When the user enters the PIN
    Then the user see the dashboard screen

  @US_IPID_TC_02 @manual:Passed
  Scenario: Add photo ID
    Given the dashboard page is displayed on screen
    When the user clicks add doc button
    And the add document page is displayed on screen
    And the user clicks the Photo ID button
    Then on screen is displayed the authentication method selection

  @US_IPID_TC_03 @manual:Passed
  Scenario: Select authentication method and enter data
    Given on screen is displayed the authentication method selection
    When the user clicks country selection
    And the user clicks FormEU
    Then the data page is displayed

  @US_IPID_TC_04 @manual:Passed
  Scenario: Register personal data and view photo ID
    Given a form is displayed
    When the user fills the form
    Then a success message is displayed on screen
    And the photo ID is displayed in the dashboard
