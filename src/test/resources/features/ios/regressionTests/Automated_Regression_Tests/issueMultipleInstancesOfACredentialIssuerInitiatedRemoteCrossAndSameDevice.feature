@IOS @automated @US_IMIOACIIRCASD @Q2_2025 @REG_GENERAL
Feature: Batch Issuance of Attestations in EUDI Wallet
  As a EUDI Wallet User,
  I want to request the issuance of multiple attestations of the same attestation type, attribute values and technical validity period (batch issuance)
  so that my privacy is protected when presenting attributes from an attestation multiple times to the same Relying Party or colluding Relying Parties (Relying Party linkability)

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/180
  Scenario Outline:Batch Issuance of Attestations in EUDI Wallet
    Given the user initiates a <credential> issuance using the <issuer>
    And the issuance method is <issuance_method>
    And the issuance is performed on a <issue_scenario> for the <credential> and <issuance_method>
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    And the issuer service generates multiple attestations
    And the Wallet shows a counter indicating the total number of attestations issued
    When the issuer advertised maximum batch size is below the Wallet internal minimum threshold
    Then the issuance process continues with the issuer's maximum batch size
    And the Wallet saves the attestations based on the issuer-defined batch size

    Examples:
      | credential     | issuer | issuance_method  | issue_scenario |
      | PID (MSO Mdoc) | Python | from list        | same device    |
      | PID (MSO Mdoc) | Kotlin | from list        | same device    |
      | PID (MSO Mdoc) | Python | credential offer | same device    |
      | PID (MSO Mdoc) | Kotlin | credential offer | same device    |
      | PID (MSO Mdoc) | Python | credential offer | cross device   |
      | PID (MSO Mdoc) | Kotlin | credential offer | cross device   |