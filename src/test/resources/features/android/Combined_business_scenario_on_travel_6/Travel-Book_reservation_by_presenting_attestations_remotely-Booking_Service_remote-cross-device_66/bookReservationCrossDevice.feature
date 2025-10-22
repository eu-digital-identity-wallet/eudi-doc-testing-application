@ANDROID @manual @US_BRCD
Feature: Booking Service Reservation with EUDI Wallet (cross device)
  As a EUDI Wallet User
  I want to book my reservation by present attestations from my EUDI Wallet to the requesting Relying Party (i.e. ‘Booking Service’)
  So that I can be authenticated by the Relying Party and book my reservation as part of my travel arrangements

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/66

  @US_BRCD_TC_01 @manual:Passed
  Scenario: User fills in the booking reservation form and presents identity attestations
    Given the user visits the Booking Service on their desktop PC
    And the Booking Service presents a booking reservation form
    When the user fills in the booking reservation form fields
    Then the user selects to present the attestations with the EUDI Wallet

  @US_BRCD_TC_02 @manual:Passed
  Scenario: User selects to present attestations with the EUDI Wallet and scans QR-code
    Given the user has chosen to display the attestations using the EUDI Wallet
    When the Booking Service displays a QR-code for the user to scan
    And the user logs in successfully in the wallet app
    Then the dashboard screen is displayed

  @US_BRCD_TC_03 @manual:Passed
  Scenario: Open camera for QR scan
    Given the user views the dashboard screen
    When the user clicks on the options button
    And the user taps on the Scan a QR code button
    Then the phone camera opens

  @US_BRCD_TC_04 @manual:Passed
  Scenario: Display booking request after QR scan
    Given the phone camera has opened
    When the user scans the QR code
    Then the request of the Booking service is displayed

  @US_BRCD_TC_05 @manual:Passed
  Scenario: Consent and PIN for booking service request
    Given the user is presented with the request from the Booking service
    When the user consents to the release of the multiple requested attestations
    Then a screen with the PIN is displayed
    And the user enters the PIN

  @US_BRCD_TC_06 @manual:Passed
  Scenario: Show reservation preview
    Given the user has been successfully authenticated in the wallet
    When the Booking Service successfully verifies the attestation
    And a success message is displayed in the wallet
    Then it presents the preview of the reservation in the Booking Service

  @US_BRCD_TC_07 @manual:Passed
  Scenario Outline: Booking Service offers additional options to the user
    Given the user receives the reservation preview
    When the reservation includes booking service related fields
    And the Booking Service offers the following <options> to the user
    Examples:
      | options                                                                               |
      | Download the car rental contract as pdf pre-filled with the corresponding form fields |
      | Proceed to the car rental contract signing                                            |
      | Issue the reservation confirmation attestation                                        |
