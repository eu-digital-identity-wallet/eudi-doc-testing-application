@IOS @manual @US_VAVA
Feature: View age verification attestation
  As a user of the EUDI Wallet
  I want to view an Age Verification Attestation stored in my EUDI Wallet
  So that I can be informed about the contents of the Age Verification Attestation

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/44

  @US_VAVA_TC_01 @manual:Passed
  Scenario: User successfully logs in and sees the dashboard
    Given the user is on Login screen
    When the user enters the PIN
    Then the user see the dashboard screen

  @US_VAVA_TC_02 @manual:Passed
  Scenario: User opens an Age Verification attestation from the dashboard
    Given the user is on dashboard screen
    When the user clicks on the Age Verification
    Then the PID should open manually
    And the user should see the details of the Age Verification

  @US_VAVA_TC_03 @manual:Passed
  Scenario: User closes the Age Verification attestation and returns to the dashboard
    Given the Age Verification is open
    When the user clicks on the X button
    Then the Age Verification should close
    And the user see the dashboard screen
