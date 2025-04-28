@ANDROID @manual @US_CAUPAARS
Feature: Check and update periodically all attestations revocation status
  As an EUDI Wallet,
  I want periodically to check and update the revocation status of all attestations stored in the EUDI Wallet
  so that I can ensure all attestations stored in the EUDI Wallet are valid and can be presented to the Relying Parties

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/125

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the user has issued PID attestation to the EUDI Wallet
    And internet connectivity is available for connectivity and data transfer capabilities
    And the Issuer has included VC status check information in the attestation

  @US_CAUPAARS_TC_01 @manual:Passed
  Scenario: Revocation status check on wallet open
    Given the EUDI Wallet has valid attestations stored
    When the user opens the EUDI Wallet
    Then the EUDI Wallet checks online the revocation status of all stored valid attestations

  @US_CAUPAARS_TC_02 @manual:Passed
  Scenario: Attestation is revoked by the issuer
    Given the EUDI Wallet has valid attestations stored
    When the EUDI Wallet checks the revocation status
    And an attestation is found to be revoked by the issuer
    Then the EUDI Wallet updates the internal attestation status as revoked
    And the EUDI Wallet raises a notification informing the user the attestation is revoked and cannot be presented anymore to Relying Parties
    And the EUDI Wallet displays a visual indication on the attestation to inform the user this attestation is revoked

  @US_CAUPAARS_TC_03 @manual:Passed
  Scenario: Visual indication of revoked attestation in Documents screen
    Given an attestation in the EUDI Wallet has been marked as revoked
    When the user views the Documents screen
    Then the revoked attestation should have a clear visual indication of its revoked status

  @US_CAUPAARS_TC_04 @manual:Passed
  Scenario: Visual indication of revoked attestation in View document screen
    Given an attestation in the EUDI Wallet has been marked as revoked
    When the user selects to view the details of the revoked attestation
    Then the View document screen should display a clear visual indication of the attestation's revoked status

  @US_CAUPAARS_TC_05 @manual:Passed
  Scenario: Handling revoked attestation in Filter and Sort
    Given the EUDI Wallet contains both valid and revoked attestations
    When the user applies filters or sorting on the Documents screen
    Then the revoked attestations should be handled correctly according to the filter and sort criteria




