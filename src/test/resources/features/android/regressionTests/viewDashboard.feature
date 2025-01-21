@ANDROID @automated @US_VD
Feature: Dashboard display

  @US_VD_TC_01 @before_01
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the dashboard screen

  @US_VD_TC_02 @before_01
  Scenario: User views issued documents on the dashboard
    Given the user is on the dashboard screen
    Then the user should see the documents they have issued so far

  @US_VD_TC_03 @before_01
    Scenario: User views add doc button
    Given the user is on the dashboard screen
    Then the user should click the add doc button