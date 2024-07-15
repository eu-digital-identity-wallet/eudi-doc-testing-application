@ANDROID @manual @US_IAVA
Feature: Issue age verification attestation

  @US_IAVA_TC_01 @manual:Passed
  Scenario: Log in successfully
    Given the user is on the Login screen manually
    When the user enters their PIN manually
    Then the user should see the dashboard screen

  @US_IAVA_TC_02 @manual:Passed
  Scenario: Add document via Age Verification
    Given the dashboard page is displayed manually
    When the user clicks the add doc button manually
    And the add document page is displayed on screen
    And the user clicks the Age Verification button
    Then the authentication method selection is displayed manually

  @US_IAVA_TC_03 @manual:Passed
  Scenario: Select authentication method and enter data
    Given the authentication method selection is displayed manually
    When the user clicks country selection
    And the user clicks FormEU
    Then the data page is displayed

  @US_IAVA_TC_04 @manual:Passed
  Scenario: Register personal data and view age verification
    Given a form is displayed
    When the user fills in the form manually
    Then a success message is displayed manually
    And the age verification is displayed in the dashboard