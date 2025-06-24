@IOS @US_IMIOACIIRCASD @Q2_2025
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

  @US_IMIOACIIRCASD_TC_01 @manual:Passed
  Scenario: User visits issuer service on a different device
    Given the user visits the issuer service on a different device
    When the user requests the issuance of an attestation type
    Then the issuer service renders a QR code

  @US_IMIOACIIRCASD_TC_02 @manual:Passed
  Scenario: User opens Wallet and authenticates successfully
    Given the user opens the EUDI Wallet
    When the user authenticates using a six-digit PIN or Biometrics
    Then the authentication is successful

  @US_IMIOACIIRCASD_TC_03 @manual:Passed
  Scenario: Wallet scans QR code and receives attestation offer
    Given the user scans the QR code with the Wallet device
    When the QR code is valid
    Then the EUDI Wallet presents the attestation

  @US_IMIOACIIRCASD_TC_04 @manual:Passed
  Scenario: Wallet requests confirmation to proceed
    Given the EUDI Wallet presents the attestation
    When the user views the attestation
    Then the Wallet requests the user to confirm to proceed

  @US_IMIOACIIRCASD_TC_05 @manual:Passed
  Scenario: User visits issuer on same device
    Given the user visits the issuer service on the same device
    When the user requests the issuance of an attestation type
    Then the issuer service redirects the user to the Wallet

  @US_IMIOACIIRCASD_TC_06 @manual:Passed
  Scenario: User authenticates successfully in Wallet on same device
    Given the EUDI Wallet opens
    When the user authenticates using a six-digit PIN or Biometrics
    Then the authentication is successful

  @US_IMIOACIIRCASD_TC_07 @manual:Passed
  Scenario: Wallet receives offer and prompts confirmation
    Given the authentication is successful
    When the Wallet receives the attestation from the issuer service
    Then issuer service issues multiple attestations
    And the Wallet displays a counter showing the number of attestations issued

  @US_IMIOACIIRCASD_TC_08 @manual:Passed
  Scenario: Attestations stored based on issuer's smaller batch size
    Given the issuer service issued multiple attestations
    When the maximum batch size advertised by the issuer is lower than the EUDI Wallet's internal minimum
    Then the issuance flow proceeds using the issuer's maximum batch size
    And the Wallet stores the attestations according to the issuer's batch size

