@IOS @US_PAIOACRPIRSD @Q2_2025 @REG_GENERAL
Feature: Presentation of Batch Issued Attestations in EUDI Wallet
  As an EUDI Wallet User,,
  I want to present attributes of a batch issued attestation to a requestor Relying Party residing on the same device as my EUDI Wallet
  so that my privacy is protected when presenting attributes from an attestation multiple times to the same Relying Party or colluding Relying Parties (Relying Party linkability)

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/185

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the EUDI Wallet and the Relying Party User Interface reside on the same device
    And there is an active internet connection

  @US_PAIOACRPIRSD_TC_01
  Scenario Outline: Relying Party initiated presentation flow
    Given the user initiates a <credential> issuance using the <issuer>
    And the issuance method is <issuance_method>
    And the issuance is performed on a <issue_scenario> for the <credential> and <issuance_method>
    When the issuance flow is completed
    Then the credential is stored in the Wallet
    And the user accesses the Relying Party service through their mobile device using <verifier>
    When the user chooses to present an attestation type
    Then the Relying Party service navigates the user to the EUDI Wallet
    When the user is unable to authenticate using a six-digit PIN or Biometrics
    Then the Wallet displays an authentication error
    And the user is allowed to attempt the authentication again
    When the user verifies their identity using a six-digit PIN or Biometrics
    And the authentication is completed successfully
    Then the EUDI Wallet notifies the user that the Relying Party is requesting an attestation with <presentation_scenario>

    Examples:
      | credential     | issuer | issuance_method | issue_scenario | verifier     | presentation_scenario |
      | PID (MSO Mdoc) | Python | from list       | same device    | Web verifier | same device           |
      | PID (MSO Mdoc) | Kotlin | from list       | same device    | Web verifier | same device           |