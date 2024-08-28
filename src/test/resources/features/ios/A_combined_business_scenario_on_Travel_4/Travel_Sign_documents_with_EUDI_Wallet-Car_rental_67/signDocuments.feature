@IOS @manual @US_SD
Feature: Sign car rental contract using EUDI Wallet
  As a EUDI Wallet User
  I want to be able to sign the car rental contract utilizing my EUDI Wallet
  So that I can easily complete a digital signing process remotely

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/67

  @US_SD_TC_01
  Scenario: Successful authentication and presentation of attestation
    Given the user visits the QTSP service
    When the user selects Digital ID Authentication option
    Then the QTSP sservice presents a QR code

  @US_SD_TC_02
  Scenario: User authenticates and scans QR code
    Given the QTSP service presents a QR code
    When the user initiates the wallet app
    Then the user views the dashboard screen

  @US_SD_TC_03
  Scenario: User scans the QR code
    Given the user is on dashboard screen
    When the user clicks on the options button
    And the user clicks on the scan a qr code button
    Then camera opens and the user scans the QR code

  @US_SD_TC_04
  Scenario: Successful attestation release
    Given the user scans the QR code
    When the Wallet presents a screen requesting to release the matching attestation
    Then the Wallet checks for available attestations to match the request

  @US_SD_TC_05
  Scenario: User consents to release attestation
    Given the Wallet checked for available attestations to match the request
    When the user consents to release the requested attestation by authenticating with a six-digit PIN
    Then the Wallet presents the requested attestation to the QTSP

  @US_SD_TC_06
  Scenario: QTSP service verifies attestation
    Given the Wallet presented the requested attestation to the QTSP
    When the QTSP service verifies the attestation successfully
    Then the QTSP service navigates the user to the upload page
