@IOS @manual @US_RPAAI
Feature: Revoke PID/Attestation as Issuer
  As a PID or attestation issuer
  I want to revoke a PID or attestation issued by me (to an EUDI Wallet instance holder)
  So that the PID or attestation cannot be misused.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/128

  Background:
    Given the issuer maintains an Attestation status list or Attestation revocation list
    And the issuer's Attestation status list or Attestation revocation list is publicly available
    And the PID or Attestation issuer includes revocation information in the issued PID or attestations
    And the revocation information includes a URL indicating the location where the status list or revocation list can be obtained, and an identifier or index for this specific certificate or attestation within that list
    And the issuer can revoke only its own issued PID or attestations

  @US_RPAAI_TC_01 @manual
  Scenario: Issuer decides to revoke a PID
    Given the issuer has issued a PID to an EUDI Wallet instance holder
    When the issuer decides to revoke the PID according to ARF Annex 2 VCR_03, VCR_04 and VCR_07
    Then the issuer updates the index in the Attestation status list or Attestation revocation list that corresponds to the PID to be revoked
    And the PID cannot be misused

  @US_RPAAI_TC_02 @manual
  Scenario: Issuer decides to revoke an attestation
    Given the issuer has issued an attestation to an EUDI Wallet instance holder
    When the issuer decides to revoke the attestation according to ARF Annex 2 VCR_03, VCR_04 and VCR_07
    Then the issuer updates the index in the Attestation status list or Attestation revocation list that corresponds to the attestation to be revoked
    And the attestation cannot be misused

  @US_RPAAI_TC_03 @manual
  Scenario: Issuer updates the Attestation status list or Attestation revocation list
    Given the issuer has decided to revoke a PID or attestation
    When the issuer accesses the provided user interface
    And the issuer updates the index in the Attestation status list or Attestation revocation list that corresponds to the PID or attestation to be revoked
    Then the PID or attestation is marked as revoked in the list
    And the revocation is publicly available

  @US_RPAAI_TC_04 @manual
  Scenario: Revoked PID or attestation cannot be reversed
    Given the issuer has revoked a PID or attestation
    When the issuer attempts to reverse the revocation
    Then the system prevents the issuer from reversing the revocation
    And the PID or attestation remains revoked

