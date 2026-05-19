@ANDROID @US_RIAWIOUR
Feature: User Authentication and Re-Issuance of PIDs/Attestations
  As an EUDI User,
  I want to re-issue a PID/attestation from the original issuer by replacing an existing PID or attestation
  so that fresh attestations are available in my EUDI Wallet.

  # https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/225

  @US_RIAWIOUR_TC_01
  Scenario: Successful authentication
    Given the user opens the Wallet application
    When the user authenticates successfully using a six digit PIN
    Then the Wallet grants access to the user

  @US_RIAWIOUR_TC_02
  Scenario: Unsuccessful authentication
    Given the user opens the Wallet application
    When the user fails to authenticate using the six digit PIN
    Then the Wallet presents an error message
    And the user can retry the authentication

  @US_RIAWIOUR_TC_03
  Scenario: User initiates re-issuance from an existing PID or attestation
    Given the user is authenticated in the Wallet
    And an existing PID or attestation is stored in the Wallet
    When the user selects the re-issuance option from the Issuer details
    Then the wallet requests re-issuance from the issuer

  @US_RIAWIOUR_TC_04
  Scenario: Wallet requests re-issuance to the same issuer for same document type
    Given the user requested re-issuance for an existing PID or attestation
    When the wallet sends the re-issuance request
    Then the request is sent to the same issuer that issued the original PID or attestation
    And the request is for the same PID or attestation type

  @US_RIAWIOUR_TC_05
  Scenario: Existing PID or attestation is replaced by the re-issued one
    Given an existing PID or attestation is already stored in the Wallet
    When the Wallet receives the re-issued PID or attestation of the same type
    Then the existing PID or attestation is replaced

  @US_RIAWIOUR_TC_06
  Scenario: Re-issuance in batch when the original issuance was in batch
    Given the original PID or attestation was issued in batch
    When the issuer re-issues the PID or attestation
    Then the PID or attestation is re-issued in batch
