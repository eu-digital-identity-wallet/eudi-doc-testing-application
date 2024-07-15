@ANDROID @manual @US_VAVA
Feature: View age verification attestation

  @US_VAVA_TC_01
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen manually
    When the user enters their PIN
    Then the user should see the dashboard screen

  @US_VAVA_TC_02
  Scenario: User opens an Age Verification attestation from the dashboard
    Given the user is on the dashboard screen manually
    When the user clicks on the Age Verification
    Then the PID should open
    And the user should see the details of the Age Verification

  @US_VAVA_TC_03
  Scenario: User closes the Age Verification attestation and returns to the dashboard
    Given the Age Verification is open
    When the user clicks the X button manually
    Then the Age Verification should close
    And the user should see the dashboard screen again
