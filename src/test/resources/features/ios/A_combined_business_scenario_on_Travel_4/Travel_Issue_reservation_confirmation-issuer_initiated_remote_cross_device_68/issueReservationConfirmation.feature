@IOS @manual @US_IRC
Feature: Issuing reservation confirmation to EUDI Wallet
  As a EUDI Wallet User
  I want the booking service to issue a confirmation of my reservation and store it in my EUDI Wallet
  So that I can check-in easily to the hotel using my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/68

  @US_IRC_TC_01
  Scenario: User selects to issue a reservation confirmation attestation
    Given the booking service offers the user the option to issue a reservation confirmation attestation to the EUDI Wallet
    When the user selects the option to issue the reservation confirmation attestation
    Then the booking service renders a QR code

  @US_IRC_TC_02
  Scenario: Successful authentication in EUDI Wallet
    Given the booking service presents the QR code
    When the user opens the EUDI Wallet
    Then the dashboard screen is displayed

  @US_IRC_TC_03
  Scenario: Display options modal
    Given the user views the dashboard screen
    When the user clicks on the options button
    Then a modal is displayed

  @US_IRC_TC_04
  Scenario: Open camera for QR code scanning
    Given the user views the modal
    When the user clicks on the Scan a QR code option
    Then the phone camera opens

  @US_IRC_TC_05
  Scenario: Inform user of attestation offer after QR code scan
    Given the phone camera is opened
    When the user scans the QR code
    Then the EUDI Wallet informs the user that the Issuer offered to issue an attestation

  @US_IRC_TC_06
  Scenario: Issuer sends credential to Wallet
    Given the EUDI Wallet informed the user that the Issuer offered to issue an attestation
    When the user consents to the credential issuance
    Then the booking service issues and sends the credential to the Wallet
    And a success message is displayed in the wallet

  @US_IRC_TC_07
  Scenario: Confirming Credential Addition
    Given the user views the success message
    When the user clicks on the OK button
    Then the credential is added to the EUDI Wallet
