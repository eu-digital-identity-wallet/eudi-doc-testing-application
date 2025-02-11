@IOS @manual @US_SD
Feature: EUDI Wallet User Signs Document through QTSP with Explicit Consent
  As a EUDI Wallet User,
  I want to provide my explicit consent on signing a document through my EUDI Wallet and sign the document
  so that I can ensure that I sign the document as the sole signer of the specific document.

  #https://github.com/eu-digital-identity-wallet/eudi-wallet-product-roadmap/issues/103

  Background:
    Given the user has a valid EUDI Wallet on their mobile device
    And the user has issued a PID attestation to the EUDI Wallet
    And the user has internet connectivity
    And the user is authenticated in the QTSP

  @US_SD_TC_01 @manual:Passed
  Scenario: Relying Party retrieves available Credential IDs
    Given the user is on the Relying Party interface
    When the user has only one available Credential ID enrolled in the QTSP
    Then the Relying Party retrieves the available User Credential IDs

  @US_SD_TC_02 @manual:Failed
  Scenario: No Credential IDs enrolled for user in QTSP
    Given the Relying Party has retrieved available Credential IDs from the QTSP
    When there are no available Credential IDs for the user in the QTSP
    Then the Relying Party should inform the user to enroll a Qualified Certificate in the QTSP
    And the document signing process should stop

  @US_SD_TC_03 @manual:Failed
  Scenario: User selects preferred Credential ID (multiple options)
    Given there are multiple Credential IDs available for the user in the QTSP
    When the Relying Party requests the user to select a Credential ID
    Then the user selects a preferred Credential ID for signing the document

  @US_SD_TC_04 @manual:Passed
  Scenario: Relying Party retrieves details of selected Credential ID (single or selected ID)
    Given either the user has selected a Credential ID or there is only one available Credential ID
    When the QTSP renders a QR-code
    And the user scans the QR-code with the EUDI Wallet
    Then the EUDI Wallet informs the user that the QTSP requests to release the matching attestation

  @US_SD_TC_05 @manual:Passed
  Scenario: User does not want to proceed
    Given the EUDI Wallet finds available matching attestations
    When the EUDI Wallet requests the user to consent by authenticating with a six-digit PIN
    When the user decides not to proceed
    Then the user can return to the main page of the Wallet

  @US_SD_TC_06 @manual:Passed
  Scenario: User consents to release attestation
    Given the EUDI Wallet finds available matching attestations
    When the EUDI Wallet requests the user to consent by authenticating with a six-digit PIN
    And the user authenticates successfully
    Then the EUDI Wallet presents the requested attestation to the QTSP

  @US_SD_TC_07 @manual:Passed
  Scenario: QTSP verifies the attestation successfully
    Given the QTSP receives the attestation
    When the QTSP verifies the attestation successfully
    Then the EUDI Wallet displays a confirmation message indicating the presentation outcome from the QTSP
    And the user views the signed document at the Relying Party interface


