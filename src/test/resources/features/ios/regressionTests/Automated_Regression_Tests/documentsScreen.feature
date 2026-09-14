@IOS @automated @US_DS @REG_GENERAL
Feature: EUDI Wallet Documents Screen
  As a EUDI Wallet User
  I want to have a 'Documents' screen consolidating my issued attestations
  So that I can easily view and perform actions on the attestations

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/204

  @US_DS_TC_01
  Scenario Outline: Documents screen consolidates and lists issued attestations
    Given the user issues a <credential> attestation using <issuer>
    And the user is on the Home screen of the Wallet
    Then the bottom navigation bar shows the Home, Documents, and History tabs
    When the user opens the Documents screen
    Then the Documents tab becomes highlighted after selection
    And the Documents screen provides a Search bar
    And the Documents screen provides a Filter control
    And the Documents screen lists the issued attestations
    And the listed attestations are organized by category
    And each attestation card shows the attestation name, the issuer, and the validity end date
    When the user taps on an attestation from the list
    Then the attestation details are displayed
    Examples:
      | credential     | issuer |
      | PID (MSO Mdoc) | Python |
      | PID (MSO Mdoc) | Kotlin |
