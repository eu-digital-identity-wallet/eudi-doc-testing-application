@IOS @automated @US_VP
Feature: View PID document

  @US_VP_TC_01 @before_01
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the dashboard screen

  @US_VP_TC_02 @before_01
  Scenario: User opens the PID document from the dashboard
    Given the user is on the dashboard screen
    When the user clicks on the PID doc
    Then the PID should open
    And the user should see the details of the PID

  @US_VP_TC_03 @before_01
  Scenario: User closes the PID document and returns to the dashboard
    Given the PID is open
    When the user clicks the X button
    Then the PID should close
    And the user should see the dashboard screen again

