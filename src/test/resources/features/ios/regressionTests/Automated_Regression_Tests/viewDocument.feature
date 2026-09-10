@IOS @automated @US_VD @REG_GENERAL
Feature: View, bookmark and manage attestations in the EUDI Wallet

  As a EUDI Wallet User
  I want to be able to view my attestations issued in the EUDI Wallet
  So that I can review the details of my issued attestation

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/198

  @US_VD_TC_01
  Scenario Outline: View, bookmark, inspect and delete an attestation through the EUDI Wallet
    Given the user issues a <credential> attestation using <issuer>
    And the user is on the Home screen of the Wallet
    When the user opens the Documents screen
    Then the Documents screen lists the issued attestations
    When the user taps on an attestation from the list
    Then the attestation details are displayed
    And the attestation details are blurred by default
    And an eye icon is shown to reveal the attestation details
    When the user taps on the eye icon
    Then the attestation details are no longer blurred
    And the user can view the full attestation details
    When the user taps the bookmark icon on the attestation
    Then the attestation gets marked as bookmarked
    And the bookmark icon updates to indicate the bookmarked state
    When the user opens the issuer details from the attestation
    Then the issuer details are displayed to the user
    When the user closes the attestation details without deleting it
    Then the user lands back on the Documents screen
    When the user reopens the attestation and selects the delete option
    Then the attestation gets removed from the EUDI Wallet
    And the Documents screen no longer lists the deleted attestation
    Examples:
      | credential     | issuer |
      | PID (MSO Mdoc) | Python |
      | PID (MSO Mdoc) | Kotlin |
