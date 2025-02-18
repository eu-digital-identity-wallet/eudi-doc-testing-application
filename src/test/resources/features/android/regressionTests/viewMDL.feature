@ANDROID @automated @US_VMDL
Feature: View mDL document

  @US_VMDL_TC_01 @before_01
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen test
    When the user enters their PIN test
    Then the user should see the dashboard screen test

  @US_VMDL_TC_02 @before_03
  Scenario: User opens the mDL document from the dashboard
    Given the user is on the dashboard screen test
    When the user clicks on the mDL doc test
    Then the mDL should open test
    And the user should see the details of the mDL test

  @US_VMDL_TC_03 @before_03
  Scenario: User closes the mDL document and returns to the dashboard
    Given the mDL is open
    When the user clicks the back button
    Then the mDL should close
    And the user should see the dashboard screen again