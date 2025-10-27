@ANDROID @manual @US_AVACD
Feature: Age verification attestation cross device
  As a user of the EUDI Wallet
  I want to present my Age Verification Attestation to a requesting Relying Party on a different device
  So that I can prove that I am over 18 years old without revealing my age or any other personal information

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/42

  @US_AVACD_TC_01 @manual:Passed
  Scenario: Display QR code on Relying Party service
    Given the user visits the Relying Party service on a different device from the one where the wallet app is installed
    When the Relying Party service displays a QR code
    Then the user views the QR code

  @US_AVACD_TC_02 @manual:Passed
  Scenario: User scans the QR code
    Given the user views the QR code
    When the user clicks on the options button
    And the user clicks on the scan a qr code button
    Then camera opens and the user scans the QR code

  @US_AVACD_TC_03 @manual:Passed
  Scenario: Inform user about age verification request
    Given the user scans the QR code
    When the wallet app displays a screen informing the user about the age verification request
    Then the user reads the age verification request

  @US_AVACD_TC_04 @manual:Passed
  Scenario: User attempts to share age verification attestation
    Given the user reads the age verification request
    When the user taps the share button in the wallet app
    Then the wallet app prompts the user to enter the PIN

  @US_AVACD_TC_05 @manual:Passed
  Scenario: User enters incorrect PIN
    Given the wallet app prompts the user to enter the PIN
    When the user enters the incorrect PIN
    Then the wallet app displays a corresponding error message

  @US_AVACD_TC_06 @manual:Passed
  Scenario: User re-enters correct PIN
    Given the wallet app displays a corresponding error message
    When the user re-enters the correct PIN
    Then the wallet app displays on screen a success message

