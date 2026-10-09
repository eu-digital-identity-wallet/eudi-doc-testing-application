@ANDROID @manual @US_HPR
Feature: Handle PID revocation (EUDI Wallet)
  As an EUDI Wallet Instance
  I want to reset my state on PID revocation
  so that I align my state to the ARF 4.3.3 specification

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/127

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user has issued PID attestation to the EUDI Wallet
    And internet is available for connectivity and data transfer capabilities

  @US_HPR_TC_01 @manual
  Scenario: Detect PID revocation during periodic revocation check
    Given the EUDI Wallet performs periodic revocation checks
    When the EUDI Wallet detects that the PID is revoked
    Then the EUDI Wallet interrupts any ongoing operation with a message informing the user that the operation cannot be continued due to PID revocation
    And the EUDI Wallet downgrades its state back to Operational as specified in ARF 4.3.3
    And the Operational state allows the holder to issue attestations but disallows the holder to present or share attestations or sign documents
    And the EUDI Wallet requires the holder to re-issue a valid PID to access the EUDI Wallet

  @US_HPR_TC_02 @manual
  Scenario: Detect PID revocation during presentation request
    Given the EUDI Wallet performs revocation checks on presentation request
    When the EUDI Wallet detects that the PID is revoked
    Then the EUDI Wallet interrupts any ongoing operation with a message informing the user that the operation cannot be continued due to PID revocation
    And the EUDI Wallet downgrades its state back to Operational as specified in ARF 4.3.3
    And the Operational state allows the holder to issue attestations but disallows the holder to present or share attestations or sign documents
    And the EUDI Wallet requires the holder to re-issue a valid PID to access the EUDI Wallet

  @US_HPR_TC_03 @manual
  Scenario: PID revocation interrupts ongoing operation
    Given the EUDI Wallet is performing an operation
    When the EUDI Wallet detects that the PID is revoked
    Then the EUDI Wallet interrupts the ongoing operation with a message informing the user that the operation cannot be continued due to PID revocation
    And the EUDI Wallet downgrades its state back to Operational as specified in ARF 4.3.3
    And the Operational state allows the holder to issue attestations but disallows the holder to present or share attestations or sign documents
    And the EUDI Wallet requires the holder to re-issue a valid PID to access the EUDI Wallet

  @US_HPR_TC_04 @manual
  Scenario: EUDI Wallet requires re-issuance of PID after revocation
    Given the PID is revoked and the EUDI Wallet is in Operational state
    When the user attempts to access the EUDI Wallet
    Then the EUDI Wallet requires the holder to re-issue a valid PID to access the EUDI Wallet

