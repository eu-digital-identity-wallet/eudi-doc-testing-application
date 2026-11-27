@IOS @US_RCOFUI @Q4_2026
Feature: Reject credential offers from unconfigured issuers
  As a EUDI Wallet User
  I want the wallet to accept credential offers only from configured issuers for this wallet
  So that credentials are issued only by approved issuers

  #

  @US_RCOFUI_TC_01
  Scenario: Accept offers from configured issuers
    Given the Wallet is unlocked
    And the issuer is listed in the wallet configuration
    When the tries to issue an attestation from a configured issuer
    Then the credential offer is accepted for processing
    And the wallet proceeds with the credential issuance flow
    When the user successfully completes the required issuance steps
    Then the credential is successfully issued
    And the issued credential is available in the wallet

  @US_RCOFUI_TC_02
  Scenario: Reject offers from unconfigured issuers
    Given the Wallet is unlocked
    And the issuing entity is not listed in the wallet configuration
    When the user tries to issue an attestation from the unconfigured issuer
    Then the credential offer is rejected
    And a corresponding error screen is displayed, e.g. 'This offer comes from an issuer that this wallet is not set up to use'
    And the user cannot proceed with credential issuance

  @US_RCOFUI_TC_03
  Scenario: Close the unconfigured issuer error
    Given the wallet has received a credential offer from an unconfigured issuer
    And the unconfigured issuer error screen is displayed
    When the user selects Close
    Then the error screen is closed
    And the rejected credential offer is no longer active
    And no credential is added to the wallet
