@ANDROID @US_UTPITRI @Q1_2025
Feature: Support the latest version of PID in EUDI Wallet

#https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/167

  Background:
    Given the EUDI Wallet supports the latest version of the PID

  @US_UTPITRI_TC_01 @manual:Passed
  Scenario: Issue PID in wallet-initiated flow @manual:Passed
    Given the user initiates the process to add a document by choosing from the list
    When the user selects PID from the list
    Then the PID is successfully issued in the EUDI Wallet

  @US_UTPITRI_TC_02 @manual:Passed
  Scenario: Issue PID in issuer-initiated flow
    Given the user initiates the process to add a document by scanning a QR code
    When the user scans the QR code provided by the issuer
    Then the PID is successfully issued in the EUDI Wallet

  @US_UTPITRI_TC_03 @manual:Passed
  Scenario: View PID details in 'Documents' screen
    Given the user has a PID issued in the EUDI Wallet
    When the user navigates to the Documents screen
    Then the user can view the PID details
    And the details are aligned to the defined data model as described in Update PID

  @US_UTPITRI_TC_04 @manual:Passed
  Scenario: Present PID in remote scenarios
    Given the user initiates the process to present a document online
    When the user selects the PID for presentation
    Then the PID is successfully presented in the remote scenario

  @US_UTPITRI_TC_05 @manual:Passed
  Scenario: Present PID in proximity scenarios
    Given the user initiates the process to present a document in proximity
    When the user selects the PID for presentation
    Then the PID is successfully presented in the proximity scenario



