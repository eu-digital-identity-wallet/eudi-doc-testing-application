@IOS @automated @US_RIP
Feature: Request/Issue PID

  @US_RIP_TC_01 @before_01
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the dashboard screen

  @US_RIP_TC_02 @before_01
  Scenario: Add document via national ID
    Given the dashboard page is displayed on wallet
    When the user clicks the add doc button
    And the add document page is displayed automated
    And the user clicks the national id button
    Then the authentication method selection is displayed

  @US_RIP_TC_03 @before_01
  Scenario: Select authentication method and enter data
    Given the authentication method selection is displayed on screen
    When the user clicks on country selection and submits
    And the user clicks on Credential Provider FormEU and submits
    Then the provider form is displayed for the user to register personal data

  @US_RIP_TC_04 @before_01
  Scenario: Enter data and display national ID
    Given a provider form is displayed
    When the user fills in the form
    Then a success message for pid is displayed
    And the national id is displayed in the dashboard
