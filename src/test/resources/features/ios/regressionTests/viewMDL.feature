@IOS @automated @US_VMDL
Feature: View mDL document

  @US_VMDL_TC_01 @before_01
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen
    When the user enters their PIN
    Then the user should see the home screen

  @US_VMDL_TC_02 @before_03
  Scenario: User opens the mDL document from the dashboard
    Given the user is on the home screen
    When the user clicks on the mDL doc
    Then the mDL should open
    And the details should be blurred by default auto
    And the user should see the eye icon to view the details of the attestation auto

  @US_VP_TC_03 @before_03
  Scenario: Viewing attestation details after selecting the 'eye' icon
    Given the user is viewing the details of the mDL
    When the user selects eye icon auto
    Then the attestation details should no longer be blurred auto

  @US_VMDL_TC_04 @before_03
  Scenario: User closes the mDL document and returns to the dashboard
    Given the mDL is open
    When the user clicks the back button
    Then the mDL should close
    And the user should see the dashboard screen again