@ANDROID @manual @US_IAVA
Feature: Issue age verification attestation

  @US_IAVA_TC_01
  Scenario: Log in successfully
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the dashboard screen

  @US_IAVA_TC_02
  Scenario: Add document via Age Verification
    Given the dashboard page is displayed
    When the user clicks the add doc button
    And the add document page is displayed
    And the user clicks the Age Verification button
    Then the authentication method selection is displayed

  @US_IAVA_TC_03
  Scenario: Select authentication method and enter data
    Given the authentication method selection is displayed
    When the user clicks country selection
    And the user clicks FormEU
    Then the data page is displayed

  @US_IAVA_TC_04
  Scenario: Register personal data and view age verification
    Given a form is displayed
    When the user fills in the form
    Then a success message is displayed
    And the age verification is displayed in the dashboard