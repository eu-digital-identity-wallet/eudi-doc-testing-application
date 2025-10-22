@ANDROID @US_PAIOACRPIRSD @Q2_2025
Feature: Presentation of Batch Issued Attestations in EUDI Wallet
  As an EUDI Wallet User,,
  I want to present attributes of a batch issued attestation to a requestor Relying Party residing on the same device as my EUDI Wallet
  so that my privacy is protected when presenting attributes from an attestation multiple times to the same Relying Party or colluding Relying Parties (Relying Party linkability)

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application-internal/issues/185

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the EUDI Wallet and the Relying Party User Interface reside on the same device
    And there is an active internet connection

  @US_PAIOACRPIRSD_TC_01 @manual:Passed
  Scenario: Relying Party initiated presentation flow
    Given the user visits the Relying Party service on their mobile device
    When the user selects the option to present an attestation type
    Then the Relying Party service redirects the user to the EUDI Wallet

  @US_PAIOACRPIRSD_TC_02 @manual:Passed
  Scenario: Successful authentication in the EUDI Wallet
    Given the EUDI Wallet is opened
    When the user authenticates using a six-digit PIN or Biometrics
    Then the authentication is successful

  @US_PAIOACRPIRSD_TC_03 @manual:Passed
  Scenario: Unsuccessful authentication in the EUDI Wallet
    Given the EUDI Wallet is opened
    When the user fails to authenticate using a six-digit PIN or Biometrics
    Then the Wallet presents an error message
    And the user can retry the authentication

  @US_PAIOACRPIRSD_TC_04 @manual:Passed
  Scenario: Presentation of attestation request to the user
    Given the user is successfully authenticated in the EUDI Wallet
    Then the EUDI Wallet informs the user that the Relying Party requests an attestation

  @US_PAIOACRPIRSD_TC_05 @manual:Passed
  Scenario: Selection of attestation using Method A
    Given Method A is configured for the attestation type
    When the EUDI Wallet selects an available matching attestation
    Then the Wallet uses an attestation not previously presented to any Relying Party
    And the EUDI Wallet reduces the internal counter of unused attestations

  @US_PAIOACRPIRSD_TC_06 @manual:Passed
  Scenario: Selection of attestation using Method C
    Given Method C is configured for the attestation type
    When the EUDI Wallet selects an available matching attestation
    Then the Wallet uses an attestation from a batch in a random order

  @US_PAIOACRPIRSD_TC_07 @manual:Passed
  Scenario: No matching attestation available
    Given the EUDI Wallet attempts to match the attestation request
    When no attestation matches the request
    Then the Wallet stops the presentation flow
    And presents an error message indicating no available attestations

  @US_PAIOACRPIRSD_TC_08 @manual:Passed
  Scenario: User consent for attestation presentation
    Given the EUDI Wallet requests the user to consent
    When the user consents by authenticating successfully
    Then the authentication is successful

  @US_PAIOACRPIRSD_TC_09 @manual:Passed
  Scenario: Unsuccessful consent authentication
    Given the EUDI Wallet requests the user to consent
    When the user fails to authenticate using a six-digit PIN or Biometrics
    Then the Wallet presents an error message
    And the user can retry the authentication

  @US_PAIOACRPIRSD_TC_10 @manual:Passed
  Scenario: Presentation of attestation to Relying Party
    Given the user consents to the attestation presentation
    When the EUDI Wallet displays a confirmation message indicating the outcome
    Then the Relying Party service receives the attestation
    And verifies it, informing the user of the successful verification outcome
