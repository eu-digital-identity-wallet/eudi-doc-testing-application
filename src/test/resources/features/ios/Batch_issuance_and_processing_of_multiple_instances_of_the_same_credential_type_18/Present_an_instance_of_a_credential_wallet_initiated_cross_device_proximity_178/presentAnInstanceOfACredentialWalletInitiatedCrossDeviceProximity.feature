@IOS @US_PAIOACWICDP @Q2_2025
Feature: Presentation of Batch Issued Attestations in Proximity Scenario
  As an EUDI Wallet User,,
  I want to present attributes of batch issued attestations to a requestor Relying Party operating a ‘reader’ device in a proximity scenario
  so that my privacy is protected when presenting attributes from an attestation multiple times to the same Relying Party or colluding Relying Parties (Relying Party linkability)

  #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application-internal/issues/178

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the EUDI Wallet and Relying Party User Interface reside on separate devices
    And QR & BLE protocols are available for connectivity in proximity sharing scenarios

  @US_PAIOACWICDP_TC_01 @manual:Passed
  Scenario: Successful authentication in the EUDI Wallet
    Given the user opens the EUDI Wallet
    When the user authenticates using a six-digit PIN or Biometrics
    Then the authentication is successful

  @US_PAIOACWICDP_TC_02 @manual:Passed
  Scenario: Unsuccessful authentication in the EUDI Wallet
    Given the user opens the EUDI Wallet
    When the user fails to authenticate using a six-digit PIN or Biometrics
    Then the Wallet presents an error message
    And the user can retry the authentication

  @US_PAIOACWICDP_TC_03 @manual:Passed
  Scenario: Presenting QR Code for attestation request
    Given the user is successfully authenticated in the EUDI Wallet
    When the user selects the option Show QR Code
    Then the EUDI Wallet renders a QR code on the device screen

  @US_PAIOACWICDP_TC_04 @manual:Passed
  Scenario: Scanning QR Code by Relying Party device
    Given the EUDI Wallet User presents the QR code to the Relying Party device
    When the Relying Party device scans the QR code
    Then the connection is established between the two devices

  @US_PAIOACWICDP_TC_05 @manual:Passed
  Scenario: Validation of attestation request by EUDI Wallet
    Given the EUDI Wallet receives the attestation request
    When the request is successfully validated
    Then the Wallet presents a screen informing the user of the request details

  @US_PAIOACWICDP_TC_06 @manual:Passed
  Scenario: Selection of attestation using Method A
    Given Method A is configured for the attestation type
    When the EUDI Wallet selects an available matching attestation
    Then the Wallet uses an attestation not previously presented to any Relying Party
    And the EUDI Wallet reduces the internal counter of unused attestations

  @US_PAIOACWICDP_TC_07 @manual:Passed
  Scenario: Selection of attestation using Method C
    Given Method C is configured for the attestation type
    When the EUDI Wallet selects an available matching attestation
    Then the Wallet uses an attestation from a batch in a random order

  @US_PAIOACWICDP_TC_08 @manual:Passed
  Scenario: No matching attestation available
    Given the EUDI Wallet attempts to match the attestation request
    When no attestation matches the request
    Then the Wallet stops the presentation flow
    And presents an error message indicating no available attestations

  @US_PAIOACWICDP_TC_09 @manual:Passed
  Scenario: User consent for attestation presentation
    Given the EUDI Wallet requests the user to consent
    When the user consents by authenticating successfully
    Then the authentication is successful

  @US_PAIOACWICDP_TC_10 @manual:Passed
  Scenario: Unsuccessful consent authentication
    Given the EUDI Wallet requests the user to consent
    When the user fails to authenticate using a six-digit PIN or Biometrics
    Then the Wallet presents an error message
    And the user can retry the authentication

  @US_PAIOACWICDP_TC_11 @manual:Passed
  Scenario: Presentation of attestation to Relying Party
    Given the user consents to the attestation presentation
    When the EUDI Wallet presents the requested attestation to the Relying Party
    Then the Relying Party service receives the attestation
    And verifies it, informing the user of the successful verification outcome

  @US_PAIOACWICDP_TC_12 @manual:Passed
  Scenario: Confirmation of presentation outcome
    Given the Relying Party service verifies the attestation
    Then the EUDI Wallet displays a confirmation message indicating the outcome