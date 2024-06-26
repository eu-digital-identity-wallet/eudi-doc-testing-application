@ANDROID @manual @US_COCDIS
Feature: Credential issuance on the cross device through the issuer service (first-time setup)

  @US_COCDIS_TC_01 @manual:Failed
  Scenario: First-time user sets up a PIN and adds a document
    When the user opens the EUDI Wallet for the first time
    Then the user is prompted to set up a PIN
    When the user sets up the PIN successfully
    Then the 'Add document' page is displayed

  @US_COCDIS_TC_02 @manual:Failed
  Scenario: User scans QR code to add a document
    Given the user is on the 'Add document' page
    When the user clicks on the 'Scan QR' option manually
    Then the camera is activated to scan a QR code

  @US_COCDIS_TC_03 @manual:Passed
  Scenario: User scans the QR code from the issuer service
    Given the user has activated the camera to scan a QR code
    When the user scans the QR code from the issuer service
    Then the user is presented with the details of the credential to be issued including the type of credential and issuer name

  @US_COCDIS_TC_04 @manual:Passed
  Scenario: User proceeds with the document issuance process
    Given the user is presented with the credential details
    When the user presses the ISSUE button manually
    Then the user is redirected to the issuer service for authentication and consent

  @US_COCDIS_TC_05 @manual:Passed
  Scenario: User authenticates and consents to the issuance at the issuer service
    Given the user is at the issuer service page
    When the user authenticates and consents to the issuance of the document
    Then the credential is issued to the user
    And the user is redirected back to the EUDI Wallet app

  @US_COCDIS_TC_06 @manual:Passed
  Scenario: User views success message and continues
    Given the user is redirected back to the EUDI Wallet app after the issuance
    When a success message is displayed
    And the user presses the CONTINUE button manually
    Then the new document is displayed in the dashboard screen