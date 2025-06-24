@IOS @US_PAIOACRPIRCD @Q2_2025
Feature: Presentation of Batch Issued Attestations to Relying Party on Separate Device
  As an EUDI Wallet User,,
  I want to present attributes of a batch issued attestations to a requestor Relying Party residing on a separate device from my EUDI Wallet
  so that my privacy is protected when presenting attributes from an attestation multiple times to the same Relying Party or colluding Relying Parties (Relying Party linkability)

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/173

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the EUDI Wallet and the Relying Party User Interface reside on separate devices
    And there is an active internet connection

  @US_PAIOACRPIRCD_TC_01 @manual:Passed
  Scenario: Relying Party initiated presentation flow
    Given the user visits the Relying Party service on a different device
    When the user selects the option to present an attestation type
    Then the Relying Party service renders a QR code

  @US_PAIOACRPIRCD_TC_02 @manual:Passed
  Scenario: Successful authentication in the EUDI Wallet
    Given the user opens the EUDI Wallet
    When the user authenticates using a six-digit PIN or Biometrics
    Then the authentication is successful

  @US_PAIOACRPIRCD_TC_03 @manual:Passed
  Scenario: Unsuccessful authentication in the EUDI Wallet
    Given the user opens the EUDI Wallet
    When the user fails to authenticate using a six-digit PIN or Biometrics
    Then the Wallet presents an error message
    And the user can retry the authentication

  @US_PAIOACRPIRCD_TC_04 @manual:Passed
  Scenario: Scanning QR Code with the EUDI Wallet device
    Given the Relying Party service renders a QR code
    When the user scans the QR code with the EUDI Wallet device
    Then the Wallet presents a screen informing the user of the attestation request
    And displays the name of the Relying Party and the attestation

  @US_PAIOACRPIRCD_TC_05 @manual:Passed
  Scenario: Selection of attestation using Method A
    Given Method A is configured for the attestation type
    When the EUDI Wallet selects an available matching attestation
    Then the Wallet uses an attestation not previously presented to any Relying Party
    And the EUDI Wallet reduces the internal counter of unused attestations

  @US_PAIOACRPIRCD_TC_06 @manual:Passed
  Scenario: Selection of attestation using Method C
    Given Method C is configured for the attestation type
    When the EUDI Wallet selects an available matching attestation
    Then the Wallet uses an attestation from a batch in a random order

  @US_PAIOACRPIRCD_TC_07 @manual:Passed
  Scenario: No matching attestation available
    Given the EUDI Wallet attempts to match the attestation request
    When no attestation matches the request
    Then the Wallet stops the presentation flow
    And presents an error message indicating no available attestations

  @US_PAIOACRPIRCD_TC_08 @manual:Passed
  Scenario: User consent for attestation presentation
    Given the EUDI Wallet requests the user to consent
    When the user consents by authenticating successfully
    Then the authentication is successful

  @US_PAIOACRPIRCD_TC_09 @manual:Passed
  Scenario: Unsuccessful consent authentication
    Given the EUDI Wallet requests the user to consent
    When the user fails to authenticate using a six-digit PIN or Biometrics
    Then the Wallet presents an error message
    And the user can retry the authentication

  @US_PAIOACRPIRCD_TC_10 @manual:Passed
  Scenario: Presentation of attestation to Relying Party
    Given the user consents to the attestation presentation
    When the EUDI Wallet presents the requested attestation to the Relying Party
    Then the Relying Party service receives the attestation
    And verifies it, informing the user of the successful verification outcome

  @US_PAIOACRPIRCD_TC_11 @manual:Passed
  Scenario: Confirmation of presentation outcome
    Given the Relying Party service verifies the attestation
    Then the EUDI Wallet displays a confirmation message indicating the outcome