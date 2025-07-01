@ANDROID @US_RIAWIA @Q2_2025
Feature:Automatic Re-Issuance of PIDs or Attestations
  As an EUDI Wallet,
  I want to automatically re-issue a PID or attestation from the original issuer by replacing an existing PID or attestation when its technical validity is about to expire or when the number of unused attestations of the same document type and issuer falls below a defined threshold,
  so that a sufficient number of valid attestations are available in the EUDI Wallet.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/161

  @US_RIAWIA_TC_01
  Scenario: Trigger re-issuance due to expiration in batch
    Given the wallet has a valid refresh token
    And the existing PID or attestations are part of a batch group
    And the technical validity of one of the batched PID or attestations is about to expire within the internal minimum expiration limit
    When the wallet detects the upcoming expiration
    Then the wallet automatically requests the issuer to re-issue the same PID or attestation

  @US_RIAWIA_TC_02
  Scenario: Trigger re-issuance due to unused attestations threshold
    Given the wallet has a valid refresh token
    And the number of unused PID or attestation instances has reached the internal lower limit
    When the wallet detects the threshold has been reached
    Then the wallet automatically requests the issuer to re-issue the same PID or attestation

  @US_RIAWIA_TC_03
  Scenario: Wallet requests new PID/attestation from issuer
    Given the wallet has triggered a re-issuance of a PID or attestation
    When the wallet requests the same issuer to issue the same PID or attestations document type to the wallet unit
    Then the wallet receives the new PID or attestation as single or batch, depending on the issuer's capabilities

  @US_RIAWIA_TC_04
  Scenario: Handling re-issuance refusal by issuer
    Given the wallet has requested a re-issuance from the issuer
    When the issuer refuses the re-issuance
    Then the wallet attempts a retry after an appropriate delay
    And the wallet logs the refusal and retry attempt

  @US_RIAWIA_TC_05
  Scenario: Compare and notify on attribute differences
    Given the wallet has received re-issued attestations
    When the wallet compares the re-issued attestations' attribute values to the attestations to be replaced
    And there are differences
    Then the wallet notifies the user about the differences
    And the wallet logs the notification

  @US_RIAWIA_TC_06
  Scenario: Delete replaced PIDs/Attestations
    Given the wallet has received and stored the new PID or attestation
    When the wallet confirms the successful issuance
    Then the wallet deletes all existing PIDs or attestations from the same issuer and document type
    And the wallet logs the transaction
