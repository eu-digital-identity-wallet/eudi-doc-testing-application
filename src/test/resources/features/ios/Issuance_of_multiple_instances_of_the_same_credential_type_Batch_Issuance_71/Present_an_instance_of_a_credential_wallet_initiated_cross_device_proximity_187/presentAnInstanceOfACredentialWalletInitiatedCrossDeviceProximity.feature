@IOS @US_PAIOACWICDP @Q2_2025
Feature: Presentation of Batch Issued Attestations in Proximity Scenario

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/187

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And the EUDI Wallet and Relying Party User Interface reside on separate devices
    And QR/BLE protocols are available for connectivity in proximity sharing scenarios

  @US_PAIOACWICDP_TC_01
  Scenario: Successful authentication in the EUDI Wallet
    Given the user opens the EUDI Wallet
    When the user authenticates using a 6-digit PIN or Biometrics
    Then the authentication is successful

  @US_PAIOACWICDP_TC_02
  Scenario: Unsuccessful authentication in the EUDI Wallet
    Given the user opens the EUDI Wallet
    When the user fails to authenticate using a 6-digit PIN or Biometrics
    Then the Wallet presents an error message
    And the user can retry the authentication

  @US_PAIOACWICDP_TC_03
  Scenario: Presenting QR Code for attestation request
    Given the user is successfully authenticated in the EUDI Wallet
    When the user selects the option Show QR Code
    Then the EUDI Wallet renders a QR code on the device screen

  @US_PAIOACWICDP_TC_04
  Scenario: Scanning QR Code by Relying Party device
    Given the EUDI Wallet User presents the QR code to the Relying Party device
    When the Relying Party device scans the QR code
    Then the connection is established between the two devices

  @US_PAIOACWICDP_TC_05
  Scenario: Validation of attestation request by EUDI Wallet
    Given the EUDI Wallet receives the attestation request
    When the request is successfully validated
    Then the Wallet presents a screen informing the user of the request details

  @US_PAIOACWICDP_TC_06
  Scenario: Error during request validation
    Given the EUDI Wallet receives the attestation request
    When there is an error in validation
    Then the Wallet presents an error message
    And the flow stops

  @US_PAIOACWICDP_TC_07
  Scenario: Selection of attestation using Method A
    Given Method A is configured for the attestation type
    When the EUDI Wallet selects an available matching attestation
    Then the Wallet uses an attestation not previously presented to any Relying Party

  @US_PAIOACWICDP_TC_08
  Scenario: Selection of attestation using Method C
    Given Method C is configured for the attestation type
    When the EUDI Wallet selects an available matching attestation
    Then the Wallet uses an attestation from a batch in a random order
    And the Wallet resets the batch once all attestations are used

  @US_PAIOACWICDP_TC_09
  Scenario: No matching attestation available
    Given the EUDI Wallet attempts to match the attestation request
    When no attestation matches the request
    Then the Wallet stops the presentation flow
    And presents an error message indicating no available attestations

  @US_PAIOACWICDP_TC_10
  Scenario: User consent for attestation presentation
    Given the EUDI Wallet requests the user to consent
    When the user consents by authenticating successfully
    Then the authentication is successful

  @US_PAIOACWICDP_TC_11
  Scenario: Unsuccessful consent authentication
    Given the EUDI Wallet requests the user to consent
    When the user fails to authenticate using a 6-digit PIN or Biometrics
    Then the Wallet presents an error message
    And the user can retry the authentication

  @US_PAIOACWICDP_TC_12
  Scenario: Presentation of attestation to Relying Party
    Given the user consents to the attestation presentation
    When the EUDI Wallet presents the requested attestation to the Relying Party
    Then the Relying Party service receives the attestation
    And verifies it, informing the user of the successful verification outcome

  @US_PAIOACWICDP_TC_13
  Scenario: Verification failure by Relying Party
    Given the EUDI Wallet presents the requested attestation
    When the Relying Party service cannot verify the attestation
    Then a corresponding error message is displayed to the user

  @US_PAIOACWICDP_TC_14
  Scenario: Confirmation of presentation outcome
    Given the Relying Party service verifies the attestation
    Then the EUDI Wallet displays a confirmation message indicating the outcome

  @US_PAIOACWICDP_TC_015
  Scenario: Method A batch attestation management
    Given the presented attestation is part of a batch group and Method A is configured
    When the EUDI Wallet reduces the internal counter of unused attestations
    Then if the number of unused attestations reaches the lower limit
    And the Wallet informs the user to issue more attestations manually

