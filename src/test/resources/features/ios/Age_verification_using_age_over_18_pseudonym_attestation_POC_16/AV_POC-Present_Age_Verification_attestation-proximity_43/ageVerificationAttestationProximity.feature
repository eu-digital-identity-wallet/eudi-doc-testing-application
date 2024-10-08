@IOS @manual @US_AVAP
Feature: Age verification attestation (proximity)
  As a user of the EUDI Wallet
  I want to present my Age Verification Attestation to a requesting Relying Party operating a ‘reader’ device in a proximity scenario
  So that I can prove that I am over 18 years old without revealing my age or any other personal information

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/43

  @US_AVAP_TC_01 @manual:Passed
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on Login screen
    When the user enters PIN
    Then the user see the dashboard screen

  @US_AVAP_TC_02 @manual:Passed
  Scenario: User shares age verification through QR code scan
    Given the user is on dashboard screen
    And there is an age verification attestation
    When the user clicks on the SHOW QR button
    Then the QR code appears

  @US_AVAP_TC_03 @manual:Passed
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
    Given the user presses the SHARE button
    When the user enters the PIN
    Then a success message appears in the wallet app
    And the verifier views the information shared by the wallet user

