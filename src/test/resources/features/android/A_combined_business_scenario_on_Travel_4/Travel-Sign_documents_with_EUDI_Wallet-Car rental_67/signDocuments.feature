@ANDROID @manual @US_SD
Feature: Sign car rental contract using EUDI Wallet
  As a EUDI Wallet User
  I want to be able to sign the car rental contract utilizing my EUDI Wallet
  So that I can easily complete a digital signing process remotely

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/67

  @US_SD_TC_01 @manual:Passed
  Scenario: Successful authentication and presentation of attestation
    Given the user visits the QTSP service
    When the user selects PID Authentication option
    Then the QTSP service presents a QR code

  @US_SD_TC_02 @manual:Passed
  Scenario: User authenticates and scans QR code
    Given the QR code is presented on QTSP service
    When the user initiates the wallet app
    Then the user views the dashboard screen

  @US_SD_TC_03 @manual:Passed
  Scenario: User scans the QR code
    Given the user is on dashboard screen
    When the user clicks on the options button
    And the user clicks on the scan a qr code button
    Then camera opens and the user scans the QR code

  @US_SD_TC_04 @manual:Passed
  Scenario: Successful attestation release
    Given the user scans the QR code
    When the EUDI Wallet displays the presentation request
    Then the user is prompted to consent by selecting the Share button

  @US_SD_TC_05 @manual:Passed
  Scenario: User Consents to Share the desired document
    Given the user has been prompted to consent by selecting the Share button
    When the user selects the Share button
    Then the user is prompted to enter their six-digit PIN

  @US_SD_TC_06 @manual:Passed
  Scenario: Successful PID Presentation
    Given the user has been prompted to enter their six-digit PIN
    When the user enters their six-digit PIN correctly
    Then a success message is displayed for the successful presentation
    And the Wallet presents the requested attestation to the QTSP

  @US_SD_TC_07 @manual:Passed
  Scenario: QTSP service verifies attestation
    Given the requested attestation to the QTSP has presented by the Wallet
    When the user uploads the electronic document
    And the user selects the option to sign the document
    Then QTSP service request the user to present an identity attestation to proceed

  @US_SD_TC_08 @manual:Passed
  Scenario: Document signing process
    Given the QTSP service has requested the user to present an identity attestation
    When the user repeats the steps three to six
    Then the document has been signed
    And the user can download the signed document




