@IOS @automated @US_ADBSQRC @REG_GENERAL
Feature: Issuing and storing attestations in the EUDI Wallet via QR code
  As a EUDI Wallet User
  I want to issue and store attestations in my EUDI Wallet advertised by issuers
  So that I can prove to the Relying Parties that I own them when I am requested using my EUDI Wallet

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/199

  @US_ADBSQRC_TC_01
  Scenario Outline: Add a document to the EUDI Wallet by scanning a QR code
    Given the user visits the Issuer service, generates a QR code for a PID attestation using <issuer> and returns to the Home screen of the Wallet
    When the user opens the Documents screen
    Then the Documents screen is displayed
    When the user taps the add document button on the Documents screen
    Then the wallet displays the Add document screen
    When the user selects the option to scan a QR code
    Then the wallet opens the scanner to scan the QR code rendered by the Issuer
    When the user scans a valid QR code rendered by the Issuer
    Then the wallet displays the credential offer with the attestation to be issued and the name of the issuer
    When the user selects the Cancel button on the credential offer
    Then the issuing process is canceled
    And the user is returned to the Documents screen
    When the user scans a valid QR code rendered by the Issuer
    And the user selects the Add document button on the credential offer
    Then the user proceeds with the attestation issuing flow
    And the wallet displays a success screen with the details of the issued attestation
    When the user closes the success screen
    Then the attestation is added to the EUDI Wallet
    Examples:
      | issuer |
      | Python |
      | Kotlin |
