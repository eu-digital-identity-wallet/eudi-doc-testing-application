@iOS @manual @US_AVAP
Feature: Age verification attestation (proximity)

  @US_AVAP_TC_01 @manual:Passed
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on the Login screen manually
    When the user enters their PIN manually
    Then the user should see the dashboard screen manually

  @US_AVAP_TC_02 @manual:Passed
  Scenario: User shares age verification through QR code scan
    Given the user is on the dashboard screen manually
    And there is an age verification attestation
    When the user presses the SHOW QR button
    Then the QR code appears

  @US_AVAP_TC_3 @manual:Passed
  Scenario: Verifier scans the QR code for age verification
    Given the QR code is displayed
    When the verifier scans the QR code
    Then the verifier's request appears in the wallet app

  @US_AVAP_TC_04 @manual:Passed
  Scenario: User proceeds with the verifier's request
    Given the verifier's request appears in the wallet app
    When the user chooses to proceed with the request
    And the user presses the SHARE button
    Then the user is prompted to enter the PIN

  @US_AVAP_TC_05 @manual:Passed
  Scenario: User enters the PIN and shares information
    Given the user has pressed the SHARE button manually
    When the user enters the PIN
    Then a success message appears in the wallet app
    And the verifier views the information shared by the wallet user