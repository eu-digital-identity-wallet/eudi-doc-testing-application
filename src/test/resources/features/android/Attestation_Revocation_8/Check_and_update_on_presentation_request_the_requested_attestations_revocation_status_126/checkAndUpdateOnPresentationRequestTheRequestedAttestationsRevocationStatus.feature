@ANDROID @manual @US_CAUOPRTRARS
Feature: Check and update on presentation request the requested attestations revocation status
  As an EUDI Wallet,
  I want when I am requested to present/share attestations, to check and update the revocation status of the requested attestations stored in the EUDI Wallet
  so that I can allow the holder to present only valid attestations to the Relying Parties

#https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/126

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user has issued PID attestation to the EUDI Wallet
    And internet is available for connectivity and data transfer capabilities
    And the Issuer includes into VC information for online VC status check

  @US_CAUOPRTRARS_TC_01 @manual
  Scenario: Check revocation status on presentation request
    Given the EUDI Wallet has valid attestations stored
    When the EUDI Wallet is requested to present or share attestations
    Then the EUDI Wallet checks online the revocation status of the requested attestations

  @US_CAUOPRTRARS_TC_02 @manual
  Scenario: Attestation is revoked by the issuer during presentation request
    Given the EUDI Wallet has valid attestations stored
    When the EUDI Wallet checks the revocation status during a presentation request
    And an attestation is found to be revoked by the issuer
    Then the EUDI Wallet makes the attestation unavailable for selection by the holder on the presentation request
    And the EUDI Wallet updates the internal attestation status as revoked
    And the EUDI Wallet raises a notification informing the user the attestation is revoked and cannot be presented anymore to Relying Parties

  @US_CAUOPRTRARS_TC_03 @manual
  Scenario: PID is revoked during presentation request
    Given the EUDI Wallet has a valid PID attestation stored
    When the EUDI Wallet checks the revocation status during a presentation request
    And the PID is found to be revoked by the issuer
    Then the presentation operation is interrupted with a corresponding message informing the user the operation cannot be continued due to PID revocation




