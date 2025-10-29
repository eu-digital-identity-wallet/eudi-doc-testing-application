@ANDROID @US_IMIOACIIRCASD @Q2_2025
Feature: Batch Issuance of Attestations in EUDI Wallet
  As a EUDI Wallet User,
  I want to request the issuance of multiple attestations of the same attestation type, attribute values and technical validity period (batch issuance)
  so that my privacy is protected when presenting attributes from an attestation multiple times to the same Relying Party or colluding Relying Parties (Relying Party linkability)

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/172

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the Issuer supports batch issuance with batch size greater than one
    And the EUDI Wallet has an internal minimum number of attestations expected
    And there is an active internet connection

  @US_IMIOACIIRCASD_TC_01 @before_01
  Scenario: User visits issuer on same device
    Given the user visits the issuer service on the same device
    When the user requests the issuance of an attestation type
    Then the issuer service redirects the user to the Wallet

  @US_IMIOACIIRCASD_TC_02 @before_01
  Scenario: Wallet receives offer and prompts confirmation
    Given the authentication is successful and continue
    When the Wallet receives the attestation from the issuer service
    Then issuer service issues multiple attestations
    And the Wallet displays a counter showing the number of attestations issued


