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

  @US_IMIOACIIRCASD_TC_01
  Scenario: User visits issuer service on a different device
    Given the user visits the issuer service on a different device
    When the user requests the issuance of an attestation type
    Then the issuer service renders a QR code

  @US_IMIOACIIRCASD_TC_02
  Scenario: User opens Wallet and authenticates successfully
    Given the user opens the EUDI Wallet
    When the user authenticates using a six-digit PIN or Biometrics
    Then the authentication is successful

  @US_IMIOACIIRCASD_TC_03
  Scenario: Wallet scans QR code and receives attestation offer
    Given the user scans the QR code with the Wallet device
    When the QR code is valid
    Then the EUDI Wallet presents the attestation

  @US_IMIOACIIRCASD_TC_04
  Scenario: Wallet requests confirmation to proceed
    Given the EUDI Wallet presents the attestation
    When the user views the attestation
    Then the Wallet requests the user to confirm to proceed

  @US_IMIOACIIRCASD_TC_05
  Scenario: User visits issuer on same device
    Given the user visits the issuer service on the same device
    When the user requests the issuance of an attestation type
    Then the issuer service redirects the user to the Wallet

  @US_IMIOACIIRCASD_TC_06
  Scenario: User authenticates successfully in Wallet on same device
    Given the EUDI Wallet opens
    When the user authenticates using a six-digit PIN or Biometrics
    Then the authentication is successful

  @US_IMIOACIIRCASD_TC_07
  Scenario: Wallet receives offer and prompts confirmation
    Given the authentication is successful
    When the Wallet receives the attestation offer
    Then the Wallet requests the user to confirm to proceed

  @US_IMIOACIIRCASD_TC_08
  Scenario: Unsuccessful authentication in Wallet
    Given the user opens the EUDI Wallet
    When the user fails to authenticate using a six-digit PIN or Biometrics
    Then the Wallet presents an error message

  @US_IMIOACIIRCASD_TC_09
  Scenario: User accepts to proceed with issuance
    Given the EUDI Wallet presents the attestation
    When the user accepts to proceed
    Then the user is redirected to the issuer service in the mobile browser -----

  @US_IMIOACIIRCASD_TC_10
  Scenario: User authenticates with the issuer
    Given the user is redirected to the issuer service
    When the user authenticates via the issuer's authentication method
    Then the issuer service displays the attestation details to be issued

  @US_IMIOACIIRCASD_TC_11
  Scenario: User consents and issuer completes issuance
    Given the issuer displays the attestation details
    When the user consents to the issuance
    Then the issuer service issues multiple attestations

  @US_IMIOACIIRCASD_TC_12
  Scenario: User is redirected back to Wallet after issuance
    Given the issuance is successful
    When the process is completed
    Then the user is redirected back to the EUDI Wallet

  @US_IMIOACIIRCASD_TC_13
  Scenario: Attestations stored based on issuer's smaller batch size
    Given the user consents to the attestation issuance
    When the maximum batch size advertised by the issuer is lower than the EUDI Wallet's internal minimum
    Then the issuance flow proceeds using the issuer's maximum batch size
    And the Wallet stores the attestations according to the issuer's batch size

  @US_IMIOACIIRCASD_TC_14
  Scenario: Successful storage of issued attestations
    Given the user is redirected back to the Wallet after issuance
    When the issuer has issued the attestations successfully
    Then the Wallet presents the attestation to the user

  @US_IMIOACIIRCASD_TC_15
  Scenario: Wallet informs user of successful issuance
    Given the attestation is presented in the Wallet
    When the Wallet confirms issuance and storage
    Then the Wallet displays a counter showing the number of attestations issued

  @US_IMIOACIIRCASD_TC_16
  Scenario: Error occurs during attestation issuance
    Given the user consents to the attestation issuance
    When an error occurs in the issuance process
    Then the Wallet informs the user about the error
    And no attestations are stored in the Wallet

  @US_IMIOACIIRCASD_TC_17
  Scenario: Redirection to issuer service fails
    Given the user accepts to proceed with the attestation issuance
    When there is an issue redirecting to the issuer service
    Then the user should be able to return to the EUDI Wallet
    And the user can select any other option in the Wallet