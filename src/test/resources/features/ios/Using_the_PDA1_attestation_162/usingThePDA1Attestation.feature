@IOS @manual @US_UTPA @Q1_2025
Feature: Support PDA1 Attestation in EUDI Wallet

 #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/162

  Background:
    Given the PDA1 attestation adheres to the existing functionalities of the EUDI Wallet

  @US_UTPA_TC_01
  Scenario: Issue PDA1 attestation in wallet-initiated flow
    Given the user initiates the process to add a document by choosing from the list
    When the user selects PDA1 attestation from the list
    Then the PDA1 attestation is successfully issued in the EUDI Wallet

  @US_UTPA_TC_02
  Scenario: Issue PDA1 attestation in issuer-initiated flow
    Given the user initiates the process to add a document by scanning a QR code
    When the user scans the QR code provided by the issuer
    Then the PDA1 attestation is successfully issued in the EUDI Wallet

  @US_UTPA_TC_03
  Scenario: View PDA1 attestation details in 'Documents' screen
    Given the user has a PDA1 attestation issued in the EUDI Wallet
    When the user navigates to the Documents screen
    Then the user can view the PDA1 attestation details
    And the details are aligned to the defined data model as described in Portable Document A1 (PDA1) attestation

  @US_UTPA_TC_04
  Scenario: Present PDA1 attestation in remote scenarios
    Given the user initiates the process to present a document online
    When the user selects the PDA1 attestation for presentation
    Then the PDA1 attestation is successfully presented in the remote scenario


