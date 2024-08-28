@ANDROID @manual @US_PA
Feature: EUDI Wallet Booking Reservation Presentation
  As a EUDI Wallet User
  I want to present the booking reservation attestations from my EUDI Wallet to the requesting Relying Party (i.e. Hotel check-in desk) in a proximity flow
  So that I can prove my booking reservation at the check-in process by the requesting Relying Party

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/69

  @US_PA_TC_01
  Scenario: Successful Authentication
    Given the EUDI Wallet User opens the EUDI Wallet
    When the user successfully authenticates in the EUDI Wallet
    Then the user views the dashboard screen

  @US_PA_TC_02
  Scenario: Display QR code
    Given the user is on dashboard screen
    When the user clicks on the Show QR button
    Then the QR code is displayed

  @US_PA_TC_03
  Scenario: Inform user of attestation request after QR code scan
    Given the user views the QR code in the wallet app
    When the Relying Party device scans the QR code
    Then EUDI Wallet presents a screen informing the user of the attestation request

  @US_PA_TC_04
  Scenario: Display PIN after credential consent
    Given the user views in the EUDI Wallet the request from the Relying Party
    When the user consents to the credential issuance
    Then a new screen with the six-digit PIN is displayed

  @US_PA_TC_05
  Scenario: Display success message and attestation to Relying Party
    Given the six-digit PIN field is displayed
    When the user enters right the PIN
    Then a success message is displayed on screen
    And the Relying Party views the requested attestation
