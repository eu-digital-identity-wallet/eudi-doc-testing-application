@ANDROID @automated @US_VP
Feature: View PID document

  @US_VP_TC_01 @before_01
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen
#    When the user enters their PIN
#    Then the user should see the dashboard screen

  @US_VP_TC_02 @before_01
  Scenario: User opens the PID document from the dashboard
    Given the user is on Home page
    When the user clicks on Documents
    And the user clicks on the PID doc
    Then the PID should open
    And the details should be blurred by default auto
    And the user should see the eye icon to view the details of the attestation auto

  @US_VP_TC_03 @before_01
  Scenario: Viewing attestation details after selecting the 'eye' icon
    Given the user is viewing the details of an attestation auto
    When the user selects eye icon auto
    Then the attestation details should no longer be blurred auto
   # And the user should be able to view the full details of the attestation

  @US_VP_TC_04 @before_01
  Scenario: User closes the PID document and returns to the dashboard
    Given the PID is open
    When the user clicks the back button
    Then the PID should close
    And the user should see the dashboard screen again
