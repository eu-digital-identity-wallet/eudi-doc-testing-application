@ANDROID @manual @US_UTEA @Q1_2025
Feature: Support EHIC Attestation in EUDI Wallet

 #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/163

  Background:
    Given the EHIC attestation adheres to the existing functionalities of the EUDI Wallet

 @US_UTEA_TC_01 @manual:Passed
  Scenario: Issue EHIC attestation in wallet-initiated flow
    Given the user initiates the process to add a document by choosing from the list
    When the user selects EHIC attestation from the list
    Then the EHIC attestation is successfully issued in the EUDI Wallet

  @US_UTEA_TC_02 @manual:Passed
  Scenario: Issue EHIC attestation in issuer-initiated flow
    Given the user initiates the process to add a document by scanning a QR code
    When the user scans the QR code provided by the issuer
    Then the EHIC attestation is successfully issued in the EUDI Wallet

  @US_UTEA_TC_03 @manual:Passed
  Scenario: View EHIC attestation details in Documents screen
    Given the user has an EHIC attestation issued in the EUDI Wallet
    When the user navigates to the Documents screen
    Then the user can view the EHIC attestation details
    And the details are aligned to the defined data model as described in European Health Insurance Card (EHIC) attestation

  @US_UTEA_TC_04 @manual:Passed
  Scenario: Present EHIC attestation in remote scenarios
    Given the user initiates the process to present a document online
    When the user selects the EHIC attestation for presentation
    Then the EHIC attestation is successfully presented in the remote scenario


