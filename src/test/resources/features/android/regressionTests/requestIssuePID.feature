@ANDROID @automated @US_RIP
Feature: Add Pid By Choosing From List

  @US_RIP_TC_01 @before_01
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the dashboard screen

  @US_RIP_TC_02 @before_01
  Scenario: Add document via national ID
    Given the home page is displayed on wallet
    When the user clicks the add doc button
    And the add document page is displayed
    And the user clicks the PID button
    Then the credentials provider is displayed

  @US_RIP_TC_03 @before_01
  Scenario: Select authentication method and enter data
    Given the credentials provider is displayed on screen
    When the user clicks on Credential Provider FormEU and submits for pid
    Then the provider form is displayed for the user to register personal data

  @US_RIP_TC_04 @before_01 @release
  Scenario: Enter data and display national ID
    Given a provider form is displayed
    When the user fills in the form
    Then a success message for pid is displayed
    And the national id is displayed in the dashboard

