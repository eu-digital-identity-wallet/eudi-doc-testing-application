@ANDROID @manual @US_BRCD
Feature: Booking Service Reservation with EUDI Wallet (cross device)
  As a EUDI Wallet User
  I want to book my reservation by present attestations from my EUDI Wallet to the requesting Relying Party (i.e. ‘Booking Service’)
  So that I can be authenticated by the Relying Party and book my reservation as part of my travel arrangements

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/66

  @US_BRCD_TC_01
  Scenario: User fills in the booking reservation form and presents identity attestations
    Given the user visits the Booking Service on their desktop PC
    And the Booking Service presents a booking reservation form
    When the user fills in the booking reservation form fields
    Then it requests the user to present the needed identity attestations

  @US_BRCD_TC_02
  Scenario: User selects to present attestations with the EUDI Wallet and scans QR-code
    Given the user is requested to present identity attestations
    When the user selects to present the attestations with the EUDI Wallet
    And clicks the corresponding button in the Booking Service
    Then the Booking Service displays a QR-code for the user to scan

  @US_BRCD_TC_03
  Scenario: User authenticates successfully in the EUDI Wallet
    Given the Booking Service displays a QR-code
    When the user initiates the wallet app
    And the user logs in successfully
    Then the dashboard screen is displayed

  @US_BRCD_TC_04
  Scenario: Open camera for QR scan
    Given the user views the dashboard screen
    When the user clicks on the options button
    And the user taps on the Scan a QR code button
    Then the phone camera opens

  @US_BRCD_TC_05
  Scenario: Display booking request after QR scan
    Given the phone camera has opened
    When the user scans the QR code
    Then the request of the Booking service is displayed

  @US_BRCD_TC_06
  Scenario: Consent and PIN for booking service request
    Given the user is presented with the request from the Booking service
    When the user selects to consent of the release of the requested attestation
    Then a screen with the PIN is displayed
    And the user enters the PIN

  @US_BRCD_TC_07
  Scenario: Show reservation preview
    Given the user has been successfully authenticated in the wallet
    When the Booking Service successfully verifies the attestation
    And a success message is displayed in the wallet
    Then it presents the preview of the reservation

  @US_BRCD_TC_08
  Scenario: Booking Service offers additional options to the user
    Given the user receives the reservation preview
    When the reservation includes booking service related fields
    Then the Booking Service offers the following options to the user:
      | Download the car rental contract as pdf pre-filled with the corresponding form fields |
      | Proceed to the car rental contract signing                                           |
      | Issue the reservation confirmation attestation                                       |
