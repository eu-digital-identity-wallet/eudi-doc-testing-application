@IOS @automated @US_VMDL
Feature: View mDL document

  @US_VMDL_TC_01 @before_01
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the dashboard screen

  @US_VMDL_TC_02 @before_01
  Scenario: User opens the mDL document from the dashboard
    Given the user is on the dashboard screen
    When the user clicks on the mDL doc
    Then the mDL should open
    And the user should see the details of the mDL

  @US_VMDL_TC_03 @before_01
  Scenario: User closes the mDL document and returns to the dashboard
    Given the mDL is open
    When the user clicks the X button
    Then the mDL should close
    And the user should see the dashboard screen again