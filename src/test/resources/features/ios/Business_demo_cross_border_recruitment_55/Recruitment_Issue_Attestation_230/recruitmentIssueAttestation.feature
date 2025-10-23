@IOS @US_RIA
Feature: Request and Store Attestations in EUDI Wallet
  As a EUDI Wallet User
  I want to request my Attestations from a trusted issuer and store them in my EUDI Wallet
  So that I can identify myself and present employment documents in cross-border journeys

 #https://github.com/eu-digital-identity-wallet/eudi-doc-testing-application/issues/230

  Background:
    Given the user is registered to a valid EUDI Wallet on their mobile device
    And internet connectivity is available between the issuer and EUDI Wallet

  @US_RIA_TC_01
  Scenario: Request attestation via issuer service on different device with QR code
    Given the user visits the issuer service on a different device than the EUDI Wallet
    When the user selects attestation type Diploma and provides personal details
    And the issuer service renders a QR code
    And the user scans the QR code with the EUDI Wallet device
    And the user authenticates successfully with six digit PIN
    And the user confirms the attestation offer
    Then the issuer issues and sends the attestation to the EUDI Wallet
    And the EUDI Wallet stores and displays the attestation successfully

  @US_RIA_TC_02
  Scenario: Request attestation via issuer service on same device with DeepLink
    Given the user visits the issuer service on the same device as the EUDI Wallet
    When the user selects attestation type Seafarer Certificate and provides personal details
    And the user clicks the DeepLink button
    And the user authenticates successfully with six digit PIN
    Then the user is taken automatically to the issuer request in EUDI wallet
    And the user confirms the attestation offer
    And the EUDI Wallet stores and displays the attestation successfully

  @US_RIA_TC_03
  Scenario: Request attestation from EUDI Wallet Add document screen
    Given the user opens and authenticates successfully in the EUDI Wallet
    When the user selects Add document and chooses Digital ID
    And the user is redirected to the issuer web page
    And the user provides personal details and clicks Authorise
    Then the issuer issues the attestation and redirects to EUDI Wallet
    And the EUDI Wallet stores and displays the attestation successfully

  @US_RIA_TC_04
  Scenario: Handle authentication failure during attestation request
    Given the user has scanned a QR code for attestation issuance
    When the user enters incorrect six digit PIN
    Then the EUDI Wallet presents an error message
    And the user can retry authentication

  @US_RIA_TC_05
  Scenario: Handle issuance error during attestation process
    Given the user has confirmed an attestation offer
    When an error occurs during the issuance process
    Then the EUDI Wallet informs the user about the error
    And the operation stops without storing the attestation

  @US_RIA_TC_06
  Scenario: Handle wrong issuer page during wallet-initiated flow
    Given the user has selected Add document for Tax residency
    When the redirected web page does not provide the correct issuer page
    Then the user can return back to the EUDI Wallet
    And the EUDI Wallet remains responsive for other operations

  @US_RIA_TC_07
  Scenario: Handle user cancellation during consent process
    Given the user is on the issuer consent page
    When the user decides not to consent to attestation issuance
    Then the user can return back to the EUDI Wallet
    And the EUDI Wallet remains responsive for other operations


