@IOS @manual @US_VD
Feature: Dashboard display

  @US_VD_TC_01 @data
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the dashboard screen

  @US_VD_TC_02 @data
  Scenario: User views issued documents on the dashboard
    Given the user is on the dashboard screen
    Then the user should see the documents they have issued so far
