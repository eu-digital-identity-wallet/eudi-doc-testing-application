@ANDROID @manual @US_SD
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

  @US_SD_TC_01 @manual:InProgress
  Scenario: Relying Party retrieves available Credential IDs
    Given the user is on the Relying Party interface
    When the Relying Party requests the available Credential IDs from the QTSP
    Then the Relying Party should retrieve the available Credential IDs for the user

  @US_SD_TC_02 @manual:InProgress
  Scenario: No Credential IDs enrolled for user in QTSP
    Given the Relying Party has retrieved available Credential IDs from the QTSP
    When there are no available Credential IDs for the user in the QTSP
    Then the Relying Party should inform the user to enroll a Qualified Certificate in the QTSP
    And the document signing process should stop

  @US_SD_TC_03 @manual:InProgress
  Scenario: User selects preferred Credential ID (multiple options)
    Given there are multiple Credential IDs available for the user in the QTSP
    When the Relying Party requests the user to select a Credential ID
    Then the user selects a preferred Credential ID for signing the document

  @US_SD_TC_04 @manual:InProgress
  Scenario: Relying Party retrieves details of selected Credential ID (single or selected ID)
    Given either the user has selected a Credential ID or there is only one available Credential ID
    When the Relying Party retrieves the details of the selected Credential ID from the QTSP
    And calculates the document hash
    Then the Relying Party should redirect the user to the QTSP authentication page


  @US_SD_TC_05 @manual:InProgress
  Scenario: QTSP informs user of Relying Party’s request
    Given the user is on the QTSP authentication page
    When the QTSP requests access to use the selected Credential ID on behalf of the user
    Then the QTSP should inform the user of the Relying Party’s request to use the Credential ID

  @US_SD_TC_06 @manual:InProgress
  Scenario: QTSP renders QR code for EUDI Wallet scan
    Given the QTSP has requested access to the Credential ID on



