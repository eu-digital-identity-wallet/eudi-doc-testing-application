@ANDROID @automated @US_PDO @GENERAL_TESTS
Feature: EUDI Wallet Online Document Presentation
  As a EUDI Wallet User
  I want to be able to present attestations from my EUDI Wallet
  So that I can authenticate, authorise transactions and present personal information upon requests of a Relying Party in remote scenarios

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/201

  @US_PDO_TC_01
  Scenario Outline: User can cancel and then complete an online data sharing presentation
    Given the user issues a <credential> attestation using <issuer>
    And the user is on the Home screen of the Wallet
    And a Relying Party renders a presentation request as a QR code
    When the user opens the Online option from the Authenticate section
    And the Wallet scans the Relying Party's presentation request QR code
    Then the Wallet displays the attestation details requested for sharing
    When the user cancels the presentation request
    Then the user returns to the Wallet Home screen
    And a Relying Party renders a presentation request as a QR code
    When the user opens the Online option from the Authenticate section
    And the Wallet scans the Relying Party's presentation request QR code
    Then the Wallet displays the attestation details requested for sharing
    When the user selects the Share button
    And the user enters their six-digit PIN correctly
    Then a success message is displayed for the successful presentation of the PID
    When the user closes the success screen
    Then the user returns to the Wallet Home screen
    Examples:
      | credential     | issuer |
      | PID (MSO Mdoc) | Python |
      | PID (MSO Mdoc) | Kotlin |
